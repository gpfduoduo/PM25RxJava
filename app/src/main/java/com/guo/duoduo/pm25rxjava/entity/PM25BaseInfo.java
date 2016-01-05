package com.guo.duoduo.pm25rxjava.entity;


import android.text.TextUtils;

import java.io.Serializable;

import lombok.Data;


/**
 * Created by 10129302 on 15-2-3.
 */
@Data
public class PM25BaseInfo implements Serializable
{
    /**
     * 空气质量指标
     */
    private int aqi;
    /**
     * 地区名称
     */
    private String area;
    /**
     * pm2.5值
     */
    private int pm2_5;
    /**
     * pm2.5值，24小时
     */
    private int pm2_5_24h;
    /**
     * 污染情况
     */
    private String quality;
    /**
     * 主要污染物
     */
    private String primary_pollutant;
    /**
     * 时间
     */
    private String time_point;
    /**
     * 监测点
     */
    private String position_name;

    @Override
    public String toString()
    {
        return "监测点：" + (TextUtils.isEmpty(position_name) ? "未知地点" : position_name) + "\n"
            + "空气质量指数：" + aqi + "\n" + "PM2.5值：" + pm2_5 + "\n" + "过去24小时PM2.5平均值："
            + pm2_5_24h + "\n" + "空气质量：" + quality + "\n" + "主要污染物：" + primary_pollutant;
    }
}
