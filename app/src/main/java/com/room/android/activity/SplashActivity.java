package com.room.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.room.android.R;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout mBallonLayout;
    private LinearLayout mBdayLayout;
    private Animation uptodown,downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        mBallonLayout = findViewById(R.id.ballonLayout);
        mBdayLayout = findViewById(R.id.bdayLayout);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        mBallonLayout.setAnimation(uptodown);
       mBdayLayout.setAnimation(downtoup);
    }
}
