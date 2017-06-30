package com.example.superzhang.demonews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.superzhang.demonews.R;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setToolBar();
        
    }

    private void setToolBar() {
        toolBar = (Toolbar) findViewById(R.id.loginToolBar);
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
    }

}
