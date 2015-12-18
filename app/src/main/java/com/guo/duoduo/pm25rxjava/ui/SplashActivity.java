package com.guo.duoduo.pm25rxjava.ui;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.guo.duoduo.pm25rxjava.R;


public class SplashActivity extends AppCompatActivity
{

    private TextView tvSplash;
    private ImageView imgSplash;
    private static final int DELAY = 2;
    private static final int ANIMATION = DELAY * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initWidget();

        Observable.timer(DELAY, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>()
                {
                    @Override
                    public void onCompleted()
                    {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                    }

                    @Override
                    public void onNext(Long aLong)
                    {
                    }
                });
    }

    private void initWidget()
    {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(ANIMATION);
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -150);
        animation.setDuration(ANIMATION);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alphaAnimation);
        set.addAnimation(animation);
        set.setFillAfter(true);
        tvSplash = (TextView) findViewById(R.id.tv_splash);
        tvSplash.startAnimation(set);
        imgSplash = (ImageView) findViewById(R.id.img_splash);
        imgSplash.startAnimation(set);
    }

    public void onDestroy()
    {
        super.onDestroy();
        tvSplash.clearAnimation();
        imgSplash.clearAnimation();
    }
}
