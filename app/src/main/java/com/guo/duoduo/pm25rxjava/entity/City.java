package com.guo.duoduo.pm25rxjava.entity;


/**
 * Created by 郭攀峰 on 2015/12/20.
 */
public class City
{
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 排序字母
     */
    private String sortKey;

    public City()
    {
    }

    public City(String name, String sortKey)
    {
        this.cityName = name;
        this.sortKey = sortKey;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getSortKey()
    {
        return sortKey;
    }

    public void setSortKey(String sortKey)
    {
        this.sortKey = sortKey;
    }
}
