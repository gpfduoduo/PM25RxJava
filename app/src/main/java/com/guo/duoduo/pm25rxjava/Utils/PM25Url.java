package com.guo.duoduo.pm25rxjava.utils;


import java.util.Locale;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * Created by 郭攀峰 10129302 on 15-2-3. see link http://www.pm25.in/api_doc
 */
public class PM25Url
{
    /**
     * app key, 申请，官网中的公共app key是不管用的。
     */
    private static String token = "efL4s6XPybHqT2PpJCQv";

    /**
     * 仅仅获取该城市的pm2.5信息（不需要监测点信息)
     * 
     * @param city
     * @return
     */
    public static String getCityPM25BaseInfo(String city)
    {
        return "http://www.pm25.in/api/querys/pm2_5.json?city=" + city + "&token="
            + token + "&stations=no";
    }

    /**
     * 仅仅获取该城市的pm2.5信息（需要监测点信息)
     * 
     * @param city
     * @return
     */
    public static String getCityPM25Info(String city)
    {
        return "http://www.pm25.in/api/querys/pm2_5.json?city=" + city + "&token="
            + token;
    }

    /**
     * 获取支持的城市信息
     * 
     * @return
     */
    public static String getAllCityBaseInfo()
    {
        return "http://www.pm25.in/api/querys.json?token=" + token;
    }

    public static String getHealthInfluenceByQuality(String quality)
    {
        if ("优".equals(quality))
        {
            return "空气质量令人满意, 基本无空气污染";
        }
        else if ("良".equals(quality))
        {
            return "空气质量可接受, 但某些污染物可能对极少数异常敏感人群健康有较弱影响";
        }
        else if ("轻度污染".equals(quality))
        {
            return "易感人群症状有轻度加剧, 健康人群出现刺激症状";
        }
        else if ("中度污染".equals(quality))
        {
            return "进一步加剧易感人群症状, 可能对健康人群心脏、呼吸系统有影响";
        }
        else if ("重度污染".equals(quality))
        {
            return "心脏病和肺病患者症状显著加剧, 运动耐受力降低, 健康人群普遍出现症状";
        }
        else if ("严重污染".equals(quality))
        {
            return "健康人群运动耐受力降低, 有明显 强烈症状,提前出现某些疾病";
        }
        return "无影响";
    }

    public static String getAdviceByQuality(String quality)
    {
        if ("优".equals(quality))
        {
            return "各类人群可正常活动";
        }
        else if ("良".equals(quality))
        {
            return "极少数异常敏感人群应减少户外活动";
        }
        else if ("轻度污染".equals(quality))
        {
            return "儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼";
        }
        else if ("中度污染".equals(quality))
        {
            return "儿童、老年人及心脏病、呼吸系统疾病患者避免长时间、高强度的户外锻练, 一般人群适量减少户外运动";
        }
        else if ("重度污染".equals(quality))
        {
            return "儿童、老年人和心脏病、肺病患者应停留在室内, 停止户外运动, 一般人群减少户外运动";
        }
        else if ("严重污染".equals(quality))
        {
            return "儿童、老年人和病人应当留在室内,避免体力消耗, 一般人群应避免户外活动";
        }
        return "无建议";
    }

    private static HanyuPinyinOutputFormat spellFormat = new HanyuPinyinOutputFormat();

    static
    {
        spellFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        spellFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    public static String ChineseToSpell(String chineseStr)
            throws BadHanyuPinyinOutputFormatCombination
    {
        StringBuffer result = new StringBuffer();
        for (char c : chineseStr.toCharArray())
        {
            if (c > 128)
            {
                String[] array = PinyinHelper.toHanyuPinyinStringArray(c, spellFormat);
                if (array != null && array.length > 0)
                    result.append(array[0]);
                else
                    result.append(" ");
            }
            else
                result.append(c);
        }
        return result.toString();
    }

    public static String getSortKey(String sortKeyString)
    {
        String key = sortKeyString.substring(0, 1).toUpperCase(Locale.US);
        if (key.matches("[A-Z]"))
        {
            return key;
        }
        return "#";
    }

}
