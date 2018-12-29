package com.quanzhilian.qzlscan.activities.machining;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.adapter.FragmentVPAdapter;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.base.BaseAppCompatActivity;
import com.quanzhilian.qzlscan.fragment.ContentFragment;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;

import java.util.ArrayList;
import java.util.List;

public class MachiningListActivity extends BaseAppCompatActivity implements View.OnClickListener {
    //头部公共区域
    private RelativeLayout rl_common_header_title_bar;
    private LinearLayout ll_titilebar_back;
    private TextView common_header_title;
    private LinearLayout ll_titilebar_button;
    private TextView tv_titilebar_right;
    private ImageView im_titilebar_right;
    private ProgressDialog progressDialog;//加载进度动画；

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private ArrayList<ContentFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machining_list);
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


        rl_common_header_title_bar.setBackgroundColor(getResources().getColor(R.color.machining_default));
        common_header_title.setText(getResources().getString(R.string.machining_bill_list));

        ll_titilebar_back.setOnClickListener(this);
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        fragments = new ArrayList<ContentFragment>();
        mViewPager = (ViewPager) findViewById(R.id.vp_view);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        //添加页卡标题
        mTitleList.add("全部");
        mTitleList.add("未扫码");
        mTitleList.add("待审核");

        for (int i = 0; i < mTitleList.size(); i++) {
            ContentFragment fragment = ContentFragment.newInstance(i, EnumQueryType.maching_bill.getVal());
            fragments.add(fragment);
        }


        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

        FragmentPagerAdapter mAdapter = new FragmentVPAdapter(getSupportFragmentManager(), fragments, mTitleList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_titilebar_back:
                onBackPressed();
                break;
        }
    }
}
