package com.quanzhilian.qzlscan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.quanzhilian.qzlscan.fragment.ContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangtm on 2018/2/1.
 */

public class FragmentVPAdapter extends FragmentPagerAdapter {
    private ArrayList<ContentFragment> fragments;
    private FragmentManager fm;
    List<String> mTitleList;
    public FragmentVPAdapter(FragmentManager fm, ArrayList<ContentFragment> fragments,List<String> mTitleList) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
        this.mTitleList = mTitleList;
    }

    public void setFragments(ArrayList<ContentFragment> fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
