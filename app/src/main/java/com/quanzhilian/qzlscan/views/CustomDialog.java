package com.quanzhilian.qzlscan.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * @author wo2app
 * @date 创建时间：2015年2月18日 上午11:14:20
 * @version 1.0
 * @parameter
 * @since
 * @return
 */

public class CustomDialog extends Dialog {

	Context context;

	public CustomDialog(Context context) {
		super(context);
		this.context = context;

	}

	public CustomDialog(Context context, int defStyle) {
		super(context, defStyle);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void setView(View view) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(view);
	}

	public void setView(int layout) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(layout);
	}
}
