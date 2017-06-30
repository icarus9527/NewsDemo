package com.example.superzhang.demonews.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superzhang.demonews.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * Created by superzhang on 2017/5/21.
 */

public class ContentActivity extends AppCompatActivity{

    private static final int IS_LOGIN = 1;
    private com.tencent.smtt.sdk.WebView webView;
    private Toolbar toolBar;
    private TextView toolBarTitle;

    private String url;
    private String title;
    private String date;
    private String author_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        findViews();

        getData();

        //设置TOOLBAR
        setToolBar();

        loadUrl(url);
    }

    //设置TOOLBAR
    private void setToolBar() {
        setSupportActionBar(toolBar);
        //设置ToolBar的返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //隐藏app：label的内容
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolBarTitle.setText(title);
    }

    //获取intent数据
    public void getData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        author_name = intent.getStringExtra("author_name");
    }


    private void loadUrl(String url){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                Log.i("打印日志","网页加载失败");
            }
        });
        //进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    Log.i("打印日志","加载完成");
                }
            }
        });
        webView.loadUrl(url);
    }

    private void findViews() {
        webView = (WebView) findViewById(R.id.webView);
        toolBar = (Toolbar) findViewById(R.id.contentToolBar);
        toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) webView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    //设置菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.content_item,menu);
        return true;
    }

    //ToolBar返回按钮的响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnLogin:
                Intent intent = new Intent(ContentActivity.this,LoginActivity.class);
                startActivityForResult(intent,IS_LOGIN);
                break;

            case R.id.btnSet:
                Toast.makeText(this,"设置",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
