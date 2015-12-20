package com.guo.duoduo.pm25rxjava.ui;


import java.io.IOException;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.guo.duoduo.pm25rxjava.R;
import com.guo.duoduo.pm25rxjava.adapter.CityAdapter;
import com.guo.duoduo.pm25rxjava.entity.City;
import com.guo.duoduo.pm25rxjava.utils.HttpUrl;
import com.guo.duoduo.pm25rxjava.utils.MediaIndexer;
import com.guo.duoduo.pm25rxjava.utils.PM25Url;


public class AddCityActivity extends BaseActivity implements View.OnClickListener
{
    private static final String tag = AddCityActivity.class.getSimpleName();
    private ProgressBar mProgressBar;
    private EditText mEtCity;
    private Button mSearch;
    private ListView mLvCity;
    private CityAdapter mCityAdapter;
    private List<City> mList;
    private MediaIndexer mMediaIndex;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidget();
        getSupportCity();
    }

    private void initWidget()
    {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEtCity = (EditText) findViewById(R.id.et_search);
        mSearch = (Button) findViewById(R.id.btn_search);
        mSearch.setOnClickListener(this);
        mLvCity = (ListView) findViewById(R.id.lv_city);
    }

    private void getSupportCity()
    {
        mSubscription = Observable.create(new Observable.OnSubscribe<List<City>>()
        {
            @Override
            public void call(Subscriber<? super List<City>> subscriber)
            {
                if (subscriber.isUnsubscribed())
                    return;
                try
                {
                    String url = PM25Url.getAllCityBaseInfo();
                    String city = HttpUrl.getData(url);
                    Log.d(tag, "city = " + city);
                    subscriber.onNext(processCityData(city));
                    subscriber.onCompleted();
                }
                catch (IOException e)
                {
                    subscriber.onError(e);
                }
                catch (Exception e)
                {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<City>>()
                {
                    @Override
                    public void onCompleted()
                    {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.d(tag, "get city error");
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(List<City> cities)
                    {
                        mProgressBar.setVisibility(View.GONE);

                        Log.d(tag, "city list size = " + cities.size());
                        mList = cities;
                        mMediaIndex = new MediaIndexer(mList);
                        mCityAdapter = new CityAdapter(getApplicationContext(),
                            R.layout.view_list_item, mList);
                        mCityAdapter.setIndexer(mMediaIndex);
                        mLvCity.setAdapter(mCityAdapter);
                    }
                });
    }

    private List<City> processCityData(String str)
    {
        List<City> list = new ArrayList<>();
        String cityData = str.substring(str.indexOf("[") + 1, str.length() - 2);
        String[] cityArray = cityData.split(",");
        String name = null;
        String key = null;
        String sortKey = null;
        if (cityArray != null)
        {
            for (int i = 0, len = cityArray.length; i < len; i++)
            {
                name = cityArray[i].substring(1, cityArray[i].length() - 1);
                try
                {
                    key = PM25Url.ChineseToSpell(name);
                }
                catch (BadHanyuPinyinOutputFormatCombination e)
                {
                    e.printStackTrace();
                    key = null;
                }

                if (key != null)
                {
                    sortKey = PM25Url.getSortKey(key);
                    City cityInfo = new City(name, sortKey);
                    list.add(cityInfo);
                }
            }

            if (list != null && list.size() > 0)
            {
                Comparator<City> com = new Comparator<City>()
                {
                    RuleBasedCollator collator = (RuleBasedCollator) Collator
                            .getInstance(Locale.US);

                    @Override
                    public int compare(City lhs, City rhs)
                    {
                        return collator.compare(lhs.getSortKey(), rhs.getSortKey());
                    }
                };
                Collections.sort(list, com); // according to name
            }
        }
        return list;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_search :

                break;
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed())
        {
            mSubscription.unsubscribe();
        }
    }
}
