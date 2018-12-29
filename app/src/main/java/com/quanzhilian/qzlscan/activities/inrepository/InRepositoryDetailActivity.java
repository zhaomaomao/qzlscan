package com.quanzhilian.qzlscan.activities.inrepository;

import android.app.AlertDialog;
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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.adapter.InRepositoryAdapter;
import com.quanzhilian.qzlscan.adapter.InRepositoryItemAdapter;
import com.quanzhilian.qzlscan.adapter.InRepositoryItemDetailAdapter;
import com.quanzhilian.qzlscan.application.CustomerApplication;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.base.BaseScanActivity;
import com.quanzhilian.qzlscan.common.CommonRequest;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillModel;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;
import com.quanzhilian.qzlscan.models.enums.MessageWhat;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillModel;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.scan.ClientConfig;
import com.quanzhilian.qzlscan.utils.AppUtils;
import com.quanzhilian.qzlscan.utils.GlobleCache;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.ParseTimeUtil;
import com.quanzhilian.qzlscan.utils.StringUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;
import com.quanzhilian.qzlscan.views.CustomeListView;
import com.scandecode.ScanDecode;
import com.scandecode.inf.ScanInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InRepositoryDetailActivity extends BaseActivity implements View.OnClickListener {
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

    private ProgressDialog progressDialog;//加载进度动画；

    SimpleRepositoryBillModel simpleRepositoryBillModel = null;
    InRepositoryItemAdapter itemAdapter = null;
    InRepositoryItemDetailAdapter itemDetailAdapter = null;

    final int LOCAL_QUERY_DETAIL_SUCCESS = 20000;
    private String repositoryBillId = null;
    private String lastSearchDate = null;

    List<RpositoryBillItemModel> rpositoryBillItemModelList = null;
    RpositoryBillModel rpositoryBillModel;


    private ScanInterface scanDecode;
    private boolean isScanActive = true;

    private int scanTotal = 0;
    private int currCount = 0;//已经扫码数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_repository_detail);
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

        rl_common_header_title_bar.setBackgroundColor(getResources().getColor(R.color.in_scan_default));
        common_header_title.setText(getResources().getString(R.string.in_bill_detail));

        ll_titilebar_back.setOnClickListener(this);
    }

    private void initView() {
        scanDecode = new ScanDecode(this);
        try{ 	scanDecode.initService("true"); }catch(Exception e){ 	 }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("入库单详情下载中...");
        tv_bill_no = (TextView) findViewById(R.id.tv_bill_no);//入库单号
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);//供货单位
        tv_create_datetime = (TextView) findViewById(R.id.tv_create_datetime);//创建时间
        tv_repository_name = (TextView) findViewById(R.id.tv_repository_name);//仓库名称
        tv_bill_state_title = (TextView) findViewById(R.id.tv_bill_state_title);//单据扫码状态标题
        tv_bill_state = (TextView) findViewById(R.id.tv_bill_state);//单据扫码状态
        lv_bill_product_item = (ListView) findViewById(R.id.lv_bill_product_item);
        lv_bill_product_detail = (ListView) findViewById(R.id.lv_bill_product_detail);
        scan_in_product = (TextView) findViewById(R.id.scan_in_product);
        approve_in_bill = (TextView) findViewById(R.id.approve_in_bill);

        //scan_in_product.setOnClickListener(this);
        approve_in_bill.setOnClickListener(this);
        if (getIntent().hasExtra("repositoryBillId")) {
            repositoryBillId = getIntent().getStringExtra("repositoryBillId");
            //加载明细和详情数据
            //initViewData();
        }

        if(getIntent().hasExtra("openType") && getIntent().getStringExtra("openType").equals("showDetail")){
            scan_in_product.setVisibility(View.GONE);
            approve_in_bill.setVisibility(View.GONE);
        }

        scan_in_product.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP: {
                        scanDecode.stopScan();//停止扫描
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        isScanActive = true;
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
                        scanInProduct(data);
                    }
                }
            }
        });
    }

    private void initViewData() {
        if (!StringUtils.isEmpty(repositoryBillId)) {
            rpositoryBillModel = DBManager.getInstance(this).queryBill(GlobleCache.getInst().getScmId(), GlobleCache.getInst().getCacheRepositoryId(), EnumQueryType.in_repository_bill.getVal() + "", repositoryBillId);
            if (rpositoryBillModel != null) {
                tv_bill_no.setText("入库单号：" + rpositoryBillModel.repositoryBillNo);//入库单号
                tv_company_name.setText("供货单位：" + rpositoryBillModel.relativeCompanyName);//供货单位
                tv_create_datetime.setText("创建时间：" + ParseTimeUtil.getDayToStamp(rpositoryBillModel.createDate, "yyyy-MM-dd HH:mm:ss"));//创建时间
                tv_repository_name.setText("入库仓库：" + rpositoryBillModel.repositoryName);//仓库名称
                String stateName = "未扫码入库";
                if (rpositoryBillModel.state == 1) {
                    stateName = "未扫码入库";
                    approve_in_bill.setEnabled(false);
                } else if (rpositoryBillModel.state == 2) {
                    stateName = "扫码入库中";
                    approve_in_bill.setEnabled(true);
                } else {
                    stateName = "待入库审核";
                }
                tv_bill_state.setText(stateName);
                tv_bill_state_title.setTextColor(getResources().getColor(R.color.in_scan_default));

                // 合并详情及明细
                getAllInBillDetail(repositoryBillId);
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(InRepositoryDetailActivity.this)
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
                    //验证是否已保存到本地，如果未保存从远程下载数据
                    boolean hasSaveInBillItem = DBManager.getInstance(InRepositoryDetailActivity.this).hasSaveBillItem(repositoryBillId, EnumQueryType.in_repository_bill.getVal() + "");
                    if (hasSaveInBillItem) {
                        progressDialog.setMessage("入库单详情加载中...");
                        progressDialog.show();
                        rpositoryBillItemModelList = DBManager.getInstance(InRepositoryDetailActivity.this).queryInBillItemList(repositoryBillId, EnumQueryType.in_repository_bill.getVal() + "");
                        bindModel();
                    }
                    break;
                case LOCAL_QUERY_DETAIL_SUCCESS:
                    bindModel();
                    break;
                case MessageWhat.MessageType.GET_QUERY_TIEM_SUCCESS:
                    lastSearchDate = msg.obj.toString();
                    vailDetail();
                    //saveInBill();
                    break;
                case MessageWhat.MessageType.SAVE_BILL_SUCCESS:
                    approveInBill();
                    break;
                case MessageWhat.MessageType.APPROVE_BILL_SUCCESS:
                    //审核单据成功：从单据列表删除所有相关数据
                    DBManager.getInstance(InRepositoryDetailActivity.this).delBill(repositoryBillId.toString(), EnumQueryType.in_repository_bill.getVal() + "");
                    onBackPressed();
                    forToast(getString(R.string.approve_success));
                    break;
            }
        }

    };


    private void bindModel() {
        scanTotal = 0;
        currCount = 0;
        if (rpositoryBillItemModelList != null && rpositoryBillItemModelList.size() > 0) {
            for (RpositoryBillItemModel billModel :
                    rpositoryBillItemModelList) {
                if (billModel.rpositoryBillItemDetailModelList != null) {
                    scanTotal += billModel.rpositoryBillItemDetailModelList.size();//汇总扫码总数
                    for (RpositoryBillItemDetailModel detailModel :
                            billModel.rpositoryBillItemDetailModelList) {
                        if (detailModel.state == 1) {
                            currCount += 1;
                        }
                    }
                }
            }
            if (itemAdapter == null) {
                itemAdapter = new InRepositoryItemAdapter(InRepositoryDetailActivity.this, rpositoryBillItemModelList);
                lv_bill_product_item.setAdapter(itemAdapter);
            } else {
                itemAdapter.repositoryBillItemModelList = rpositoryBillItemModelList;
                itemAdapter.notifyDataSetChanged();
            }

            if (itemDetailAdapter == null) {
                itemDetailAdapter = new InRepositoryItemDetailAdapter(InRepositoryDetailActivity.this, rpositoryBillItemModelList);
                lv_bill_product_detail.setAdapter(itemDetailAdapter);
            } else {
                itemDetailAdapter.repositoryBillItemModelList = rpositoryBillItemModelList;
                itemDetailAdapter.notifyDataSetChanged();
            }
        }
        progressDialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_titilebar_back:
                onBackPressed();
                break;
            case R.id.approve_in_bill:
                new android.support.v7.app.AlertDialog.Builder(InRepositoryDetailActivity.this)
                        .setTitle("提示")
                        .setMessage("您确认提交审核入库单？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("提交审核", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CommonRequest.getServiceQueryTime(InRepositoryDetailActivity.this, mHandler);
                            }
                        })
                        .create().show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewData();
        isScanActive = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanDecode.onDestroy();//回复初始状态
    }


    private void getAllInBillDetail(final String repositoryBillId) {
        if (NetWorkUtils.isNetWork(InRepositoryDetailActivity.this)) {
            HttpClientUtils httpClientUtil = new HttpClientUtils();
            try {
                /*请求服务端*/
                Map<String, String> paras = new HashMap<String, String>();
                paras.put("repositoryBillId", repositoryBillId);
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_get_bill_detial);
                httpClientUtil.postRequest(requestUrl, paras);
                progressDialog.show();//启动加载进度动画
            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                Toast.makeText(InRepositoryDetailActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
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
                                                            simpleRepositoryBillModel = jsonRequestResult.getResultObjBean(SimpleRepositoryBillModel.class, "repositoryBill");
                                                            boolean resultBoolean = DBManager.getInstance(InRepositoryDetailActivity.this).addInRpositoryDetail(simpleRepositoryBillModel, EnumQueryType.in_repository_bill.getVal() + "");
                                                            if (resultBoolean) {
                                                                if (rpositoryBillItemModelList == null)
                                                                    rpositoryBillItemModelList = DBManager.getInstance(InRepositoryDetailActivity.this).queryInBillItemList(repositoryBillId, EnumQueryType.in_repository_bill.getVal() + "");
                                                                mHandler.sendEmptyMessage(MessageWhat.MessageType.QUERY_DETAIL_SUCCESS);
                                                            }
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
    private void scanInProduct(String billNo) {
        //跳转到扫描结果页
        isScanActive = false;
        Bundle bundle = new Bundle();
        bundle.putString("repositoryBillId", repositoryBillId);
        bundle.putString("bar_code", billNo);
        bundle.putInt("scanTotal", scanTotal);
        bundle.putInt("currCount", currCount);
        jumpActivity(InScanResultActivity.class, bundle);
    }

    private void vailDetail() {
        List<RpositoryBillItemModel> billItemList = DBManager.getInstance(this).queryInBillItemList(repositoryBillId, EnumQueryType.in_repository_bill.getVal() + "");
        //验证是否已扫完
        boolean isComplate = true;
        for (RpositoryBillItemModel model :
                billItemList) {
            if (model.quantity > model.scanQuantity) {
                isComplate = false;
                break;
            }
        }

        if (!isComplate) {
            new android.support.v7.app.AlertDialog.Builder(InRepositoryDetailActivity.this)
                    .setTitle("提示")
                    .setMessage("入库单明细未全部扫完，是否提交？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("继续提交", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            saveInBill();
                        }
                    })
                    .create().show();
        } else {
            saveInBill();
        }
    }


    /**
     * 保存入库单
     */
    private void saveInBill() {
        if (NetWorkUtils.isNetWork(InRepositoryDetailActivity.this)) {
            final HttpClientUtils httpClientUtil = new HttpClientUtils();
            try {
                /*请求服务端*/
                Map<String, String> paras = new HashMap<String, String>();
                paras.put("type", "1");
                paras.put("repositoryBillId", repositoryBillId);
                paras.put("repositoryId", rpositoryBillModel.repositoryId.toString());
                paras.put("operateDate", lastSearchDate);
                final List<RpositoryBillItemModel> billItemList = DBManager.getInstance(this).queryInBillItemAndScanDetailList(repositoryBillId, EnumQueryType.in_repository_bill.getVal() + "");
                Map<String, Object> map = null;
                for (RpositoryBillItemModel model :
                        billItemList) {
                    for (RpositoryBillItemDetailModel detailModel :
                            model.rpositoryBillItemDetailModelList) {
                        if (detailModel.state == 0) {
                            //未扫的单据详情不要传
                            model.rpositoryBillItemDetailModelList.remove(detailModel);
                        }
                    }
                    map = new HashMap<String, Object>();
                    map.put("itemDetailArr", model.rpositoryBillItemDetailModelList);
                    model.map = map;
                    model.rpositoryBillItemDetailModelList = null;
                }
                paras.put("itemListJSON", JSONObject.toJSONString(billItemList, SerializerFeature.DisableCircularReferenceDetect));
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_save_bill);
                try {
                    httpClientUtil.postRequest(requestUrl, paras);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.show();//启动加载进度动画

            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                Toast.makeText(InRepositoryDetailActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {

            }
            httpClientUtil.setOnGetData(new HttpClientUtils.OnGetResponseData() {
               @Override
               public void onGetData(String result) {
                   if (result == null) {
                       progressDialog.dismiss(); //关闭加载进度动画
                       forToast("http报错："+getString(R.string.server_connect_error));
                   } else {
                       try {
                           JsonRequestResult jsonRequestResult = JsonRequestResult.toJsonRequestResult(result);
                           if (jsonRequestResult.getCode() == 1) {
                               DBManager.getInstance(InRepositoryDetailActivity.this).updateBillState(repositoryBillId.toString(), "3", EnumQueryType.in_repository_bill.getVal() + "");
                               mHandler.sendEmptyMessage(MessageWhat.MessageType.SAVE_BILL_SUCCESS);
                               progressDialog.dismiss();
                           } else {
                               new android.support.v7.app.AlertDialog.Builder(InRepositoryDetailActivity.this)
                                       .setTitle("提示")
                                       .setMessage(jsonRequestResult.getMsg())
                                       .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               dialog.dismiss();
                                           }
                                       })
                                       .create().show();
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

    private void approveInBill() {
        if (NetWorkUtils.isNetWork(InRepositoryDetailActivity.this)) {
            HttpClientUtils httpClientUtil = new HttpClientUtils();
            try {
                /*请求服务端*/
                Map<String, String> paras = new HashMap<String, String>();
                paras.put("state", "2");
                paras.put("repositoryBillId", repositoryBillId);
                paras.put("auditMemo", lastSearchDate + "：扫码枪端审核");
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_approve_bill);
                httpClientUtil.postRequest(requestUrl, paras);
                progressDialog.show();//启动加载进度动画
            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                Toast.makeText(InRepositoryDetailActivity.this, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
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
                                                            progressDialog.dismiss();
                                                            mHandler.sendEmptyMessage(MessageWhat.MessageType.APPROVE_BILL_SUCCESS);
                                                        } else {
                                                            new android.support.v7.app.AlertDialog.Builder(InRepositoryDetailActivity.this)
                                                                    .setTitle("提示")
                                                                    .setMessage(jsonRequestResult.getMsg())
                                                                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                        }
                                                                    })
                                                                    .create().show();
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
