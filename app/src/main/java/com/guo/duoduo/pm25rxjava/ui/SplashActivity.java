package com.guo.duoduo.pm25rxjava.ui;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.guo.duoduo.pm25rxjava.R;


public class SplashActivity extends AppCompatActivity
{

    private TextView tvSplash;
    private static final int DELAY = 2;

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
        tvSplash = (TextView) findViewById(R.id.tv_splash);
    }
}
