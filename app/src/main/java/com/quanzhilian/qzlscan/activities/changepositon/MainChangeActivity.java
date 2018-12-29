package com.quanzhilian.qzlscan.activities.changepositon;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.inrepository.InScanResultActivity;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.base.BaseScanActivity;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.scan.ClientConfig;
import com.quanzhilian.qzlscan.utils.GlobleCache;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.StringUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;
import com.scandecode.ScanDecode;
import com.scandecode.inf.ScanInterface;

import java.util.HashMap;
import java.util.Map;

public class MainChangeActivity extends BaseScanActivity implements View.OnClickListener {
    //头部公共区域
    private RelativeLayout rl_common_header_title_bar;
    private LinearLayout ll_titilebar_back;
    private TextView common_header_title;
    private LinearLayout ll_titilebar_button;
    private TextView tv_titilebar_right;
    private ImageView im_titilebar_right;

    private TextView tv_repository_name;
    private Button btn_change_repository;

    private LinearLayout ll_scan_product;

    private static final int SHOW_REPOSITORY = 1;
    private static final int TIME = 3000;

    private ProgressDialog progressDialog;//加载进度动画；

    private ScanInterface scanDecode;
    private boolean isScanActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_change);
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
        ll_titilebar_button = (LinearLayout) findViewById(R.id.ll_titilebar_button);
        tv_titilebar_right = (TextView) findViewById(R.id.tv_titilebar_right);
        im_titilebar_right = (ImageView) findViewById(R.id.im_titilebar_right);
        tv_titilebar_right.setText("切换仓库");
        tv_titilebar_right.setVisibility(View.VISIBLE);
        im_titilebar_right.setImageDrawable(getResources().getDrawable(R.mipmap.change_repository));
        rl_common_header_title_bar.setBackgroundColor(getResources().getColor(R.color.change_default));
        common_header_title.setText(getResources().getString(R.string.change_scan));

        ll_titilebar_button.setVisibility(View.VISIBLE);

        ll_titilebar_back.setOnClickListener(this);
        ll_titilebar_button.setOnClickListener(this);
    }

    /**
     * 初始化页面
     */
    private void initView() {
        scanDecode = new ScanDecode(this);
        try{ 	scanDecode.initService("true"); }catch(Exception e){ 	 }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("产品信息下载中...");
        tv_repository_name = (TextView) findViewById(R.id.tv_repository_name);
        //btn_change_repository = (Button) findViewById(R.id.btn_change_repository);
        ll_scan_product = (LinearLayout) findViewById(R.id.ll_scan_product);

        //btn_change_repository.setOnClickListener(this);
        //ll_scan_product.setOnClickListener(this);
        showRepository();

        ll_scan_product.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP: {
                        scanDecode.stopScan();//停止扫描
                        handler.removeCallbacks(startTask);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        scanDecode.starScan();//启动扫描
                        break;
                    }

                    default:
                        break;
                }
                return false;
            }
        });

        scanDecode.getBarCode(new ScanInterface.OnScanListener() {
            @Override
            public void getBarcode(String billNo) {
                if (isScanActive) {
                    if (!StringUtils.isEmpty(billNo)) {
                        scanInProduct(billNo);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_titilebar_back:
                onBackPressed();
                break;
            case R.id.ll_titilebar_button:
                getRepositoryList(this, mHandler, true);
                break;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // progressDialog.dismiss();
            switch (msg.what) {
                case SHOW_REPOSITORY:
                    //刷新仓库
                    showRepository();
                    forToast("仓库已切换成：" + GlobleCache.getInst().getCacheRepositoryName());
                    break;
            }
        }

    };

    Handler handler = new Handler();

    //连续扫描
    private Runnable startTask = new Runnable() {
        @Override
        public void run() {
            scanDecode.starScan();
            handler.postDelayed(startTask, 1000);
        }
    };


    /**
     * 从repositorySP显示仓库信息
     */
    private void showRepository() {
        String repositoryName = GlobleCache.getInst().getCacheRepositoryName();
        tv_repository_name.setText("当前仓库：" + repositoryName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isScanActive = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanDecode.onDestroy();//回复初始状态
    }

    /**
     * 扫码入库产品
     */
    private void scanInProduct(String billNo) {
        //跳转到扫描结果页
        isScanActive = false;
        Bundle bundle = new Bundle();
        bundle.putString("bar_code", billNo);
        jumpActivity(ChangeScanResultActivity.class, bundle);
    }
}
