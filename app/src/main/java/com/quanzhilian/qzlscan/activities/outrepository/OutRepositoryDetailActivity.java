package com.quanzhilian.qzlscan.activities.outrepository;

import android.app.AlertDialog;
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
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.inrepository.InRepositoryDetailActivity;
import com.quanzhilian.qzlscan.activities.inrepository.InScanResultActivity;
import com.quanzhilian.qzlscan.activities.machining.MachiningDetailActivity;
import com.quanzhilian.qzlscan.activities.machining.MachiningItemDetailActivity;
import com.quanzhilian.qzlscan.adapter.InRepositoryItemAdapter;
import com.quanzhilian.qzlscan.adapter.InRepositoryItemDetailAdapter;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.base.BaseScanActivity;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.domain.RepositoryProduct;
import com.quanzhilian.qzlscan.models.domain.SimpleOrderPickupItemModel;
import com.quanzhilian.qzlscan.models.domain.SimpleOrderPickupModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillModel;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;
import com.quanzhilian.qzlscan.models.enums.MessageWhat;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillModel;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.scan.ClientConfig;
import com.quanzhilian.qzlscan.utils.AppUtils;
import com.quanzhilian.qzlscan.utils.Constant;
import com.quanzhilian.qzlscan.utils.GlobleCache;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.ParseTimeUtil;
import com.quanzhilian.qzlscan.utils.StringUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;
import com.scandecode.ScanDecode;
import com.scandecode.inf.ScanInterface;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.quanzhilian.qzlscan.R.id.tv_scan_diff_ton;
import static com.quanzhilian.qzlscan.R.id.tv_scan_ton;

public class OutRepositoryDetailActivity extends BaseActivity implements View.OnClickListener {
    //头部公共区域
    private RelativeLayout rl_common_header_title_bar;
    private LinearLayout ll_titilebar_back;
    private TextView common_header_title;
    private LinearLayout ll_titilebar_button;
    private TextView tv_titilebar_right;
    private ImageView im_titilebar_right;

    private TextView tv_bill_no;
    private TextView tv_company_name;
    private TextView tv_create_datetime;
    private TextView tv_repository_name;
    private TextView tv_bill_state_title;
    private TextView tv_bill_state;
    private TextView scan_in_product;
    private TextView approve_in_bill;

    private ListView lv_bill_product_item;
    private ListView lv_bill_product_detail;

    private TextView tv_bill_product_detail;

    private ProgressDialog progressDialog;//加载进度动画；

    SimpleOrderPickupModel orderPickupModel = null;
    InRepositoryItemAdapter itemAdapter = null;
    InRepositoryItemDetailAdapter itemDetailAdapter = null;

    final int LOCAL_QUERY_DETAIL_SUCCESS = 20000;
    private String repositoryBillId = null;

    List<RpositoryBillItemModel> rpositoryBillItemModelList = null;
    RpositoryBillModel rpositoryBillModel = null;

    private ScanInterface scanDecode;
    private boolean isScanActive = true;

    private List<RpositoryBillItemDetailModel> rpositoryBillItemDetailModelList = null;
    private double scanTon = 0d;
    private double scanDiffTon = 0d;

