package com.quanzhilian.qzlscan.activities.outrepository;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Vibrator;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.inrepository.InRepositoryDetailActivity;
import com.quanzhilian.qzlscan.activities.inrepository.InScanResultActivity;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.base.BaseScanActivity;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.domain.RepositoryProduct;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryCuttingDetail;
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

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutScanResultActivity extends BaseScanActivity implements View.OnClickListener {
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
    private double scanQuantity = 0;
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
    private TextView tv_total_count;//总吨数
    private TextView tv_curr_count;//当前扫描数量
    private TextView tv_scan_ton;//已扫吨数
    private TextView tv_scan_diff_ton;//差额

    private ProgressDialog progressDialog;//加载进度动画；
    final int QUERY_DETAIL_SUCCESS = 10000;
    final int LOCAL_QUERY_DETAIL_SUCCESS = 20000;

    RpositoryBillItemModel itemModel = null;
    RpositoryBillItemDetailModel detialModel = null;
    SimpleRepositoryBillItemDetailModel itemDetailModel = null;

    private ScanInterface scanDecode;
    private boolean isScanActive = true;
    private double currCount;
    private double scanTon;
    private double scanDiffTon;

    List<RpositoryBillItemDetailModel> rpositoryBillItemDetailModelList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_scan_result);

        initTitle();
        initView();

        //扫码成功提示音
        player = MediaPlayer.create(getApplicationContext(), R.raw.scan);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
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
        //tv_scan_original_barcode = (TextView) findViewById(R.id.tv_scan_original_barcode);
        //tv_scan_position_barcode = (TextView) findViewById(R.id.tv_scan_position_barcode);
        //tv_scan_position_barcode.setVisibility(View.GONE);

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
        tv_scan_ton = (TextView) findViewById(R.id.tv_scan_ton);
        tv_scan_diff_ton = (TextView) findViewById(R.id.tv_scan_diff_ton);
        tv_continue_scan_product = (TextView) findViewById(R.id.tv_continue_scan_product);

        tv_show_detail.setOnClickListener(this);
        tv_save_and_exit.setOnClickListener(this);
        //tv_continue_scan_product.setOnClickListener(this);

        rl_common_header_title_bar.setBackgroundColor(getResources().getColor(R.color.out_scan_default));
        common_header_title.setText(getResources().getString(R.string.out_bill_scan_success));
        bar_code = getIntent().getStringExtra("bar_code");
        repositoryBillId = getIntent().getStringExtra("repositoryBillId");
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
                if (isScanActive) {
                    if (!StringUtils.isEmpty(billNo)) {
                        if (scanType == 1) {
                            bar_code = billNo;
                            saveScanResult();
                            showScanResult(billNo);
                        } else if (scanType == 2) {
                            //扫码原厂码
                            tv_original_barcode.setText(billNo);
                            detialModel.originalBarcode = billNo;
                        } else if (scanType == 3) {
                            // 扫码库位码
                            SimpleRepositoryPositionModel position = DBManager.getInstance(OutScanResultActivity.this).queryRepositoryPositionModelByCode(billNo, GlobleCache.getInst().getScmId());
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
        scanTon = 0;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("产品信息下载中...");
        if (!StringUtils.isEmpty(bar_code)) {
            RepositoryProduct repositoryProduct = DBManager.getInstance(this).getRepositoryProductByBarCode(bar_code, repositoryBillId, EnumQueryType.out_repository_bill.getVal() + "");
            if (repositoryProduct != null) {
                itemModel = DBManager.getInstance(OutScanResultActivity.this).queryBillItemByProductId(repositoryBillId, repositoryProduct.getProductId().toString(), EnumQueryType.out_repository_bill.getVal() + "");
                itemDetailModel = new SimpleRepositoryBillItemDetailModel();
                itemDetailModel.setProductId(repositoryProduct.getProductId());
                itemDetailModel.setRepositoryProductId(repositoryProduct.getRepositoryProductId());
                itemDetailModel.setAmountLing(repositoryProduct.getOldAmountLing());
                itemDetailModel.setAmountWeight(repositoryProduct.getTon());
                itemDetailModel.setRepositoryBillItemId(itemModel.repositoryBillItemId);
                itemDetailModel.setBarCode(bar_code);
                itemDetailModel.setRepositoryBillId(itemModel.repositoryBillId);
                itemDetailModel.setRepositoryPositionId(repositoryProduct.getRepositoryPositionId());
                if (repositoryProduct.getRepositoryPositionId() != null) {
                    SimpleRepositoryPositionModel positionModel = DBManager.getInstance(this).queryRepositoryPositionModel(repositoryProduct.getRepositoryPositionId().toString(), GlobleCache.getInst().getScmId());
                    if (positionModel != null) {
                        itemDetailModel.setRepositoryPosition(positionModel);
                    }
                }
                bindModel();
            } else {
                //getProduct();
                getProduct();
//                forToast("扫码失败，库存信息不存在！");
//                ll_scan_fail.setVisibility(View.VISIBLE);
//                ll_scan_success.setVisibility(View.GONE);
//                common_header_title.setText(getResources().getString(R.string.out_bill_scan_file));
            }
//            detialModel = DBManager.getInstance(OutScanResultActivity.this).queryInBillItmeDetail(bar_code);
//            if (detialModel != null && detialModel.repositoryBillItemId != null) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        itemModel = DBManager.getInstance(OutScanResultActivity.this).queryInBillItem(detialModel.repositoryBillItemId.toString());
//                        mHandler.sendEmptyMessage(LOCAL_QUERY_DETAIL_SUCCESS);
//                    }
//                }).start();
//            } else {
//                getProduct();
//            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // progressDialog.dismiss();
            switch (msg.what) {
                case MessageWhat.MessageType.QUERY_DETAIL_SUCCESS:
                    //1.验证扫码的产品是否与期货单明细产品匹配，如果匹配则加入详情
                    if (itemDetailModel != null && itemDetailModel.getProductId() != null) {
                        itemModel = DBManager.getInstance(OutScanResultActivity.this).queryBillItemByProductId(repositoryBillId, itemDetailModel.getProductId().toString(), EnumQueryType.out_repository_bill.getVal() + "");
                        if (itemModel != null && itemModel.repositoryBillItemId > 0) {
                            //添加详情到数据库，标记单据为扫码状态
                            itemDetailModel.setRepositoryBillItemId(itemModel.repositoryBillItemId);
                        }
                    }
                    bindModel();
                    break;
                case MessageWhat.MessageType.QUERY_DETAIL_FAIL:
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
     * 显示页面数据
     */
    private void bindModel() {
        if (itemModel == null || itemDetailModel == null) {
            //显示扫码失败提示
            ll_scan_fail.setVisibility(View.VISIBLE);
            ll_scan_success.setVisibility(View.GONE);
        } else {

            ll_scan_fail.setVisibility(View.GONE);
            ll_scan_success.setVisibility(View.VISIBLE);
            common_header_title.setText(getResources().getString(R.string.out_bill_scan_success));

            tv_total_count.setText(Html.fromHtml("共需<font color='#01e19f'>" + new DecimalFormat("#,##0.00000").format(itemModel.ton) + "</font>吨"));
            currCount = itemModel.scanQuantity;

            detialModel = DBManager.getInstance(this).queryInBillItmeDetail(bar_code, EnumQueryType.out_repository_bill.getVal() + "");
            if (detialModel == null || detialModel.state == 0) {
                tv_curr_count.setText("第   " + (currCount + 1) + "   件");
                forToast("扫码成功，条码号：" + bar_code);
            } else {
                tv_curr_count.setText("第   " + (currCount) + "   件");
                forToast("重复扫码，条码号：" + bar_code);
                return;
            }

            rpositoryBillItemDetailModelList = DBManager.getInstance(OutScanResultActivity.this).queryInBillItemDetailList(itemModel.repositoryBillItemId.toString(), EnumQueryType.out_repository_bill.getVal() + "","1");
            if (rpositoryBillItemDetailModelList != null) {
                for (RpositoryBillItemDetailModel model :
                        rpositoryBillItemDetailModelList) {
                    if (model.state == 1) {
                        scanTon += model.amountWeight;
                    }
                }

                if(itemDetailModel!=null) {scanTon += itemDetailModel.getAmountWeight();}

                tv_scan_ton.setText(Html.fromHtml("已扫<font color='#01e19f'>" + new DecimalFormat("#,##0.00000").format(scanTon) + "</font>吨"));

                BigDecimal decimalScanTon = new BigDecimal(scanTon);
                BigDecimal decimalTon = new BigDecimal(itemModel.ton);

                if (decimalScanTon.compareTo(decimalTon) > 0) {
                    scanDiffTon = scanTon - itemModel.ton;
                    tv_scan_diff_ton.setText(Html.fromHtml("多扫了<font color='#e1ad52'>" + new DecimalFormat("#,##0.00000").format(scanDiffTon) + "</font>吨"));//
                } else if (decimalScanTon.compareTo(decimalTon) < 0) {
                    scanDiffTon = itemModel.ton - scanTon;
                    tv_scan_diff_ton.setText(Html.fromHtml("还需<font color='#fb0404'>" + new DecimalFormat("#,##0.00000").format(scanDiffTon) + "</font>吨"));
                } else {
                    tv_scan_diff_ton.setVisibility(View.GONE);
                }
            } else {
                tv_curr_count.setText(Html.fromHtml("已扫<font color='#01e19f'>0</font>件"));
                tv_scan_ton.setText(Html.fromHtml("已扫<font color='#01e19f'>0</font>吨"));
                tv_scan_diff_ton.setVisibility(View.GONE);
            }
            if (itemDetailModel != null) {
                tv_product_bar_code.setText(itemDetailModel.getBarCode());
                tv_ling_amount.setText(itemDetailModel.getAmountLing() + "");
                tv_weight_ton.setText(itemDetailModel.getAmountWeight() + "吨");
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
                RpositoryBillModel rpositoryBillModel = DBManager.getInstance(OutScanResultActivity.this).getBillById(repositoryBillId, EnumQueryType.out_repository_bill.getVal() + "");
                Intent detailActivityIntent = new Intent(OutScanResultActivity.this, OutRepositoryDetailActivity.class);
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
        }
    }

    /**
     * 保存扫码结果
     */
    private void saveScanResult() {
        if (itemModel != null && repositoryBillId.equals(itemModel.repositoryBillId.toString())) {
            itemDetailModel.setIsFull(true);
            int result = DBManager.getInstance(OutScanResultActivity.this).addPickupItemDetail(itemDetailModel);
            if (result == 0) {
                currCount += 1;
                //1.更新详情库位，原厂码，扫码状态
                //DBManager.getInstance(OutScanResultActivity.this).updateBillItemDetailState(detialModel.repositoryBillItemDetailId.toString(), "1", detialModel.repositoryPositionId.toString(), detialModel.repositoryPositionName, detialModel.originalBarcode);
                //2.更新单据明细：更新扫码数量
                DBManager.getInstance(OutScanResultActivity.this).updateBillItemState(itemModel.repositoryBillItemId.toString(), (scanQuantity + 1) + "", EnumQueryType.out_repository_bill.getVal() + "");
                //3.更新单据状态为扫码入库
                DBManager.getInstance(OutScanResultActivity.this).updateBillState(repositoryBillId.toString(), "2", EnumQueryType.out_repository_bill.getVal() + "");
            }
        }
    }


    /*
      根据条码查询产品信息
     */
    private void getProduct() {
        if (NetWorkUtils.isNetWork(OutScanResultActivity.this)) {
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
                Toast.makeText(OutScanResultActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
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
                                                            itemDetailModel = jsonRequestResult.getResultObjBean(SimpleRepositoryBillItemDetailModel.class);
                                                            itemModel = DBManager.getInstance(OutScanResultActivity.this).queryBillItemByProductId(repositoryBillId, itemDetailModel.getProductId().toString(), EnumQueryType.out_repository_bill.getVal() + "");
                                                            mHandler.sendEmptyMessage(MessageWhat.MessageType.QUERY_DETAIL_SUCCESS);
                                                            progressDialog.dismiss();
                                                            //数据下载成功后打开列表页
                                                            //jumpActivity(InRepositoryListActivity.class);
                                                        } else {
                                                            forToast(jsonRequestResult.getMsg());
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveScanResult();
    }
}
