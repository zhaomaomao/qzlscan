package com.quanzhilian.qzlscan.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.inrepository.AllInRepositoryListActivity;
import com.quanzhilian.qzlscan.activities.inrepository.InRepositoryDetailActivity;
import com.quanzhilian.qzlscan.activities.inrepository.InRepositoryListActivity;
import com.quanzhilian.qzlscan.activities.machining.MachiningDetailActivity;
import com.quanzhilian.qzlscan.activities.machining.MachiningListActivity;
import com.quanzhilian.qzlscan.activities.outrepository.OutRepositoryDetailActivity;
import com.quanzhilian.qzlscan.activities.outrepository.OutRepositoryListActivity;
import com.quanzhilian.qzlscan.adapter.InRepositoryAdapter;
import com.quanzhilian.qzlscan.adapter.MachingRepositoryAdapter;
import com.quanzhilian.qzlscan.adapter.OutRepositoryAdapter;
import com.quanzhilian.qzlscan.base.BaseFragment;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillModel;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillModel;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.utils.GlobleCache;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangtm on 2018/2/1.
 */

public class ContentFragment extends BaseFragment {
    private View view;
    private static final String POSITION_KEY = "positon";
    private static final String TYPY_KEY = "type";//单据类型：同枚举类型EnumQueryType
    private String state = "1,2,3";
    private int queryType = EnumQueryType.in_repository_bill.getVal();

    private ListView lv_all_in_bill;
    //入库单
    List<RpositoryBillModel> repositoryBillModelList;
    InRepositoryAdapter inRepositoryAdapter;
    //出库单
    OutRepositoryAdapter outRepositoryAdapter;
    //加工单
    MachingRepositoryAdapter machingRepositoryAdapter;

    Class jumpDetailActivity = null;

    private ProgressDialog progressDialog;//加载进度动画；

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.contentfragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        Bundle args = getArguments();
        if (args != null) {
            if (args.getInt(POSITION_KEY) == 1) {
                state = "1";
            } else if (args.getInt(POSITION_KEY) == 2) {
                state = "3";
            } else {
                state = "1,2,3";
            }
            queryType = args.getInt(TYPY_KEY);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        queryList();
    }

    private void initView(View view) {
        lv_all_in_bill = (ListView) view.findViewById(R.id.lv_all_in_bill);
        lv_all_in_bill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RpositoryBillModel rpositoryBillModel = repositoryBillModelList.get(position);

                Intent detailActivityIntent = new Intent(getActivity(), jumpDetailActivity);
                Bundle bundle = new Bundle();
                //bundle.putSerializable("rpositoryBillModel", rpositoryBillModel);
                bundle.putString("repositoryBillId",rpositoryBillModel.repositoryBillId.toString());
                detailActivityIntent.putExtras(bundle);
                startActivity(detailActivityIntent);
            }
        });
    }

    /**
     * fragment静态传值
     */
    public static ContentFragment newInstance(int position, int queryType) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION_KEY, position);
        bundle.putInt(TYPY_KEY, queryType);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 查询列表
     */
    private void queryList() {
        if (EnumQueryType.in_repository_bill.getVal() == queryType) {
            getAllInBillList();
            jumpDetailActivity = InRepositoryDetailActivity.class;
        } else if (EnumQueryType.out_repository_bill.getVal() == queryType) {
            getAllOutBillList();
            jumpDetailActivity = OutRepositoryDetailActivity.class;
        } else if (EnumQueryType.maching_bill.getVal() == queryType) {
            getAllMachingBillList();
            jumpDetailActivity = MachiningDetailActivity.class;
        }

    }

    private void getAllInBillList() {
        String scmId = GlobleCache.getInst().getScmId();
        repositoryBillModelList = DBManager.getInstance(getActivity()).queryBillList(scmId, GlobleCache.getInst().getCacheRepositoryId(), state,EnumQueryType.in_repository_bill.getVal()+"");
        if (repositoryBillModelList != null) {
            if (inRepositoryAdapter == null) {
                inRepositoryAdapter = new InRepositoryAdapter(getActivity(), repositoryBillModelList);
                lv_all_in_bill.setAdapter(inRepositoryAdapter);
            } else {
                inRepositoryAdapter.repositoryBillModelList = repositoryBillModelList;
                inRepositoryAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 查询出库单列表
     */
    private void getAllOutBillList() {
        String scmId = GlobleCache.getInst().getScmId();
        repositoryBillModelList = DBManager.getInstance(getActivity()).queryBillList(scmId, GlobleCache.getInst().getCacheRepositoryId(), state,EnumQueryType.out_repository_bill.getVal()+"");
        if (repositoryBillModelList != null) {
            if (outRepositoryAdapter == null) {
                outRepositoryAdapter = new OutRepositoryAdapter(getActivity(), repositoryBillModelList);
                lv_all_in_bill.setAdapter(outRepositoryAdapter);
            } else {
                outRepositoryAdapter.repositoryBillModelList = repositoryBillModelList;
                outRepositoryAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 查询加工单列表
     */
    private void getAllMachingBillList() {
        String scmId = GlobleCache.getInst().getScmId();
        repositoryBillModelList = DBManager.getInstance(getActivity()).queryBillList(scmId, GlobleCache.getInst().getCacheRepositoryId(), state,EnumQueryType.maching_bill.getVal()+"");
        if (repositoryBillModelList != null) {
            if (machingRepositoryAdapter == null) {
                machingRepositoryAdapter = new MachingRepositoryAdapter(getActivity(), repositoryBillModelList);
                lv_all_in_bill.setAdapter(machingRepositoryAdapter);
            } else {
                machingRepositoryAdapter.repositoryBillModelList = repositoryBillModelList;
                machingRepositoryAdapter.notifyDataSetChanged();
            }
        }
    }
}
