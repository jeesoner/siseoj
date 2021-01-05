package com.sise.oj.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.sise.oj.base.SysConstants;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    /***
     * 值过滤器
     */
    private static final ValueFilter filter = (obj, s, v) -> {
        if (v == null)
            return "";
        return v;
    };

    private static final SerializerFeature[] feature = {
            //解决FastJson循环引用的问题
            SerializerFeature.DisableCircularReferenceDetect,
            //输出值为null的字段
            SerializerFeature.WriteMapNullValue
    };
    private static final SerializeConfig mapping = new SerializeConfig();

    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer(SysConstants.TIME_PATTERN));
        mapping.put(Timestamp.class, new SimpleDateFormatSerializer(SysConstants.TIME_PATTERN));//数据库的一个时间类型
    }

    /**
     * 将对象转换成JSON
     * 时间格式 yyyy-MM-dd HH:mm:ss
     *
     * @param bean BO/VO,map,list,数组,对象
     * @return JSON字符串
     */
    public static String objToJson(Object bean) {
        SerializeConfig customMapping = new SerializeConfig();
        customMapping.put(Date.class, new SimpleDateFormatSerializer(SysConstants.TIME_PATTERN));
        return getJsonByObj(bean, customMapping);
    }

    /**
     * 将JSON数据转换为ListBean集合
     *
     * @param <T> 类型参数
     * @param json  JSON数组数据
     * @param calzz 待转换的Bean类型 --LinkedCaseInsensitiveMap
     * @return bean
     */
    public static <T> List<T> jsonToObj(String json, Class<T> calzz) {
        return JSON.parseArray(json, calzz);
    }

    /***
     * 通用封装--获取json字符串
     * @param bean 对象
     * @param mappings 时间类型计划等
     * @return JSON
     */
    private static String getJsonByObj(Object bean, SerializeConfig mappings) {
        String json = JSON.toJSONString(bean, mappings, filter, feature);
        json = stringToJson(json);
        return json; //所有Key为小写
    }

    /**
     * 当文本中含有如下特殊字符时，此方法可以成功处理，让其在前台被正确解析，注意：此法不能处理单引号
     */
    public static String stringToJson(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                /*case '\"':
                    sb.append("\\\"");
					break;*/
                case '\\':   //如果不处理单引号，可以释放此段代码，若结合下面的方法处理单引号就必须注释掉该段代码
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':      //退格
                    sb.append("\\b");
                    break;
                case '\f':      //走纸换页
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n"); //换行
                    break;
                case '\r':      //回车
                    sb.append("\\r");
                    break;
                case '\t':      //横向跳格
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
}
