package com.guo.duoduo.pm25rxjava.utils;


import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.content.Context;
import android.content.SharedPreferences;

import com.guo.duoduo.pm25rxjava.entity.City;


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
        mPreferences.edit().putString(PRE_CITY, city).commit();
    }

    public static List<City> processCityData(String str)
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
}
