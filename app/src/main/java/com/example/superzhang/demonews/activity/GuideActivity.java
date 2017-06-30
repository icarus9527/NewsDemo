package com.example.superzhang.demonews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.superzhang.demonews.R;

/**
 * Created by superzhang on 2017/5/28.
 */

public class GuideActivity extends Activity {
    private ViewFlipper guide_flipepr;
    private TextView startUse;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_guide);
        guide_flipepr = (ViewFlipper) findViewById(R.id.guide_viewflipper);
        guide_flipepr.startFlipping();

        startUse = (TextView) findViewById(R.id.txtStart);
        startUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
