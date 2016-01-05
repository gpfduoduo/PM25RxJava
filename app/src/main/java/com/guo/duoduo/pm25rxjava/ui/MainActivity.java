package com.guo.duoduo.pm25rxjava.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guo.duoduo.pm25rxjava.R;
import com.guo.duoduo.pm25rxjava.entity.PM25BaseInfo;
import com.guo.duoduo.pm25rxjava.utils.CityManager;
import com.guo.duoduo.pm25rxjava.utils.HttpUrl;
import com.guo.duoduo.pm25rxjava.utils.PM25Url;
import com.guo.duoduo.pm25rxjava.view.CustomGaugeView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String tag = MainActivity.class.getSimpleName();
    private TextView tvAddCity;
    private TextView tvAirResult;
    private ProgressBar mProgressBar;
    private CustomGaugeView mGaugeView;
    private Subscription mSubscription;
    private TextView mTvCurCity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initWidget();
        initData();
    }

    private void initWidget()
    {
        tvAddCity = (TextView) findViewById(R.id.tv_click_add_city);
        tvAddCity.setOnClickListener(this);
        tvAirResult = (TextView) findViewById(R.id.air_result);
        mGaugeView = (CustomGaugeView) findViewById(R.id.gauge_view);
        mGaugeView.setVisibility(View.GONE);
        mGaugeView.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTvCurCity = (TextView) findViewById(R.id.tv_cur_city);
        mTvCurCity.setOnClickListener(this);
    }

    private void initData()
    {
        String city = CityManager.getInstance(getApplicationContext()).getCity();
        if (TextUtils.isEmpty(city))
        {
            tvAddCity.setVisibility(View.VISIBLE);
            tvAirResult.setVisibility(View.GONE);
            mGaugeView.setVisibility(View.GONE);
        }
        else
        {
            mTvCurCity.setText(city);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String city = CityManager.getInstance(getApplicationContext()).getCity();
        Log.d(tag, "onResume city name=" + city);
        if (!TextUtils.isEmpty(city))
        {
            Log.d(tag, "city = " + city);
            tvAddCity.setVisibility(View.GONE);
            tvAirResult.setVisibility(View.VISIBLE);
            mTvCurCity.setText(city);
            getCityAir(city);
        }
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_click_add_city :
                Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.gauge_view :
                startActivity(new Intent(MainActivity.this, DetailActivity.class));
                break;
            case R.id.tv_cur_city :
                startActivity(new Intent(MainActivity.this, AddCityActivity.class));
                break;
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed())
        {
            mSubscription.unsubscribe();
        }
    }

    private void getCityAir(final String city)
    {
        mProgressBar.setVisibility(View.VISIBLE);
        mSubscription = Observable.create(new rx.Observable.OnSubscribe<PM25BaseInfo>()
        {
            @Override
            public void call(Subscriber<? super PM25BaseInfo> subscriber)
            {
                if (subscriber.isUnsubscribed())
                    return;

                try
                {
                    String cityAir = HttpUrl.getData(
                        PM25Url.getCityPM25BaseInfo(PM25Url.ChineseToSpell(city)));
                    Log.d(tag, "city: " + city + " air: " + cityAir);
                    JSONArray jsonArray = JSONArray.parseArray(cityAir);//返回的是json数组
                    if (jsonArray != null && jsonArray.size() == 1)
                    {
                        JSONObject object = (JSONObject) jsonArray.get(0);
                        PM25BaseInfo pm25BaseInfo = JSON.toJavaObject(object,
                            PM25BaseInfo.class);
                        subscriber.onNext(pm25BaseInfo);
                        subscriber.onCompleted();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())

        .subscribe(new Subscriber<PM25BaseInfo>()
        {
            @Override
            public void onCompleted()
            {
            }

            @Override
            public void onError(Throwable e)
            {
                Log.d(tag, "error: " + e.toString());
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onNext(PM25BaseInfo pm25BaseInfo)
            {
                Log.d(tag, "air data = " + pm25BaseInfo.toString());
                mProgressBar.setVisibility(View.GONE);
                tvAirResult.setText(pm25BaseInfo.toString());
                mGaugeView.setVisibility(View.VISIBLE);
                mGaugeView.setValue(pm25BaseInfo.getPm2_5());
            }
        });
    }
}
