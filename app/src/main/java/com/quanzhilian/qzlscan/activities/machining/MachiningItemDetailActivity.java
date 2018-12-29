package com.quanzhilian.qzlscan.activities.machining;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.outrepository.OutRepositoryItemDetailActivity;
import com.quanzhilian.qzlscan.adapter.InRepositoryDetailAdapter;
import com.quanzhilian.qzlscan.adapter.InRepositoryItemDetailAdapter;
import com.quanzhilian.qzlscan.adapter.RepositoryDetailAdapter;
import com.quanzhilian.qzlscan.common.CommonRequest;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.enums.EnumQuantityUnit;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillModel;
import com.quanzhilian.qzlscan.utils.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class MachiningItemDetailActivity extends Activity implements View.OnClickListener {
    //头部公共区域
    private RelativeLayout rl_common_header_title_bar;
    private LinearLayout ll_titilebar_back;
    private TextView common_header_title;
    private LinearLayout ll_titilebar_button;
    private TextView tv_titilebar_right;
    private ImageView im_titilebar_right;

    private TextView tv_product_info;
    private TextView tv_total_count;
    private TextView tv_scan_count;
    private TextView tv_scan_ton;
    private TextView tv_scan_diff_ton;
    private ListView lv_bill_product_detail;


    private String repositoryCuttingId;
    private String repositoryCuttingItemId;
    private RpositoryBillItemModel repositoryBillItemModel;
    List<RpositoryBillItemDetailModel> rpositoryBillItemDetailModelList = null;
    RpositoryBillItemDetailModel model = null;

    RepositoryDetailAdapter detailAdapter = null;
    double scanTon = 0d;
    double scanDiffTon = 0d;

    Dialog mCameraDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machining_item_detail);

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

        rl_common_header_title_bar.setBackgroundColor(getResources().getColor(R.color.machining_default));
        common_header_title.setText(getResources().getString(R.string.machining_bill_detail));

        ll_titilebar_back.setOnClickListener(this);
        ll_titilebar_button.setOnClickListener(this);
    }

    private void initView() {
        if (getIntent().hasExtra("repositoryBillItemId")) {
            repositoryCuttingItemId = getIntent().getStringExtra("repositoryBillItemId");
        }
        if (getIntent().hasExtra("repositoryBillId")) {
            repositoryCuttingId = getIntent().getStringExtra("repositoryBillId");
        }

//        if (getIntent().hasExtra("repositoryBillItemModel")) {
//            repositoryBillItemModel = (RpositoryBillItemModel) getIntent().getSerializableExtra("repositoryBillItemModel");
//        }

        tv_product_info = (TextView) findViewById(R.id.tv_product_info);//产品信息
        tv_scan_ton = (TextView) findViewById(R.id.tv_scan_ton);//已扫吨数
        tv_scan_diff_ton = (TextView) findViewById(R.id.tv_scan_diff_ton);//差额吨数
        tv_scan_count = (TextView) findViewById(R.id.tv_scan_count);//已扫数量
        lv_bill_product_detail = (ListView) findViewById(R.id.lv_bill_product_detail);

        bindView();
    }

    private void bindView() {
        if (!StringUtils.isEmpty(repositoryCuttingItemId)) {
            scanTon = 0d;
            repositoryBillItemModel = DBManager.getInstance(this).queryInBillItem(repositoryCuttingItemId, EnumQueryType.maching_bill.getVal() + "");
            String unitName = "";
            if (repositoryBillItemModel.quantityUnit != null) {
                unitName = EnumQuantityUnit.getValueById(repositoryBillItemModel.quantityUnit).getName();
            }

            tv_product_info.setText(repositoryBillItemModel.productInfo);
            tv_scan_count.setText(Html.fromHtml("已扫<font color='#5eb95e'>" + repositoryBillItemModel.scanQuantity + "</font>件"));


            rpositoryBillItemDetailModelList = DBManager.getInstance(MachiningItemDetailActivity.this).queryInBillItemDetailList(repositoryCuttingItemId, EnumQueryType.maching_bill.getVal() + "","1");
            if (rpositoryBillItemDetailModelList != null) {
                for (RpositoryBillItemDetailModel model :
                        rpositoryBillItemDetailModelList) {
                    if (model.state == 1) {
                        scanTon += model.amountWeight;
                    }
                }

                tv_scan_ton.setText(Html.fromHtml("已扫<font color='#5eb95e'>" + new DecimalFormat("#,##0.00000").format(scanTon) + "</font>吨"));

                BigDecimal decimalScanTon = new BigDecimal(scanTon);
                BigDecimal decimalTon = new BigDecimal(repositoryBillItemModel.ton);

                if (decimalScanTon.compareTo(decimalTon) > 0) {
                    scanDiffTon = scanTon - repositoryBillItemModel.ton;
                    tv_scan_diff_ton.setText(Html.fromHtml("多扫了<font color='#fb0404'>" + new DecimalFormat("#,##0.00000").format(scanDiffTon) + "</font>吨"));//
                } else if (decimalScanTon.compareTo(decimalTon) < 0) {
                    scanDiffTon = repositoryBillItemModel.ton - scanTon;
                    tv_scan_diff_ton.setText(Html.fromHtml("还需<font color='#fb0404'>" + new DecimalFormat("#,##0.00000").format(scanDiffTon) + "</font>吨"));
                } else {
                    tv_scan_diff_ton.setVisibility(View.GONE);
                }
            } else {
                tv_scan_count.setText(Html.fromHtml("已扫<font color='#5eb95e'>0</font>件"));
                tv_scan_ton.setText(Html.fromHtml("已扫<font color='#5eb95e'>0</font>吨"));
                tv_scan_diff_ton.setVisibility(View.GONE);
            }
            if (detailAdapter == null) {
                detailAdapter = new RepositoryDetailAdapter(MachiningItemDetailActivity.this, rpositoryBillItemDetailModelList);
                lv_bill_product_detail.setAdapter(detailAdapter);
            } else {
                detailAdapter.repositoryBillItemDetailModelList = rpositoryBillItemDetailModelList;
                detailAdapter.notifyDataSetChanged();
            }

            lv_bill_product_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    model = rpositoryBillItemDetailModelList.get(position);
                    setDialog(model);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_titilebar_back:
                onBackPressed();
                break;
            case R.id.ll_titilebar_button:
                //清除已扫
                new android.support.v7.app.AlertDialog.Builder(MachiningItemDetailActivity.this)
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
                                DBManager.getInstance(MachiningItemDetailActivity.this).delBillDetailByItemId(repositoryCuttingId, repositoryCuttingItemId, EnumQueryType.maching_bill.getVal() + "");
                                //initViewData();
                                bindView();
                            }
                        })
                        .create().show();
            case R.id.tv_cancle:
                mCameraDialog.dismiss();
                break;
            case R.id.tv_second_oper:
                if (model.isFull == 1) {
                    //是整件出
                    DBManager.getInstance(this).updateBillDetailIsFullByRepositoryProductId(repositoryCuttingItemId, model.repositoryProductId.toString(), "0", EnumQueryType.maching_bill.getVal() + "");
                    bindView();
                } else {
                    //是拆零出
                    DBManager.getInstance(this).updateBillDetailIsFullByRepositoryProductId(repositoryCuttingItemId, model.repositoryProductId.toString(), "1", EnumQueryType.maching_bill.getVal() + "");
                    bindView();
                }
                mCameraDialog.dismiss();
                break;
            case R.id.tv_del_item:
                if (model != null) {
                    new android.support.v7.app.AlertDialog.Builder(MachiningItemDetailActivity.this)
                            .setTitle("提示")
                            .setMessage("确认删除该产品？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DBManager.getInstance(MachiningItemDetailActivity.this).delBillDetailByRepositoryProductId(repositoryCuttingItemId, model.repositoryProductId.toString(), EnumQueryType.maching_bill.getVal() + "");
                                    bindView();
                                }
                            })
                            .create().show();
                }
                mCameraDialog.dismiss();
                break;
        }
    }

    private void setDialog(RpositoryBillItemDetailModel model) {
        mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.bottom_dialog, null);
        //初始化视图
        root.findViewById(R.id.tv_del_item).setOnClickListener(this);
        TextView tv_second_oper = (TextView) root.findViewById(R.id.tv_second_oper);
        tv_second_oper.setVisibility(View.GONE);
        tv_second_oper.setOnClickListener(this);
        root.findViewById(R.id.tv_cancle).setOnClickListener(this);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }
}
