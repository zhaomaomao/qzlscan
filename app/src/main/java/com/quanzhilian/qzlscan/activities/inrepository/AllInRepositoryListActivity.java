package com.quanzhilian.qzlscan.activities.inrepository;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.adapter.InRepositoryAdapter;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillModel;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllInRepositoryListActivity extends BaseActivity {

    private ListView lv_all_in_bill;

    List<SimpleRepositoryBillModel> repositoryBillModelList;
    InRepositoryAdapter inRepositoryAdapter;
    private ProgressDialog progressDialog;//加载进度动画；

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_in_repository_list);
        initView();
    }

    private void initView() {
        lv_all_in_bill = (ListView) findViewById(R.id.lv_all_in_bill);
        progressDialog = new ProgressDialog(AllInRepositoryListActivity.this);
        lv_all_in_bill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SimpleRepositoryBillModel memberRuleModel = repositoryBillModelList.get(position);
//                    Intent addMemberCardRuleIntent = new Intent(mContext, AddMemberCardRuleActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("memberRuleEdit", memberRuleModel);
//                    addMemberCardRuleIntent.putExtras(bundle);
//                    startActivityForResult(addMemberCardRuleIntent, 11);
            }
        });
    }
}
