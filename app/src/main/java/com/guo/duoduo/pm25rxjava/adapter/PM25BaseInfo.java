package com.guo.duoduo.pm25rxjava.adapter;


import java.io.Serializable;

import android.text.TextUtils;


/**
 * Created by 10129302 on 15-2-3.
 */
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

    public int getAqi()
    {
        return aqi;
    }

    public String getArea()
    {
        return area;
    }

    public int getPm2_5()
    {
        return pm2_5;
    }

    public int getPm2_5_24h()
    {
        return pm2_5_24h;
    }

    public String getQuality()
    {
        return quality;
    }

    public String getTime_point()
    {
        return time_point;
    }

    public void setAqi(int aqi)
    {
        this.aqi = aqi;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public void setPm2_5(int pm2_5)
    {
        this.pm2_5 = pm2_5;
    }

    public void setPm2_5_24h(int pm2_5_24h)
    {
        this.pm2_5_24h = pm2_5_24h;
    }

    public void setQuality(String quality)
    {
        this.quality = quality;
    }

    public void setTime_point(String time_point)
    {
        this.time_point = time_point;
    }

    public String getPrimary_pollutant()
    {
        return primary_pollutant;
    }

    public void setPrimary_pollutant(String primary_pollutant)
    {
        this.primary_pollutant = primary_pollutant;
    }

    public String getPosition_name()
    {
        return position_name;
    }

    public void setPosition_name(String position_name)
    {
        this.position_name = position_name;
    }

    @Override
    public String toString()
    {
        return "监测点：" + (TextUtils.isEmpty(position_name) ? "未知地点" : position_name)
            + "\n" + "空气质量指数：" + aqi + "\n" + "PM2.5值：" + pm2_5 + "\n"
            + "过去24小时PM2.5平均值：" + pm2_5_24h + "\n" + "空气质量：" + quality + "\n" + "主要污染物："
            + primary_pollutant;
    }
}
