package com.guo.duoduo.pm25rxjava;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


public class MainActivity extends AppCompatActivity
{

    private static final String tag = MainActivity.class.getSimpleName();

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

    }

    private void initData()
    {
        String[] nameArray = {"Panda Guo", "Fang Tai"};
        Observable.from(nameArray).subscribe(new Action1<String>()
        {
            @Override
            public void call(String s)
            {
                Log.d(tag, "name =" + s);
            }
        });

        Observable.just(1, 2, 3, 4).subscribeOn(Schedulers.io()) //时间产生的线程
                .observeOn(AndroidSchedulers.mainThread()) //事件消费的线程
                .subscribe(new Action1<Integer>()
                {
                    @Override
                    public void call(Integer integer)
                    {
                        Log.d(tag, "number = " + integer);
                    }
                });
    }
}