    private boolean hasRepositoryProduct = true;//是否有库存产品

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_repository_detail);
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

        ll_titilebar_button.setVisibility(View.VISIBLE);


        rl_common_header_title_bar.setBackgroundColor(getResources().getColor(R.color.out_scan_default));
        common_header_title.setText(getResources().getString(R.string.out_bill_detail));

        ll_titilebar_back.setOnClickListener(this);
        ll_titilebar_button.setOnClickListener(this);
    }

    private void initView() {
        scanDecode = new ScanDecode(this);
        try{ 	scanDecode.initService("true"); }catch(Exception e){ 	 }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("出库单详情下载中...");
        tv_bill_no = (TextView) findViewById(R.id.tv_bill_no);//入库单号
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);//供货单位
        tv_create_datetime = (TextView) findViewById(R.id.tv_create_datetime);//创建时间
        tv_repository_name = (TextView) findViewById(R.id.tv_repository_name);//仓库名称
        tv_bill_state_title = (TextView) findViewById(R.id.tv_bill_state_title);//单据扫码状态标题
        tv_bill_state = (TextView) findViewById(R.id.tv_bill_state);//单据扫码状态
        lv_bill_product_item = (ListView) findViewById(R.id.lv_bill_product_item);
        lv_bill_product_detail = (ListView) findViewById(R.id.lv_bill_product_detail);
        tv_bill_product_detail = (TextView) findViewById(R.id.tv_bill_product_detail);
        scan_in_product = (TextView) findViewById(R.id.scan_in_product);
        approve_in_bill = (TextView) findViewById(R.id.approve_in_bill);

        scan_in_product.setOnClickListener(this);
        approve_in_bill.setOnClickListener(this);
        if (getIntent().hasExtra("repositoryBillId")) {
            repositoryBillId = getIntent().getStringExtra("repositoryBillId");
            //加载详情和明细数据
            //initViewData();
        }

        if (getIntent().hasExtra("openType") && getIntent().getStringExtra("openType").equals("showDetail")) {
            scan_in_product.setVisibility(View.GONE);
            approve_in_bill.setVisibility(View.GONE);
        }

        scan_in_product.setOnTouchListener(new View.OnTouchListener() {
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
            public void getBarcode(String data) {
                if (isScanActive) {
                    if (!StringUtils.isEmpty(data)) {
                        isScanActive = false;
                        scanProduct(data);
                    }
                }
            }
        });

    }

    private void initViewData() {
        if (!StringUtils.isEmpty(repositoryBillId)) {
            rpositoryBillModel = DBManager.getInstance(this).queryBill(GlobleCache.getInst().getScmId(), GlobleCache.getInst().getCacheRepositoryId(), EnumQueryType.out_repository_bill.getVal() + "", repositoryBillId);
            if (rpositoryBillModel != null) {
                tv_bill_no.setText("出库单号：" + rpositoryBillModel.repositoryBillNo);//入库单号
                tv_company_name.setText("销售单位：" + rpositoryBillModel.relativeCompanyName);//供货单位
                tv_create_datetime.setText("创建时间：" + ParseTimeUtil.getDayToStamp(rpositoryBillModel.createDate, "yyyy-MM-dd HH:mm:ss"));//创建时间
                tv_repository_name.setText("出库仓库：" + rpositoryBillModel.repositoryName);//仓库名称
                tv_bill_state_title.setTextColor(getResources().getColor(R.color.in_scan_default));

                String stateName = "未扫码入库";
                if (rpositoryBillModel.state == 1) {
                    stateName = "未扫码出库";
                    //approve_in_bill.setEnabled(false);
                } else if (rpositoryBillModel.state == 2) {
                    stateName = "扫码出库中";
                    //approve_in_bill.setEnabled(true);
                } else {
                    stateName = "待出库审核";
                }
                tv_bill_state.setText(stateName);
                tv_bill_state_title.setTextColor(getResources().getColor(R.color.in_scan_default));

                //验证是否已保存到本地，如果未保存从远程下载数据
                boolean hasSaveInBillItem = DBManager.getInstance(OutRepositoryDetailActivity.this).hasSaveBillItem(repositoryBillId, EnumQueryType.out_repository_bill.getVal() + "");
                if (hasSaveInBillItem) {
                    progressDialog.setMessage("出库单详情加载中...");
                    progressDialog.show();
                    //已下载到本地
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            rpositoryBillItemModelList = DBManager.getInstance(OutRepositoryDetailActivity.this).queryInBillItemList(repositoryBillId, EnumQueryType.out_repository_bill.getVal() + "");
                            mHandler.sendEmptyMessage(LOCAL_QUERY_DETAIL_SUCCESS);
                        }
                    }).start();
                } else {
                    getOutBillDetail(repositoryBillId);
                }
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(OutRepositoryDetailActivity.this)
                        .setIcon(getResources().getDrawable(R.mipmap.iconfont_tishi))
                        .setTitle("提示")
                        .setMessage("该单据不属于当前仓库，请先切换仓库信息！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                onBackPressed();
                            }
                        })
                        .create();
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        } else {
            onBackPressed();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // progressDialog.dismiss();
            switch (msg.what) {
                case MessageWhat.MessageType.QUERY_DETAIL_SUCCESS:
                    bindModel();
                    break;
                case LOCAL_QUERY_DETAIL_SUCCESS:
                    bindModel();
                    break;
                case MessageWhat.MessageType.APPROVE_BILL_SUCCESS:
                    //审核单据成功：从单据列表删除所有相关数据
                    DBManager.getInstance(OutRepositoryDetailActivity.this).delBill(repositoryBillId.toString(), EnumQueryType.out_repository_bill.getVal() + "");
                    onBackPressed();
                    forToast(getString(R.string.approve_success));
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
        initViewData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanDecode.onDestroy();
    }

    private void bindModel() {
        if (rpositoryBillItemModelList != null && rpositoryBillItemModelList.size() > 0) {
            if (itemAdapter == null) {
                itemAdapter = new InRepositoryItemAdapter(OutRepositoryDetailActivity.this, rpositoryBillItemModelList);
                lv_bill_product_item.setAdapter(itemAdapter);
            } else {
                itemAdapter.repositoryBillItemModelList = rpositoryBillItemModelList;
                itemAdapter.notifyDataSetChanged();
            }

            //获取是否有库存明细信息，
            hasRepositoryProduct = DBManager.getInstance(this).hasRepositoryProduct(repositoryBillId, EnumQueryType.out_repository_bill.getVal() + "");
            if (hasRepositoryProduct) {
                if (itemDetailAdapter == null) {
                    itemDetailAdapter = new InRepositoryItemDetailAdapter(OutRepositoryDetailActivity.this, rpositoryBillItemModelList);
                    lv_bill_product_detail.setAdapter(itemDetailAdapter);
                } else {
                    itemDetailAdapter.repositoryBillItemModelList = rpositoryBillItemModelList;
                    itemDetailAdapter.notifyDataSetChanged();
                }
                hasRepositoryProduct = true;
            } else {
                lv_bill_product_detail.setVisibility(View.GONE);
                tv_bill_product_detail.setVisibility(View.GONE);
                hasRepositoryProduct = false;
            }
        }

        lv_bill_product_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RpositoryBillItemModel rpositoryBillModel = rpositoryBillItemModelList.get(position);

                Intent detailActivityIntent = new Intent(OutRepositoryDetailActivity.this, OutRepositoryItemDetailActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putSerializable("rpositoryBillModel", rpositoryBillModel);
                bundle.putString("repositoryBillId", rpositoryBillModel.repositoryBillId.toString());
                bundle.putString("repositoryBillItemId", rpositoryBillModel.repositoryBillItemId.toString());
                bundle.putSerializable("repositoryBillItemModel", rpositoryBillModel);
                detailActivityIntent.putExtras(bundle);
                startActivity(detailActivityIntent);
            }
        });
        progressDialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_titilebar_back:
                onBackPressed();
                break;
            case R.id.approve_in_bill:
                new android.support.v7.app.AlertDialog.Builder(OutRepositoryDetailActivity.this)
                        .setTitle("提示")
                        .setMessage("确认出库？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                vailDetail();
                            }
                        })
                        .create().show();
                break;
            case R.id.ll_titilebar_button:
                //清除已扫
                new android.support.v7.app.AlertDialog.Builder(OutRepositoryDetailActivity.this)
                        .setTitle("提示")
                        .setMessage("确认清除已扫描产品？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBManager.getInstance(OutRepositoryDetailActivity.this).delBillDetail(repositoryBillId, EnumQueryType.out_repository_bill.getVal() + "");
                                initViewData();
                            }
                        })
                        .create().show();
                break;
        }
    }


    private void getOutBillDetail(final String repositoryBillId) {
        if (NetWorkUtils.isNetWork(OutRepositoryDetailActivity.this)) {
            HttpClientUtils httpClientUtil = new HttpClientUtils();
            try {
                /*请求服务端*/
                Map<String, String> paras = new HashMap<String, String>();
                paras.put("orderPickupId", repositoryBillId);
                paras.put("flag", "1");
                paras.put("type", "1");
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_get_pickup_detail);
                httpClientUtil.postRequest(requestUrl, paras);
                progressDialog.show();//启动加载进度动画
            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                Toast.makeText(OutRepositoryDetailActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
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
                                                            //单据详情
                                                            orderPickupModel = jsonRequestResult.getResultObjBean(SimpleOrderPickupModel.class, "orderPickup");
                                                            List<RepositoryProduct> repositoryProductList = jsonRequestResult.getResultBeanList(RepositoryProduct.class, "repositoryProductList");
                                                            DBManager.getInstance(OutRepositoryDetailActivity.this).addPickupItem(orderPickupModel.getOrderPickupItemList());
                                                            DBManager.getInstance(OutRepositoryDetailActivity.this).insertRepositoryProduct(repositoryProductList, repositoryBillId, EnumQueryType.out_repository_bill.getVal() + "");
                                                            if (rpositoryBillItemModelList == null)
                                                                rpositoryBillItemModelList = DBManager.getInstance(OutRepositoryDetailActivity.this).queryInBillItemList(repositoryBillId, EnumQueryType.out_repository_bill.getVal() + "");
                                                            mHandler.sendEmptyMessage(MessageWhat.MessageType.QUERY_DETAIL_SUCCESS);
                                                            progressDialog.dismiss();
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

    /**
     * 扫码入库产品
     */
    private void scanProduct(String billNo) {
        //跳转到扫描结果页
        Bundle bundle = new Bundle();
        bundle.putString("repositoryBillId", repositoryBillId);
        bundle.putString("bar_code", billNo);
        jumpActivity(OutScanResultActivity.class, bundle);
    }

    private void vailDetail() {
        String msg = "";
        boolean hasChaiLing = false;
        if (rpositoryBillItemModelList != null) {
            for (RpositoryBillItemModel itemModel :
                    rpositoryBillItemModelList) {
                scanTon = 0;
                rpositoryBillItemDetailModelList = DBManager.getInstance(OutRepositoryDetailActivity.this).queryInBillItemDetailList(itemModel.repositoryBillItemId.toString(), EnumQueryType.out_repository_bill.getVal() + "", "1");
                if (rpositoryBillItemDetailModelList != null) {
                    for (RpositoryBillItemDetailModel model :
                            rpositoryBillItemDetailModelList) {
                        if (model.state == 1 && model.isFull == 1) {
                            scanTon += model.amountWeight;
                        }
                        if (model.isFull == 0 && !hasChaiLing) {
                            hasChaiLing = true;//如果有拆零商品，不做验证，传值到服务端验证
                        }
                    }

                    BigDecimal decimalScanTon = new BigDecimal(scanTon);
                    BigDecimal decimalTon = new BigDecimal(itemModel.ton);

                    if (decimalScanTon.compareTo(decimalTon) > 0) {
                        scanDiffTon = scanTon - itemModel.ton;
                        msg += itemModel.productInfo + ",多扫了<font color='#fb0404'>" + new DecimalFormat("#,##0.00000").format(scanDiffTon) + "</font>吨<br>";
                    } else if (decimalScanTon.compareTo(decimalTon) < 0) {
                        scanDiffTon = itemModel.ton - scanTon;
                        msg += itemModel.productInfo + ",还需<font color='#fb0404'>" + new DecimalFormat("#,##0.00000").format(scanDiffTon) + "</font>吨<br>";
                    }
                }
            }
        }
        if (StringUtils.isEmpty(msg) || hasChaiLing || !hasRepositoryProduct) {
            approveInBill();
        } else {
            new android.support.v7.app.AlertDialog.Builder(OutRepositoryDetailActivity.this)
                    .setTitle("提示")
                    .setMessage(Html.fromHtml(msg))
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("继续提交", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //DBManager.getInstance(OutRepositoryDetailActivity.this).delBillDetail(repositoryBillId, EnumQueryType.out_repository_bill.getVal() + "");
                            approveInBill();
                        }
                    })
                    .create().show();
        }
    }


    /**
     * 审核入库单
     */
    private void approveInBill() {
        if (NetWorkUtils.isNetWork(OutRepositoryDetailActivity.this)) {
            HttpClientUtils httpClientUtil = new HttpClientUtils();
            try {
                /*请求服务端*/
                Map<String, String> paras = new HashMap<String, String>();
                paras.put("type", "2");
                paras.put("orderPickupId", repositoryBillId);
                paras.put("operateDate", AppUtils.getCurrentTime());
                paras.put("operator", GlobleCache.getInst().getCacheOperator());
                List<RpositoryBillItemModel> billItemList = DBManager.getInstance(this).queryInBillItemList(repositoryBillId, EnumQueryType.out_repository_bill.getVal() + "");
                if (billItemList != null) {
                    //重新组装提货单
                    List<SimpleOrderPickupItemModel> orderPickupItemModels = new ArrayList<SimpleOrderPickupItemModel>();
                    SimpleOrderPickupItemModel orderPickupItemModel = null;
                    Map<String, Object> map = null;
                    for (RpositoryBillItemModel model :
                            billItemList) {
                        orderPickupItemModel = new SimpleOrderPickupItemModel();
                        orderPickupItemModel.setOrderPickupItemId(model.repositoryBillItemId);
                        orderPickupItemModel.setQuantity(model.quantity);
                        orderPickupItemModel.setQuantityUnit(model.quantityUnit);
                        orderPickupItemModel.setNote("");
                        orderPickupItemModel.setTon(model.ton);

                        map = new HashMap<String, Object>();
                        map.put("repositoryBillItemDetailList", JSONObject.toJSONString(model.rpositoryBillItemDetailModelList));
                        orderPickupItemModel.setMap(map);
                        orderPickupItemModels.add(orderPickupItemModel);
                    }
                    paras.put("itemListJSON", JSONObject.toJSONString(orderPickupItemModels, SerializerFeature.DisableCircularReferenceDetect));
                }
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_save_bill_out);
                httpClientUtil.postRequest(requestUrl, paras);
                progressDialog.show();//启动加载进度动画
            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                Toast.makeText(OutRepositoryDetailActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
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
                                                            mHandler.sendEmptyMessage(MessageWhat.MessageType.APPROVE_BILL_SUCCESS);
                                                            progressDialog.dismiss();
                                                        } else {
                                                            forToast(jsonRequestResult.getMsg());
                                                            if(!StringUtils.IsNullOrEmpty(jsonRequestResult.getMsg()) && jsonRequestResult.getMsg().contains("提单状态已改变")){
                                                                DBManager.getInstance(OutRepositoryDetailActivity.this).delBill(repositoryBillId,EnumQueryType.out_repository_bill.getVal()+"");
                                                                onBackPressed();
                                                            }
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
