package com.quanzhilian.qzlscan.activities.machining;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.quanzhilian.qzlscan.activities.outrepository.MainOutRepositoryActivity;
import com.quanzhilian.qzlscan.activities.outrepository.OutRepositoryListActivity;
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

public class MainMachiningActivity extends BaseActivity implements View.OnClickListener {
    //头部公共区域
    private RelativeLayout rl_common_header_title_bar;
    private LinearLayout ll_titilebar_back;
    private TextView common_header_title;
    private LinearLayout ll_titilebar_button;
    private TextView tv_titilebar_right;
    private ImageView im_titilebar_right;

    private TextView tv_repository_name;
    private Button btn_change_repository;

    private LinearLayout ll_scan_machining_bill;
    private LinearLayout ll_download_machining_bill;

    private static final int SHOW_REPOSITORY = 1;
    private static final int TIME = 3000;

    private ProgressDialog progressDialog;//加载进度动画；
    List<SimpleRepositoryCutting> repositoryCuttinglList;

    private static final int GET_QUERY_TIEM_SUCCESS = 20000;
    private String lastSearchDate = null;

    private ScanInterface scanDecode;
    private  boolean isScanActive=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_machining);
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

        rl_common_header_title_bar.setBackgroundColor(getResources().getColor(R.color.machining_default));
        common_header_title.setText(getResources().getString(R.string.check_machining_bill));

        ll_titilebar_back.setOnClickListener(this);
        ll_titilebar_button.setOnClickListener(this);
    }


    /**
     * 初始化页面
     */
    private void initView(){
        scanDecode = new ScanDecode(this);
        try{ 	scanDecode.initService("true"); }catch(Exception e){ 	 }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加工出库单下载中...");
        tv_repository_name = (TextView) findViewById(R.id.tv_repository_name);
        //btn_change_repository = (Button) findViewById(R.id.btn_change_repository);
        ll_scan_machining_bill= (LinearLayout) findViewById(R.id.ll_scan_machining_bill);
        ll_download_machining_bill = (LinearLayout) findViewById(R.id.ll_download_machining_bill);

        //btn_change_repository.setOnClickListener(this);
        //ll_scan_machining_bill.setOnClickListener(this);
        ll_download_machining_bill.setOnClickListener(this);
        showRepository();

        ll_scan_machining_bill.setOnTouchListener(new View.OnTouchListener() {
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
            public void getBarcode(String billNo) {
                if(isScanActive){
                    if (!StringUtils.isEmpty(billNo)) {
                        isScanActive = false;
                        getBillDetail(billNo);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_titilebar_back:
                onBackPressed();
                break;
            case R.id.ll_download_machining_bill:
                //下载加工单
                if (isLogined(this)) {
                    CommonRequest.getServiceQueryTime(MainMachiningActivity.this, mHandler);
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
                        lastSearchDate = DBManager.getInstance(MainMachiningActivity.this).getLastSearchDate(EnumQueryType.maching_bill, nowLastSearchDate, GlobleCache.getInst().getCacheRepositoryId(), GlobleCache.getInst().getScmId());
                        String scmId = GlobleCache.getInst().getScmId();
                        getAllBillList(scmId);
                    }
                    break;
                case MessageWhat.MessageType.BILL_APPROVEED:
                    final String billId = msg.obj.toString();
                    AlertDialog alertDialog = new AlertDialog.Builder(MainMachiningActivity.this)
                            .setIcon(getResources().getDrawable(R.mipmap.iconfont_tishi))
                            .setTitle("提示")
                            .setMessage("单据已审核，无需再扫码！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    DBManager.getInstance(MainMachiningActivity.this).delBill(billId,EnumQueryType.maching_bill.getVal()+"");
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

    private void getAllBillList(final String scmId) {
        if (NetWorkUtils.isNetWork(MainMachiningActivity.this)) {
            HttpClientUtils httpClientUtil = new HttpClientUtils();
            try {
                /*请求服务端*/
                Map<String, String> paras = new HashMap<String, String>();
                paras.put("state", "1");
                paras.put("type", "1");
                paras.put("isParent","0");
                paras.put("oldRepositoryId",GlobleCache.getInst().getCacheRepositoryId());
                paras.put("lastSearchDate",lastSearchDate);
                //paras.put("repositoryBillIds", getRepositoryBillIds());
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_get_cutting_list);
                httpClientUtil.postRequest(requestUrl, paras);
                progressDialog.show();//启动加载进度动画
            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                Toast.makeText(MainMachiningActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
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
                                                            repositoryCuttinglList = jsonRequestResult.getResultBeanList(SimpleRepositoryCutting.class);
                                                            if (repositoryCuttinglList != null && repositoryCuttinglList.size() > 0) {
                                                                List<RpositoryBillModel> rpositoryBillModels = new ArrayList<RpositoryBillModel>();
                                                                RpositoryBillModel rpositoryBillModel;
//                                                                for (SimpleRepositoryBillModel model :
//                                                                        repositoryBillModelList) {
//                                                                    rpositoryBillModel = new RpositoryBillModel(scmId, model.getRepositoryBillId(), model.getRepositoryBillNo(), model.getRelativeCompanyName(), model.getCreateDate().getTime(), model.getRepositoryName(),model.getRepositoryId(), 1);
//                                                                    rpositoryBillModels.add(rpositoryBillModel);
//                                                                }
                                                                DBManager.getInstance(MainMachiningActivity.this).addMachingBill(repositoryCuttinglList, EnumQueryType.maching_bill.getVal());
                                                            }
                                                            progressDialog.dismiss();
                                                            //数据下载成功后打开列表页
                                                            isScanActive = false;
                                                            jumpActivity(MachiningListActivity.class);
                                                        } else {
                                                            isOnlineViaesult(MainMachiningActivity.this, jsonRequestResult);
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

    private void getBillDetail(final String billNo) {
        //先从本地数据库查询
        RpositoryBillModel rpositoryBillModel = DBManager.getInstance(MainMachiningActivity.this).getBill(billNo,EnumQueryType.maching_bill.getVal()+"");
        if (rpositoryBillModel != null && rpositoryBillModel.repositoryBillId > 0) {
            Bundle bundle = new Bundle();
            bundle.putString("repositoryBillId",rpositoryBillModel.repositoryBillId.toString());
            bundle.putSerializable("rpositoryBillModel", rpositoryBillModel);
            jumpActivity(MachiningDetailActivity.class, bundle);
        } else {
            if (NetWorkUtils.isNetWork(MainMachiningActivity.this)) {
                HttpClientUtils httpClientUtil = new HttpClientUtils();
                try {
                    /*请求服务端*/
                    Map<String, String> paras = new HashMap<String, String>();
                    paras.put("repositoryCuttingNo", billNo);
                    paras.put("oldRepositoryId", GlobleCache.getInst().getCacheRepositoryId());
                    String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_get_cutting_detail);
                    httpClientUtil.postRequest(requestUrl, paras);
                    progressDialog.setMessage("请求数据中...");
                    progressDialog.show();//启动加载进度动画
                } catch (Exception ex) {
                    progressDialog.dismiss(); //关闭加载进度动画
                    Toast.makeText(MainMachiningActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
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
                                                                    SimpleRepositoryCutting repositoryCutting = jsonRequestResult.getResultObjBean(SimpleRepositoryCutting.class,"repositoryCutting");
                                                                    if (repositoryCutting != null) {
                                                                        if (repositoryCutting.getState() == 2) {
                                                                            //入库单已审核，无需再扫：弹出提示
                                                                            Message message = new Message();
                                                                            message.what = MessageWhat.MessageType.BILL_APPROVEED;
                                                                            message.obj = repositoryCutting.getRepositoryCuttingId();
                                                                            mHandler.sendMessage(message);
                                                                            //mHandler.sendEmptyMessage(MessageWhat.MessageType.BILL_APPROVEED);
                                                                        } else {
                                                                            repositoryCuttinglList = new ArrayList<SimpleRepositoryCutting>();
                                                                            repositoryCuttinglList.add(repositoryCutting);
                                                                            DBManager.getInstance(MainMachiningActivity.this).addMachingBill(repositoryCuttinglList, EnumQueryType.maching_bill.getVal());
                                                                            //数据下载成功后打开详情页
                                                                            Bundle bundle = new Bundle();
                                                                            bundle.putString("repositoryBillId", repositoryCutting.getRepositoryCuttingId().toString());
                                                                            jumpActivity(MachiningDetailActivity.class, bundle);
                                                                        }
                                                                    } else {
                                                                        forToast("单据不存在！");
                                                                    }
                                                                } else {
                                                                    forToast("单据不存在！" + billNo);
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
        List<RpositoryBillModel> rpositoryBillModels = DBManager.getInstance(this).queryBillList(scmId, GlobleCache.getInst().getCacheRepositoryId(), "1,2,3", EnumQueryType.maching_bill.getVal() + "");
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
