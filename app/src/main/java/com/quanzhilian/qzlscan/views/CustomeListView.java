package com.quanzhilian.qzlscan.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class CustomeListView extends ListView {

	public CustomeListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomeListView(Context context) {
		super(context);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	@Override
	public void requestLayout() {
		super.requestLayout();
	}
}
