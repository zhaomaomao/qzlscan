package com.quanzhilian.qzlscan.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.models.enums.EnumQuantityUnit;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemModel;
import com.quanzhilian.qzlscan.utils.StringUtils;

import java.util.List;

/**
 * Created by yangtm on 2018/2/6.
 *
 * 已扫描详情列表
 */

public class RepositoryDetailAdapter extends BaseAdapter {
    private String productInfo;
    private RpositoryBillItemDetailModel model;
    private LayoutInflater mInflater = null;
    private Context mContext;
    public List<RpositoryBillItemDetailModel> repositoryBillItemDetailModelList;

    public RepositoryDetailAdapter(Context mContext, List<RpositoryBillItemDetailModel> repositoryBillItemDetailModelList) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.repositoryBillItemDetailModelList = repositoryBillItemDetailModelList;
    }

    @Override
    public int getCount() {
        return repositoryBillItemDetailModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return repositoryBillItemDetailModelList.get(position);
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
            convertView = mInflater.inflate(R.layout.item_repository_detail_list, null);
            holder.tv_bar_code = (TextView) convertView.findViewById(R.id.tv_bar_code);//产品信息
            holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);//总件数
            holder.tv_position = (TextView) convertView.findViewById(R.id.tv_position);//已扫数量
            holder.tv_out_type = (TextView) convertView.findViewById(R.id.tv_out_type);
            holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        model = repositoryBillItemDetailModelList.get(position);
        holder.tv_bar_code.setText(model.barCode);
        holder.tv_amount.setText("件重(吨)：" + model.amountWeight + "");
        holder.tv_position.setText("库位：" + (StringUtils.isEmpty(model.repositoryPositionName) ? "--" : model.repositoryPositionName));
        if(model.type == EnumQueryType.out_repository_bill.getVal()){
            if(model.isFull == 0){
                holder.tv_out_type.setText(Html.fromHtml("出库方式：<font color='#fb0404'>拆零出</font>"));
            }else{
                holder.tv_out_type.setText(Html.fromHtml("出库方式：<font color='#5eb95e'>整件出</font>"));
            }
        }else{
            holder.tv_out_type.setVisibility(View.GONE);
        }

        if (model.state == 1) {
            holder.tv_state.setText(Html.fromHtml("<font color='#5eb95e'>已扫</font>"));
        } else {
            holder.tv_state.setText(Html.fromHtml("<font color='#fb0404'>未扫</font>"));
        }
//        holder.tv_create_datetime.setText("创建时间："+ ParseTimeUtil.getDayToStamp(repositoryBillModelList.get(position).createDate,"yyyy-MM-dd HH:mm:ss"));//创建时间
//        holder.tv_repository_name.setText("入库仓库："+repositoryBillModelList.get(position).repositoryName);//仓库名称
//        holder.tv_bill_state_title.setTextColor(mContext.getResources().getColor(R.color.in_scan_default));
        return convertView;
    }

    class ViewHolder {
        private TextView tv_bar_code;
        private TextView tv_amount;
        private TextView tv_position;
        private TextView tv_out_type;
        private TextView tv_state;
    }
}
