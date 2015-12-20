package com.guo.duoduo.pm25rxjava.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.guo.duoduo.pm25rxjava.R;
import com.guo.duoduo.pm25rxjava.utils.CityManager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String tag = MainActivity.class.getSimpleName();

    private TextView tvAddCity;

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
    }

    private void initData()
    {
        if (TextUtils.isEmpty(CityManager.getInstance(getApplicationContext()).getCity()))
            tvAddCity.setVisibility(View.VISIBLE);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_click_add_city :
                startActivity(new Intent(MainActivity.this, AddCityActivity.class));
                break;
        }

    }
}
