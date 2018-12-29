package com.quanzhilian.qzlscan.activities.inrepository;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.changepositon.ChangeScanResultActivity;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.base.BaseScanActivity;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryPositionModel;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;
import com.quanzhilian.qzlscan.models.enums.MessageWhat;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemModel;
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

public class InScanResultActivity extends BaseScanActivity implements View.OnClickListener {
    //头部公共区域
    private RelativeLayout rl_common_header_title_bar;
    private LinearLayout ll_titilebar_back;
    private TextView common_header_title;
    private LinearLayout ll_titilebar_button;
    private TextView tv_titilebar_right;
    private ImageView im_titilebar_right;

    private TextView tv_continue_scan_product;//继续扫码
    private TextView tv_show_detail;//查看详情
    private TextView tv_save_and_exit;//保存退出
    private TextView tv_scan_original_barcode;//扫描原厂码
    private TextView tv_scan_position_barcode;//扫描库位码
    private TextView tv_select_change_position;//更改库位

    private String repositoryBillId;//入库单ID
    private String bar_code;//条码号
    private int scanQuantity = 0;
    private String repositoryPositionId;
    private int scanType = 1;//1:扫码入库详情；2：扫原厂码；3：扫库位

    private LinearLayout ll_scan_success;
    private LinearLayout ll_scan_fail;

    private TextView tv_product_bar_code;//条码
    private TextView tv_ling_amount;//令重
    private TextView tv_weight_ton;//件重
    private TextView tv_product_simple_code;//商品编码
    private TextView tv_product_name;//品名
    private TextView tv_factory_name;//厂家
    private TextView tv_brand_name;//品牌
    private TextView tv_gram_weight;//克重
    private TextView tv_specification;//规格
    private TextView tv_grade;//等级
    private TextView tv_original_barcode;//原厂码
    private TextView tv_position;//库位
    private TextView tv_total_count;//总数量
    private TextView tv_curr_count;//当前扫描数量

    private ProgressDialog progressDialog;//加载进度动画；
    final int QUERY_DETAIL_SUCCESS = 10000;
    final int LOCAL_QUERY_DETAIL_SUCCESS = 20000;

    private boolean isScanComplete = false;//扫码是否完成

    RpositoryBillItemModel itemModel = null;
    RpositoryBillItemDetailModel detialModel = null;

