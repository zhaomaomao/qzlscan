package com.quanzhilian.qzlscan.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillModel;
import com.quanzhilian.qzlscan.utils.AppUtils;
import com.quanzhilian.qzlscan.utils.ParseTimeUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 */

public class InRepositoryAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private Context mContext;
    public List<RpositoryBillModel> repositoryBillModelList;

    public InRepositoryAdapter(Context mContext, List<RpositoryBillModel> repositoryBillModelList){
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.repositoryBillModelList = repositoryBillModelList;
    }

    @Override
    public int getCount() {
        return repositoryBillModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return repositoryBillModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_in_repository_list, null);
            holder.tv_bill_no = (TextView) convertView.findViewById(R.id.tv_bill_no);//入库单号
            holder.tv_company_name = (TextView) convertView.findViewById(R.id.tv_company_name);//供货单位
            holder.tv_create_datetime = (TextView) convertView.findViewById(R.id.tv_create_datetime);//创建时间
            holder.tv_repository_name = (TextView) convertView.findViewById(R.id.tv_repository_name);//仓库名称
            holder.tv_bill_state_title = (TextView) convertView.findViewById(R.id.tv_bill_state_title);//单据扫码状态标题
            holder.tv_bill_state = (TextView) convertView.findViewById(R.id.tv_bill_state);//单据扫码状态
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_bill_no.setText("入库单号："+repositoryBillModelList.get(position).repositoryBillNo);//入库单号
        holder.tv_company_name.setText("供货单位："+repositoryBillModelList.get(position).relativeCompanyName);//供货单位
        holder.tv_create_datetime.setText("创建时间："+ ParseTimeUtil.getDayToStamp(repositoryBillModelList.get(position).createDate,"yyyy-MM-dd HH:mm:ss"));//创建时间
        holder.tv_repository_name.setText("入库仓库："+repositoryBillModelList.get(position).repositoryName);//仓库名称
        //holder.tv_bill_state_title.setTextColor(mContext.getResources().getColor(R.color.in_scan_default));
        String stateName = "未扫码入库";
        if(repositoryBillModelList.get(position).state == 1){
            stateName = "<font color='#0219fb'>未扫码入库</font>";
        } else if(repositoryBillModelList.get(position).state == 2){
            stateName = "<font color='#0219fb'>扫码入库中</font>";
        } else {
            stateName = "待入库审核";
        }
        holder.tv_bill_state.setText(Html.fromHtml(stateName));
        return convertView;
    }

    class ViewHolder {
        private TextView tv_bill_no;
        private TextView tv_company_name;
        private TextView tv_create_datetime;
        private TextView tv_repository_name;
        private TextView tv_bill_state_title;
        private TextView tv_bill_state;
    }
}
