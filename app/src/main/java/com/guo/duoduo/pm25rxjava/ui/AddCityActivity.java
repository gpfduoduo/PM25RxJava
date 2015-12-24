package com.guo.duoduo.pm25rxjava.ui;


import java.io.IOException;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.guo.duoduo.pm25rxjava.R;
import com.guo.duoduo.pm25rxjava.adapter.CityAdapter;
import com.guo.duoduo.pm25rxjava.entity.City;
import com.guo.duoduo.pm25rxjava.utils.CityManager;
import com.guo.duoduo.pm25rxjava.utils.HttpUrl;
import com.guo.duoduo.pm25rxjava.utils.MediaIndexer;
import com.guo.duoduo.pm25rxjava.utils.PM25Url;


public class AddCityActivity extends BaseActivity
    implements
        TextWatcher,
        AdapterView.OnItemClickListener
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
        mEtCity.addTextChangedListener(this);
        mLvCity = (ListView) findViewById(R.id.lv_city);
        mLvCity.setOnItemClickListener(this);
    }

    private void getSupportCity()
    {
        mSubscription = Observable
                .create(new Observable.OnSubscribe<List<City>>()
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
                            subscriber.onNext(CityManager.getInstance(
                                getApplicationContext()).processCityData(city));
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
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        try
        {
            String key = PM25Url.ChineseToSpell(s.toString());
            if (mList != null)
            {
                for (int i = 0, len = mList.size(); i < len; i++)
                {
                    String cityName = mList.get(i).getCityName();
                    cityName = PM25Url.ChineseToSpell(cityName);
                    if (cityName.contains(key))
                    {
                        mLvCity.setSelection(i);
                        break;
                    }
                }
            }
        }
        catch (BadHanyuPinyinOutputFormatCombination e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        City city = (City) mCityAdapter.getItem(position);
        Log.d(tag, "city = " + city.getCityName());
        if (city != null)
        {
            if (!TextUtils.isEmpty(city.getCityName()))
            {
                CityManager.getInstance(getApplicationContext()).setCity(
                    city.getCityName());
                setResult(RESULT_OK);
                finish();
            }
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
