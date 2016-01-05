package com.guo.duoduo.pm25rxjava.ui;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.guo.duoduo.pm25rxjava.R;


public class DetailActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvDetail = (TextView) findViewById(R.id.tv_detail);
    }
}