    private ScanInterface scanDecode;
    private boolean isScanActive = true;
    private boolean isFirstOn = true;
    private int scanTotal;
    private int currCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_scan_result);
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
        ll_titilebar_back.setVisibility(View.GONE);

        ll_titilebar_back.setOnClickListener(this);
    }

    private void initView() {
        scanDecode = new ScanDecode(this);
        try{ 	scanDecode.initService("true"); }catch(Exception e){ 	 }

        ll_scan_fail = (LinearLayout) findViewById(R.id.ll_scan_fail);
        ll_scan_success = (LinearLayout) findViewById(R.id.ll_scan_success);

        tv_show_detail = (TextView) findViewById(R.id.tv_show_detail);
        tv_save_and_exit = (TextView) findViewById(R.id.tv_save_and_exit);
        tv_scan_original_barcode = (TextView) findViewById(R.id.tv_scan_original_barcode);
        tv_scan_position_barcode = (TextView) findViewById(R.id.tv_scan_position_barcode);
        tv_scan_position_barcode.setVisibility(View.GONE);

        tv_product_bar_code = (TextView) findViewById(R.id.tv_product_bar_code);
        tv_ling_amount = (TextView) findViewById(R.id.tv_ling_amount);
        tv_weight_ton = (TextView) findViewById(R.id.tv_weight_ton);
        tv_product_simple_code = (TextView) findViewById(R.id.tv_product_simple_code);
        tv_product_name = (TextView) findViewById(R.id.tv_product_name);
        tv_factory_name = (TextView) findViewById(R.id.tv_factory_name);
        tv_brand_name = (TextView) findViewById(R.id.tv_brand_name);
        tv_gram_weight = (TextView) findViewById(R.id.tv_gram_weight);
        tv_specification = (TextView) findViewById(R.id.tv_specification);
        tv_grade = (TextView) findViewById(R.id.tv_grade);
        tv_original_barcode = (TextView) findViewById(R.id.tv_original_barcode);
        tv_position = (TextView) findViewById(R.id.tv_position);
        tv_select_change_position = (TextView) findViewById(R.id.tv_select_change_position);
        tv_total_count = (TextView) findViewById(R.id.tv_total_count);
        tv_curr_count = (TextView) findViewById(R.id.tv_curr_count);
        tv_continue_scan_product = (TextView) findViewById(R.id.tv_continue_scan_product);

        tv_show_detail.setOnClickListener(this);
        tv_save_and_exit.setOnClickListener(this);
        //tv_scan_original_barcode.setOnClickListener(this);
        //tv_scan_position_barcode.setOnClickListener(this);
        tv_select_change_position.setOnClickListener(this);
        //tv_continue_scan_product.setOnClickListener(this);

        rl_common_header_title_bar.setBackgroundColor(getResources().getColor(R.color.in_scan_default));
        common_header_title.setText(getResources().getString(R.string.in_bill_scan_success));
        bar_code = getIntent().getStringExtra("bar_code");
        repositoryBillId = getIntent().getStringExtra("repositoryBillId");
        scanTotal = getIntent().getIntExtra("scanTotal", 0);
        currCount = getIntent().getIntExtra("currCount", 0);

        showScanResult(bar_code);

        tv_continue_scan_product.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP: {
                        scanDecode.stopScan();//停止扫描
                        handler.removeCallbacks(startTask);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        isScanActive = true;
                        scanType = 1;
                        scanDecode.starScan();//启动扫描
                        break;
                    }

                    default:
                        break;
                }
                return false;
            }
        });

        tv_scan_original_barcode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP: {
                        scanDecode.stopScan();//停止扫描
                        handler.removeCallbacks(startTask);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        isScanActive = true;
                        scanType = 2;
                        scanDecode.starScan();//启动扫描
                        break;
                    }

                    default:
                        break;
                }
                return false;
            }
        });
        tv_scan_position_barcode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP: {
                        scanDecode.stopScan();//停止扫描
                        handler.removeCallbacks(startTask);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        isScanActive = true;
                        scanType = 3;
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
                if (isScanActive) {
                    isScanActive = false;
                    //forToast("result:结果页");
                    if (!StringUtils.isEmpty(data)) {
                        if (scanType == 1) {
                            saveScanResult();//保存上一次扫描结果
                            showScanResult(data);
                        } else if (scanType == 2) {
                            //扫码原厂码
                            tv_original_barcode.setText("原厂码："+data);
                            detialModel.originalBarcode = data;
                        } else if (scanType == 3) {
                            // 扫码库位码
                            SimpleRepositoryPositionModel position = DBManager.getInstance(InScanResultActivity.this).queryRepositoryPositionModelByCode(data, GlobleCache.getInst().getScmId());
                            if (position != null) {
                                tv_position.setText(position.getName());
                                detialModel.repositoryPositionId = position.getRepositoryPositionId();
                                detialModel.repositoryPositionName = position.getName();
                            }
                        }
                    }
                }
            }
        });
    }

    private void showScanResult(String bar_code) {
        tv_total_count.setText("共   " + scanTotal + "   件   ");
        if (!StringUtils.isEmpty(bar_code)) {
            //验证是否重复扫描
            detialModel = DBManager.getInstance(this).queryInBillItmeDetail(bar_code, EnumQueryType.in_repository_bill.getVal() + "");
            if (detialModel != null && detialModel.repositoryBillItemId != null) {
                if (detialModel.state == 0) {
                    tv_curr_count.setText("第   " + (currCount + 1) + "   件");
                    forToast("扫码成功，条码号：" + bar_code);
                } else {
                    tv_curr_count.setText("第   " + (currCount) + "   件");
                    forToast("重复扫码，条码号：" + bar_code);
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        itemModel = DBManager.getInstance(InScanResultActivity.this).queryInBillItem(detialModel.repositoryBillItemId.toString(), EnumQueryType.in_repository_bill.getVal() + "");
                        mHandler.sendEmptyMessage(LOCAL_QUERY_DETAIL_SUCCESS);
                    }
                }).start();
            } else {
                forToast("扫码失败，产品信息不存在！");
                ll_scan_fail.setVisibility(View.VISIBLE);
                ll_scan_success.setVisibility(View.GONE);
                common_header_title.setText(getResources().getString(R.string.in_bill_scan_fail));
                //getProduct();
            }
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // progressDialog.dismiss();
            switch (msg.what) {
                case QUERY_DETAIL_SUCCESS:
                    bindModel();
                    break;
                case LOCAL_QUERY_DETAIL_SUCCESS:
                    bindModel();
                    break;
                case MessageWhat.MessageType.QUERY_POSITION_SUCCESS:
                    tv_position.setText(msg.obj.toString());
                    detialModel.repositoryPositionId = msg.arg1;
                    detialModel.repositoryPositionName = msg.obj.toString();
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

    /**
     * 显示页面数据
     */
    private void bindModel() {
        if (itemModel == null || !repositoryBillId.equals(itemModel.repositoryBillId.toString())) {
            //显示扫码失败提示
            ll_scan_fail.setVisibility(View.VISIBLE);
            ll_scan_success.setVisibility(View.GONE);
        } else {

            ll_scan_fail.setVisibility(View.GONE);
            ll_scan_success.setVisibility(View.VISIBLE);
            common_header_title.setText(getResources().getString(R.string.in_bill_scan_success));
            if (detialModel != null) {
                tv_product_bar_code.setText(detialModel.barCode);
                tv_ling_amount.setText(detialModel.amountLing + "");
                tv_weight_ton.setText(detialModel.amountWeight + "吨");
                if (detialModel.repositoryPositionId != null && detialModel.repositoryPositionId > 0) {
                    tv_position.setText(DBManager.getInstance(InScanResultActivity.this).queryRepositoryPositionModel(detialModel.repositoryPositionId.toString(), GlobleCache.getInst().getScmId()).getName());
                }
                tv_original_barcode.setText("原厂码："+detialModel.originalBarcode);
            }
            if (itemModel != null) {
                tv_product_simple_code.setText(itemModel.productSku);
                tv_product_name.setText(itemModel.productName);
                tv_factory_name.setText(itemModel.factoryName);
                tv_brand_name.setText(itemModel.brandName);
                tv_gram_weight.setText(itemModel.gramWeight + "");
                tv_specification.setText(itemModel.specification);
                tv_grade.setText(itemModel.grade);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_titilebar_back:
                onBackPressed();
                break;
            case R.id.tv_show_detail:
                //显示单据详情
                RpositoryBillModel rpositoryBillModel = DBManager.getInstance(InScanResultActivity.this).getBillById(repositoryBillId, EnumQueryType.in_repository_bill.getVal() + "");
                Intent detailActivityIntent = new Intent(InScanResultActivity.this, InRepositoryDetailActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putSerializable("rpositoryBillModel", rpositoryBillModel);
                bundle.putString("repositoryBillId", rpositoryBillModel.repositoryBillId.toString());
                bundle.putString("openType","showDetail");
                detailActivityIntent.putExtras(bundle);
                startActivityForResult(detailActivityIntent, 11);
                break;
            case R.id.tv_save_and_exit:
                //保存并退出
                saveScanResult();
                onBackPressed();
                break;
            case R.id.tv_select_change_position:
                //选择更改库位
                selectRepositoryPosition(mHandler, InScanResultActivity.this);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanDecode.onDestroy();//回复初始状态
    }


    /**
     * 保存扫码结果
     */
    private void saveScanResult() {
        if (currCount >= scanTotal) isScanComplete = true;
        if (itemModel != null && repositoryBillId.equals(itemModel.repositoryBillId.toString()) && detialModel != null && !isScanComplete && detialModel.state == 0) {
            //1.更新详情库位，原厂码，扫码状态
            boolean result = DBManager.getInstance(InScanResultActivity.this).updateBillItemDetailState(detialModel.repositoryBillItemDetailId.toString(), EnumQueryType.in_repository_bill.getVal() + "", "1", detialModel.repositoryPositionId.toString(), detialModel.repositoryPositionName, detialModel.originalBarcode);
            if (result) {
                currCount += 1;
                //2.更新单据明细：更新扫码数量
                DBManager.getInstance(InScanResultActivity.this).updateBillItemState(itemModel.repositoryBillItemId.toString(), "1", EnumQueryType.in_repository_bill.getVal() + "");
                //3.更新单据状态为扫码入库/待审核入库
                //获取已扫码完成：更新状态为入库审核
                String state = "2";
                if (isScanComplete) state = "3";
                DBManager.getInstance(InScanResultActivity.this).updateBillState(repositoryBillId.toString(), state, EnumQueryType.in_repository_bill.getVal() + "");
            } else {

            }
        }
    }


    /*
      根据条码查询产品信息
     */
    private void getProduct() {
        if (NetWorkUtils.isNetWork(InScanResultActivity.this)) {
            HttpClientUtils httpClientUtil = new HttpClientUtils();
            try {
                /*请求服务端*/
                Map<String, String> paras = new HashMap<String, String>();
                paras.put("barCode", bar_code);
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_select_product_by_bar_code);
                httpClientUtil.postRequest(requestUrl, paras);
                progressDialog.show();//启动加载进度动画
            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                Toast.makeText(InScanResultActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
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
                                                            progressDialog.dismiss();
                                                            mHandler.sendEmptyMessage(MessageWhat.MessageType.QUERY_DETAIL_SUCCESS);
                                                            //数据下载成功后打开列表页
                                                            //jumpActivity(InRepositoryListActivity.class);
                                                        } else {
                                                            mHandler.sendEmptyMessage(MessageWhat.MessageType.QUERY_DETAIL_FAIL);
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
        } else {
            //设备无连接网络
            forToast(R.string.network_not_connected);
        }
    }

    /**
     * 按键扫码：只能扫产品码
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && (event.getKeyCode() == KeyEvent.KEYCODE_F4 || event.getKeyCode() == KeyEvent.KEYCODE_F5)){
            scanType = 1;
            isScanActive = true;
            if (isFirstOn) {
                isFirstOn = false;
                new AlertDialog.Builder(this)
                        .setTitle("温馨提示")
                        .setMessage("按键扫描只能扫码产品！")
                        .setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveScanResult();
    }
}
