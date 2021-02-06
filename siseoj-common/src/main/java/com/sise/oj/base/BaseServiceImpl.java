package com.sise.oj.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * BaseService 实现类（ 泛型：M是Mapper(dao)对象，T是实体 ）
 *
 * @author Cijee
 * @version 1.0
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
}
