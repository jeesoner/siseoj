package com.sise.oj.base;

/**
 * Mapper父类，注意这个类不要让 mybatis-plus 扫描到！
 *
 * @author Cijee
 * @version 1.0
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    // 自定义公共的方法
}
