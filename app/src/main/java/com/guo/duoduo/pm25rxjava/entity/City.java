package com.guo.duoduo.pm25rxjava.entity;


import lombok.Data;


/**
 * Created by 郭攀峰 on 2015/12/20.
 */
@Data
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

}
