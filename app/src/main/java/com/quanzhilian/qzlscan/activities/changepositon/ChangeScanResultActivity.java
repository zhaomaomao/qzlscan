package com.quanzhilian.qzlscan.activities.changepositon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.quanzhilian.qzlscan.activities.inrepository.InScanResultActivity;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.base.BaseScanActivity;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.domain.ProductScm;
import com.quanzhilian.qzlscan.models.domain.RepositoryProduct;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryPositionModel;
import com.quanzhilian.qzlscan.models.enums.MessageWhat;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemModel;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.scan.ClientConfig;
import com.quanzhilian.qzlscan.utils.GlobleCache;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.StringUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;
import com.scandecode.ScanDecode;
import com.scandecode.inf.ScanInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChangeScanResultActivity extends BaseScanActivity implements View.OnClickListener {
    //头部公共区域
    private RelativeLayout rl_common_header_title_bar;
    private LinearLayout ll_titilebar_back;
    private TextView common_header_title;
    private LinearLayout ll_titilebar_button;
    private TextView tv_titilebar_right;
    private ImageView im_titilebar_right;

    private TextView tv_continue_scan_product;//继续扫码
    private TextView tv_exit;//保存退出
    private TextView tv_scan_original_barcode;//扫描原厂码
    private TextView tv_scan_position_barcode;//扫描库位码
    private TextView tv_select_change_position;//更改库位

    private String repositoryBillId;//入库单ID
    private String bar_code;//条码号

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

    private boolean queryResult = true;

    private String productId = null;
    private String repositoryPositionId = null;
    private RepositoryProduct repositoryProduct = null;
    private String barCodeFactory = "没有原厂码";

    private ScanInterface scanDecode;
    private  boolean isScanActive=true;
    private int scanType;
    private boolean isFirstOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_scan_result);
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


        rl_common_header_title_bar.setBackgroundColor(getResources().getColor(R.color.change_default));
        common_header_title.setText("商品库位调整");

        ll_titilebar_back.setOnClickListener(this);
    }

    private void initView() {
        scanDecode = new ScanDecode(this);
        try{ 	scanDecode.initService("true"); }catch(Exception e){ 	 }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("产品信息下载中...");

        ll_scan_fail = (LinearLayout) findViewById(R.id.ll_scan_fail);
        ll_scan_success = (LinearLayout) findViewById(R.id.ll_scan_success);

        tv_exit = (TextView) findViewById(R.id.tv_exit);
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

        tv_exit.setOnClickListener(this);
        //tv_scan_original_barcode.setOnClickListener(this);
        //tv_scan_position_barcode.setOnClickListener(this);
        tv_select_change_position.setOnClickListener(this);
        //tv_continue_scan_product.setOnClickListener(this);

        bar_code = getIntent().getStringExtra("bar_code");
        showProduct(bar_code);

        tv_continue_scan_product.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP:{
                        scanDecode.stopScan();//停止扫描
                        handler.removeCallbacks(startTask);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN:{
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

                    case MotionEvent.ACTION_UP:{
                        scanDecode.stopScan();//停止扫描
                        handler.removeCallbacks(startTask);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN:{
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

                    case MotionEvent.ACTION_UP:{
                        scanDecode.stopScan();//停止扫描
                        handler.removeCallbacks(startTask);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN:{
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
            public void getBarcode(String billNo) {
                if(isScanActive){
                    if (!StringUtils.isEmpty(billNo)) {
                        if (scanType == 1) {
                            saveChangeRepositoryPosition(2,mHandler);
                            showProduct(billNo);
                        } else if (scanType == 2) {
                            //扫码原厂码
                            tv_original_barcode.setText(billNo);
                            barCodeFactory =  billNo;
                        }
                    }
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // progressDialog.dismiss();
            switch (msg.what) {
                case MessageWhat.MessageType.QUERY_DETAIL_SUCCESS:
                    queryResult = true;
                    bindModel();
                    break;
                case MessageWhat.MessageType.QUERY_DETAIL_FAIL:
                    //产品信息查询失败，显示失败页面
                    queryResult = false;
                    bindModel();
                    break;
                case MessageWhat.MessageType.SAVE_EXIT:
                    forToast("退出");
                    onBackPressed();
                    break;
                case MessageWhat.MessageType.QUERY_POSITION_SUCCESS:
                    tv_position.setText(msg.obj.toString());
                    repositoryPositionId = msg.arg1 + "";
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
        scanDecode.onDestroy();//回复初始状态
    }

    private void showProduct(String barCode) {
        bar_code = barCode;
        repositoryBillId = getIntent().getStringExtra("repositoryBillId");
        //progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("产品信息下载中...");
        if (!StringUtils.isEmpty(bar_code)) {
            getProduct();
        }
    }

    /**
     * 显示页面数据
     */
    private void bindModel() {
        if (repositoryProduct == null) {
            //显示扫码失败提示
            ll_scan_fail.setVisibility(View.VISIBLE);
            ll_scan_success.setVisibility(View.GONE);
            common_header_title.setText("移库扫码失败");
            tv_exit.setText("退出");
        } else {
            ll_scan_fail.setVisibility(View.GONE);
            ll_scan_success.setVisibility(View.VISIBLE);
            common_header_title.setText("商品库位调整");
            if (repositoryProduct != null) {
                repositoryPositionId = repositoryProduct.getRepositoryPositionId().toString();
                tv_product_bar_code.setText(repositoryProduct.getBarCode());
                tv_ling_amount.setText(repositoryProduct.getLing() + "");
                tv_weight_ton.setText(repositoryProduct.getTon() + "吨");
                productId = repositoryProduct.getRepositoryProductId().toString();
                if (repositoryProduct.getRepositoryPositionId() != null) {
                    SimpleRepositoryPositionModel position = DBManager.getInstance(ChangeScanResultActivity.this).queryRepositoryPositionModel(repositoryProduct.getRepositoryPositionId().toString(), GlobleCache.getInst().getScmId());
                    if (position != null) {
                        tv_position.setText(position.getName());
                    }
                }
            }
            if (repositoryProduct.getProductScm() != null) {
                ProductScm product = repositoryProduct.getProductScm();
                tv_product_simple_code.setText(product.getProductSku());
                tv_product_name.setText(product.getProductName());
                tv_factory_name.setText(product.getFactoryName());
                tv_brand_name.setText(product.getBrandName());
                tv_gram_weight.setText(product.getGramWeight() + "");
                tv_specification.setText(product.getSpecification());
                tv_grade.setText(product.getGrade());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_titilebar_back:
                onBackPressed();
                break;
            case R.id.tv_exit:
                //退出
                if (queryResult) {
                    saveChangeRepositoryPosition(1, mHandler);
                } else {
                    onBackPressed();
                }
                break;
            case R.id.tv_select_change_position:
                //选择更改库位
                selectRepositoryPosition(mHandler, ChangeScanResultActivity.this);
                break;
        }
    }

    /*
          根据条码查询产品信息
         */
    private void getProduct() {
        if (NetWorkUtils.isNetWork(ChangeScanResultActivity.this)) {
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
                Toast.makeText(ChangeScanResultActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
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
                                                            repositoryProduct = jsonRequestResult.getResultObjBean(RepositoryProduct.class);
                                                            progressDialog.dismiss();
                                                            mHandler.sendEmptyMessage(MessageWhat.MessageType.QUERY_DETAIL_SUCCESS);
                                                            //数据下载成功后打开列表页
                                                            //jumpActivity(InRepositoryListActivity.class);
                                                        } else {
                                                            repositoryProduct = null;
                                                            mHandler.sendEmptyMessage(MessageWhat.MessageType.QUERY_DETAIL_FAIL);
                                                            forToast(jsonRequestResult.getMsg());
                                                        }
                                                        progressDialog.dismiss(); //关闭加载进度动画
                                                    } catch (Exception ex) {
                                                        mHandler.sendEmptyMessage(MessageWhat.MessageType.QUERY_DETAIL_FAIL);
                                                        progressDialog.dismiss(); //关闭加载进度动画
                                                        forToast(R.string.json_parser_failed);
                                                    }
                                                }
                                            }
                                        }
            );
        } else {
            //设备无连接网络
            mHandler.sendEmptyMessage(MessageWhat.MessageType.QUERY_DETAIL_FAIL);
            forToast(R.string.network_not_connected);
        }
    }

    private void saveChangeRepositoryPosition(final int type, final Handler handler) {
        if (NetWorkUtils.isNetWork(ChangeScanResultActivity.this)) {
            HttpClientUtils httpClientUtil = new HttpClientUtils();
            try {
                /*请求服务端*/
                Map<String, String> paras = new HashMap<String, String>();
                paras.put("repositoryProductId", productId);
                paras.put("repositoryPositionId", repositoryPositionId);
                paras.put("barCodeFactory", barCodeFactory);
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_save_change_position);
                httpClientUtil.postRequest(requestUrl, paras);
                progressDialog.setMessage("保存数据中...");
                progressDialog.show();//启动加载进度动画
            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                Toast.makeText(ChangeScanResultActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
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
                                                            if (type == 1) {
                                                                //保存退出
                                                                handler.sendEmptyMessage(MessageWhat.MessageType.SAVE_EXIT);
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
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
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

}
