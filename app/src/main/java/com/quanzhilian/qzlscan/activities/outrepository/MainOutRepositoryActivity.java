package com.quanzhilian.qzlscan.activities.outrepository;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.inrepository.InRepositoryDetailActivity;
import com.quanzhilian.qzlscan.activities.inrepository.MainInRepositoryActivity;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.base.BaseScanActivity;
import com.quanzhilian.qzlscan.common.CommonRequest;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.domain.SimpleOrderPickupModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryCutting;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;
import com.quanzhilian.qzlscan.models.enums.MessageWhat;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillModel;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.utils.GlobleCache;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.StringUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;
import com.scandecode.ScanDecode;
import com.scandecode.inf.ScanInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainOutRepositoryActivity extends BaseActivity implements View.OnClickListener {

    //头部公共区域
    private RelativeLayout rl_common_header_title_bar;
    private LinearLayout ll_titilebar_back;
    private TextView common_header_title;
    private TextView tv_repository_name;
    private LinearLayout ll_titilebar_button;
    private TextView tv_titilebar_right;
    private ImageView im_titilebar_right;

    //private Button btn_change_repository;

    private LinearLayout ll_scan_out_bill;
    private LinearLayout ll_download_bill;

    private static final int SHOW_REPOSITORY = 1;
    private static final int TIME = 3000;

    private ProgressDialog progressDialog;//加载进度动画；
    List<SimpleOrderPickupModel> repositoryBillModelList;

    private String lastSearchDate = null;

    private ScanInterface scanDecode;
    private  boolean isScanActive=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_out_repository);
        initTitle();
        initView();
    }

    /**
     * 设置头部
     */
    private void initTitle() {
        rl_common_header_title_bar = (RelativeLayout) findViewById(R.id.rl_common_header_title_bar);
        ll_titilebar_back = (LinearLayout) findViewById(R.id.ll_titilebar_back);
        common_header_title = (TextView) findViewById(R.id.common_header_title);
        ll_titilebar_button = (LinearLayout) findViewById(R.id.ll_titilebar_button);
        tv_titilebar_right = (TextView) findViewById(R.id.tv_titilebar_right);
        im_titilebar_right = (ImageView) findViewById(R.id.im_titilebar_right);
        tv_titilebar_right.setText("切换仓库");
        tv_titilebar_right.setVisibility(View.VISIBLE);
        im_titilebar_right.setImageDrawable(getResources().getDrawable(R.mipmap.change_repository));

        ll_titilebar_button.setVisibility(View.VISIBLE);

        rl_common_header_title_bar.setBackgroundColor(getResources().getColor(R.color.out_scan_default));
        common_header_title.setText(getResources().getString(R.string.check_out_bill));

        ll_titilebar_back.setOnClickListener(this);
        ll_titilebar_button.setOnClickListener(this);
    }

    /**
     * 初始化页面
     */
    private void initView() {
        scanDecode = new ScanDecode(this);
        try{ 	scanDecode.initService("true"); }catch(Exception e){ 	 }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("出库单下载中...");
        tv_repository_name = (TextView) findViewById(R.id.tv_repository_name);
        //btn_change_repository = (Button) findViewById(R.id.btn_change_repository);
        ll_scan_out_bill = (LinearLayout) findViewById(R.id.ll_scan_out_bill);
        ll_download_bill = (LinearLayout) findViewById(R.id.ll_download_bill);

       // btn_change_repository.setOnClickListener(this);
        ll_scan_out_bill.setOnClickListener(this);
        ll_download_bill.setOnClickListener(this);
        showRepository();

        ll_scan_out_bill.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP:{
                        scanDecode.stopScan();//停止扫描
                        handler.removeCallbacks(startTask);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN:{
                        scanDecode.starScan();//启动扫描
                        break;
                    }

                    default:
                        break;
                }
                return false;
            }
        });

        scanDecode.getBarCode(new ScanInterface.OnScanListener() {
            @Override
            public void getBarcode(String data) {
                if(isScanActive){
                    if (!StringUtils.isEmpty(data)) {
                        isScanActive = false;
                        getBillDetail(data);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_titilebar_back:
                onBackPressed();
                break;
            case R.id.ll_download_bill:
                //下载出库单
                if (isLogined(this)) {
                    CommonRequest.getServiceQueryTime(MainOutRepositoryActivity.this, mHandler);
                } else {
                    return;
                }
                break;
            case R.id.ll_titilebar_button:
                getRepositoryList(this, mHandler, true);
                break;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // progressDialog.dismiss();
            switch (msg.what) {
                case SHOW_REPOSITORY:
                    //刷新仓库
                    showRepository();
                    forToast("仓库已切换成：" + GlobleCache.getInst().getCacheRepositoryName());
                    break;
                case MessageWhat.MessageType.GET_QUERY_TIEM_SUCCESS:
                    if(msg!=null && msg.obj!=null) {
                        String nowLastSearchDate = msg.obj.toString();
                        lastSearchDate = DBManager.getInstance(MainOutRepositoryActivity.this).getLastSearchDate(EnumQueryType.out_repository_bill, nowLastSearchDate, GlobleCache.getInst().getCacheRepositoryId(), GlobleCache.getInst().getScmId());
                        String scmId = GlobleCache.getInst().getScmId();
                        getAllOutBillList(scmId);
                    }
                    break;
                case MessageWhat.MessageType.BILL_APPROVEED:
                    final String billId = msg.obj.toString();
                    AlertDialog alertDialog = new AlertDialog.Builder(MainOutRepositoryActivity.this)
                            .setIcon(getResources().getDrawable(R.mipmap.iconfont_tishi))
                            .setTitle("提示")
                            .setMessage("单据已审核，无需再扫码！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    DBManager.getInstance(MainOutRepositoryActivity.this).delBill(billId,EnumQueryType.out_repository_bill.getVal()+"");
                                }
                            }).create();
                    alertDialog.setCancelable(true);
                    alertDialog.setCanceledOnTouchOutside(true);
                    alertDialog.show();
                    break;
            }
        }

    };

    Handler handler = new Handler();

    //连续扫描
    private Runnable startTask = new Runnable() {
        @Override
        public void run() {
            scanDecode.starScan();
            handler.postDelayed(startTask, 1000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isScanActive = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanDecode.onDestroy();
    }

    /**
     * 从repositorySP显示仓库信息
     */
    private void showRepository() {
        String repositoryName = GlobleCache.getInst().getCacheRepositoryName();
        tv_repository_name.setText("当前仓库：" + repositoryName);
    }

    private void getAllOutBillList(final String scmId) {
        if (NetWorkUtils.isNetWork(MainOutRepositoryActivity.this)) {
            HttpClientUtils httpClientUtil = new HttpClientUtils();
            try {
                /*请求服务端*/
                Map<String, String> paras = new HashMap<String, String>();
                paras.put("state", "1");
                paras.put("type", "1");
                paras.put("repositoryId", GlobleCache.getInst().getCacheRepositoryId());
                paras.put("lastSearchDate", lastSearchDate);
                //paras.put("repositoryBillIds", getRepositoryBillIds());
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_get_pickup_list);
                httpClientUtil.postRequest(requestUrl, paras);
                progressDialog.show();//启动加载进度动画
            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                Toast.makeText(MainOutRepositoryActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
            } finally {

            }
            httpClientUtil.setOnGetData(new HttpClientUtils.OnGetResponseData() {
                                            @Override
                                            public void onGetData(String result) {
                                                if (result == null) {
                                                    progressDialog.dismiss(); //关闭加载进度动画
                                                    forToast(getString(R.string.server_connect_error));
                                                } else {
                                                    try {
                                                        JsonRequestResult jsonRequestResult = JsonRequestResult.toJsonRequestResult(result);
                                                        if (jsonRequestResult.getCode() == 1 && jsonRequestResult.getObj() != null) {
                                                            repositoryBillModelList = jsonRequestResult.getResultBeanList(SimpleOrderPickupModel.class);
                                                            if (repositoryBillModelList != null && repositoryBillModelList.size() > 0) {
                                                                DBManager.getInstance(MainOutRepositoryActivity.this).addPickupBill(repositoryBillModelList, EnumQueryType.out_repository_bill.getVal());
                                                            }
                                                            progressDialog.dismiss();
                                                            //数据下载成功后打开列表页
                                                            isScanActive = false;
                                                            jumpActivity(OutRepositoryListActivity.class);
                                                        } else {
                                                            isOnlineViaesult(MainOutRepositoryActivity.this, jsonRequestResult);
                                                            forToast(jsonRequestResult.getMsg());
                                                        }
                                                        progressDialog.dismiss(); //关闭加载进度动画
                                                    } catch (Exception ex) {
                                                        progressDialog.dismiss(); //关闭加载进度动画
                                                        forToast(R.string.json_parser_failed);
                                                    }
                                                }
                                            }
                                        }
            );
        } else //设备无连接网络
        {
            forToast(R.string.network_not_connected);
        }
    }

    private void getBillDetail(final String rpositoryBillNo) {
        //先从本地数据库查询
        RpositoryBillModel rpositoryBillModel = DBManager.getInstance(this).getBill(rpositoryBillNo,EnumQueryType.out_repository_bill.getVal()+"");
        if (rpositoryBillModel != null && rpositoryBillModel.repositoryBillId > 0) {
            Bundle bundle = new Bundle();
            bundle.putString("repositoryBillId",rpositoryBillModel.repositoryBillId.toString());
            bundle.putSerializable("rpositoryBillModel", rpositoryBillModel);
            jumpActivity(OutRepositoryDetailActivity.class, bundle);
        } else {
            if (NetWorkUtils.isNetWork(this)) {
                HttpClientUtils httpClientUtil = new HttpClientUtils();
                try {
                    /*请求服务端*/
                    Map<String, String> paras = new HashMap<String, String>();
                    paras.put("orderPickupNo", rpositoryBillNo);
                    paras.put("flag", "1");
                    paras.put("type", "1");
                    String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_get_pickup_detail);
                    httpClientUtil.postRequest(requestUrl, paras);
                    progressDialog.setMessage("请求数据中...");
                    progressDialog.show();//启动加载进度动画
                } catch (Exception ex) {
                    progressDialog.dismiss(); //关闭加载进度动画
                    Toast.makeText(MainOutRepositoryActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
                } finally {

                }
                httpClientUtil.setOnGetData(new HttpClientUtils.OnGetResponseData() {
                                                @Override
                                                public void onGetData(String result) {
                                                    if (result == null) {
                                                        progressDialog.dismiss(); //关闭加载进度动画
                                                        forToast(getString(R.string.server_connect_error));
                                                    } else {
                                                        try {
                                                            JsonRequestResult jsonRequestResult = JsonRequestResult.toJsonRequestResult(result);
                                                            if (jsonRequestResult.getCode() == 1) {
                                                                if (jsonRequestResult.getObj() != null) {
                                                                    SimpleOrderPickupModel rpositoryBillModel = jsonRequestResult.getResultObjBean(SimpleOrderPickupModel.class);
                                                                    if (rpositoryBillModel != null) {
                                                                        if (rpositoryBillModel.getState() == 2) {
                                                                            //单据已审核，无需再扫：弹出提示
                                                                            Message message = new Message();
                                                                            message.what = MessageWhat.MessageType.BILL_APPROVEED;
                                                                            message.obj = rpositoryBillModel.getOrderPickupId();
                                                                            mHandler.sendMessage(message);
                                                                            //mHandler.sendEmptyMessage(MessageWhat.MessageType.BILL_APPROVEED);
                                                                        } else if(rpositoryBillModel.getRepositoryCuttingId() != null){
                                                                                forToast("该提单因为有关联的加工单，请在电脑端完成出库！");
                                                                        } else {
                                                                            repositoryBillModelList = new ArrayList<SimpleOrderPickupModel>();
                                                                            repositoryBillModelList.add(rpositoryBillModel);
                                                                            DBManager.getInstance(MainOutRepositoryActivity.this).addPickupBill(repositoryBillModelList, EnumQueryType.out_repository_bill.getVal());
                                                                            //数据下载成功后打开详情页
                                                                            isScanActive = false;
                                                                            Bundle bundle = new Bundle();
                                                                            bundle.putString("repositoryBillId",rpositoryBillModel.getOrderPickupId().toString());
                                                                            jumpActivity(OutRepositoryDetailActivity.class, bundle);
                                                                        }
                                                                    } else {
                                                                        forToast("单据不存在！");
                                                                    }
                                                                } else {
                                                                    forToast("单据不存在！" + rpositoryBillNo);
                                                                }
                                                            } else {
                                                                forToast(jsonRequestResult.getMsg());
                                                            }
                                                            progressDialog.dismiss(); //关闭加载进度动画
                                                        } catch (Exception ex) {
                                                            progressDialog.dismiss(); //关闭加载进度动画
                                                            forToast(R.string.json_parser_failed);
                                                        }
                                                    }
                                                }
                                            }
                );
            } else //设备无连接网络
            {
                forToast(R.string.network_not_connected);
            }
        }
    }

    /**
     * 获取已有单据Ids
     * @return
     */
    private String getRepositoryBillIds() {
        String billIds = "";
        String scmId = GlobleCache.getInst().getScmId();
        List<RpositoryBillModel> rpositoryBillModels = DBManager.getInstance(this).queryBillList(scmId, GlobleCache.getInst().getCacheRepositoryId(), "1,2,3", EnumQueryType.out_repository_bill.getVal() + "");
        if(rpositoryBillModels!=null) {
            for (int i = 0; i < rpositoryBillModels.size(); i++) {
                if (i < rpositoryBillModels.size()-1) {
                    billIds += rpositoryBillModels.get(i).repositoryBillId + ",";
                } else {
                    billIds += rpositoryBillModels.get(i).repositoryBillId;
                }
            }
        }
        return billIds;
    }
}
