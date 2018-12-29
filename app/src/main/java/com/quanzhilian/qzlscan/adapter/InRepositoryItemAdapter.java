package com.quanzhilian.qzlscan.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillItemModel;
import com.quanzhilian.qzlscan.models.enums.EnumQuantityUnit;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillModel;
import com.quanzhilian.qzlscan.utils.ParseTimeUtil;
import com.quanzhilian.qzlscan.utils.StringUtils;

import java.util.List;

/**
 * Created by yangtm on 2018/2/6.
 */

public class InRepositoryItemAdapter extends BaseAdapter {
    private String productInfo;
    private RpositoryBillItemModel model;
    private LayoutInflater mInflater = null;
    private Context mContext;
    public List<RpositoryBillItemModel> repositoryBillItemModelList;

    public InRepositoryItemAdapter(Context mContext, List<RpositoryBillItemModel> repositoryBillItemModelList) {
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
            convertView = mInflater.inflate(R.layout.item_in_repository_item_list, null);
            holder.tv_product_info = (TextView) convertView.findViewById(R.id.tv_product_info);//产品信息
            holder.tv_total_count = (TextView) convertView.findViewById(R.id.tv_total_count);//总件数
            holder.tv_scan_count = (TextView) convertView.findViewById(R.id.tv_scan_count);//已扫数量
            holder.ll_nva = (LinearLayout) convertView.findViewById(R.id.ll_nva);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        model = repositoryBillItemModelList.get(position);
        String unitName = "";
        if (model.quantityUnit != null) {
            unitName = EnumQuantityUnit.getValueById(model.quantityUnit).getName();
        }

        holder.tv_product_info.setText(model.productInfo);
        holder.tv_total_count.setText(Html.fromHtml("共<font color='#fb0404'>" + model.quantity + "</font>件"));
        if(model.type == EnumQueryType.in_repository_bill.getVal()){
            holder.tv_total_count.setVisibility(View.VISIBLE);
        }else {
            holder.tv_total_count.setVisibility(View.GONE);
        }
        if(model.type == EnumQueryType.in_repository_bill.getVal()){
            holder.ll_nva.setVisibility(View.GONE);
        }else{
            holder.ll_nva.setVisibility(View.VISIBLE);
        }
        holder.tv_scan_count.setText(Html.fromHtml("已扫<font color='#01e19f'>" + model.scanQuantity + "</font>件"));
//        holder.tv_create_datetime.setText("创建时间："+ ParseTimeUtil.getDayToStamp(repositoryBillModelList.get(position).createDate,"yyyy-MM-dd HH:mm:ss"));//创建时间
//        holder.tv_repository_name.setText("入库仓库："+repositoryBillModelList.get(position).repositoryName);//仓库名称
//        holder.tv_bill_state_title.setTextColor(mContext.getResources().getColor(R.color.in_scan_default));
        return convertView;
    }

    class ViewHolder {
        private TextView tv_product_info;
        private TextView tv_total_count;
        private TextView tv_scan_count;
        private LinearLayout ll_nva;
    }
}
