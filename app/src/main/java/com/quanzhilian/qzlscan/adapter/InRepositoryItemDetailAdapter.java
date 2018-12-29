package com.quanzhilian.qzlscan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillItemModel;
import com.quanzhilian.qzlscan.models.enums.EnumQuantityUnit;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemModel;
import com.quanzhilian.qzlscan.utils.StringUtils;

import java.util.List;

/**
 * Created by yangtm on 2018/2/6.
 */

public class InRepositoryItemDetailAdapter extends BaseAdapter {
    private String productInfo;
    private RpositoryBillItemModel model;
    private LayoutInflater mInflater = null;
    private Context mContext;
    public List<RpositoryBillItemModel> repositoryBillItemModelList;
    TableRow tablerow = null;

    public InRepositoryItemDetailAdapter(Context mContext, List<RpositoryBillItemModel> repositoryBillItemModelList) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.repositoryBillItemModelList = repositoryBillItemModelList;
    }

    @Override
    public int getCount() {
        return repositoryBillItemModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return repositoryBillItemModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_in_repository_item_detail_list, null);
            holder.tv_product_info = (TextView) convertView.findViewById(R.id.tv_product_info);//产品信息
            holder.tl_product_item_detail = (TableLayout) convertView.findViewById(R.id.tl_product_item_detail);//产品明细信息
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        model = repositoryBillItemModelList.get(position);
        holder.tv_product_info.setText(model.productInfo);
        holder.tl_product_item_detail.setStretchAllColumns(true);
        holder.tl_product_item_detail.removeViews(1, holder.tl_product_item_detail.getChildCount() - 1);

        if (model.rpositoryBillItemDetailModelList != null) {
            for (RpositoryBillItemDetailModel detailModel : model.rpositoryBillItemDetailModelList) {
                tablerow = new TableRow(mContext);
                TextView tvBarCode = new TextView(mContext);
                tvBarCode.setGravity(Gravity.CENTER_HORIZONTAL);
                tvBarCode.setText(detailModel.barCode);
                tablerow.addView(tvBarCode);
                TextView tvLing = new TextView(mContext);
                tvLing.setGravity(Gravity.CENTER_HORIZONTAL);
                if (detailModel.amountLing >= 0) {
                    tvLing.setText(detailModel.amountLing + "");
                } else {
                    tvLing.setText("0");
                }
                tablerow.addView(tvLing);
                TextView tvWeight = new TextView(mContext);
                tvWeight.setGravity(Gravity.CENTER_HORIZONTAL);
                tvWeight.setText(detailModel.amountWeight + "");
                tablerow.addView(tvWeight);
                TextView tvRepositoryPosition = new TextView(mContext);
                tvRepositoryPosition.setGravity(Gravity.CENTER_HORIZONTAL);
                if (detailModel.repositoryPositionName != null) {
                    tvRepositoryPosition.setText(detailModel.repositoryPositionName);
                } else {
                    tvRepositoryPosition.setText("--");
                }
                tablerow.addView(tvRepositoryPosition);
                TextView tvState = new TextView(mContext);
                tvState.setGravity(Gravity.CENTER_HORIZONTAL);
                if(detailModel.state == 1){
                    tvState.setText(Html.fromHtml("<font color='#01e19f'>已扫</font>"));
                }else{
                    tvState.setText(Html.fromHtml("<font color='#fb0404'>未扫</font>"));
                }
                tablerow.addView(tvState);
                holder.tl_product_item_detail.addView(tablerow, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
//        holder.tv_create_datetime.setText("创建时间："+ ParseTimeUtil.getDayToStamp(repositoryBillModelList.get(position).createDate,"yyyy-MM-dd HH:mm:ss"));//创建时间
//        holder.tv_repository_name.setText("入库仓库："+repositoryBillModelList.get(position).repositoryName);//仓库名称
//        holder.tv_bill_state_title.setTextColor(mContext.getResources().getColor(R.color.in_scan_default));
        return convertView;
    }

    class ViewHolder {
        private TextView tv_product_info;
        private TableLayout tl_product_item_detail;
    }
}
