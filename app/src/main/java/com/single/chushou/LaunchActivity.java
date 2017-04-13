package com.single.chushou;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by xiangcheng on 17/4/12.
 */

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanuch);
        findViewById(R.id.cs_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaunchActivity.this, ChuShouImageActivity.class));
            }
        });

        findViewById(R.id.cs_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaunchActivity.this, ChuShouScrollActivity.class));
            }
        });
    }
}
