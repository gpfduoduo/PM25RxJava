package com.guo.duoduo.pm25rxjava.utils;


import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by 郭攀峰 on 2015/12/19.
 */
public class CityManager
{
    private static final String PRE_CITY = "city";
    private static final String DEFAULT_CITY = "";
    private static CityManager mInstance;
    private static SharedPreferences mPreferences;

    public CityManager(Context context)
    {
        mPreferences = context.getSharedPreferences(PRE_CITY, Context.MODE_PRIVATE);
    }

    public static CityManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new CityManager(context);
        }
        return mInstance;
    }

    public static String getCity()
    {
        return mPreferences.getString(PRE_CITY, DEFAULT_CITY);
    }

    public static void setCity(String city)
    {
        mPreferences.edit().putString(PRE_CITY, city);
    }
}
