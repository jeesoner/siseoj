package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.Source;
import com.sise.oj.domain.param.QueryParam;

import java.util.Set;

/**
 * SourceService
 *
 * @author Cijee
 * @version 1.0
 */
public interface SourceService extends BaseService<Source> {
    
    /**
     * 分页查询题目来源
     * @param param 查询参数
     * @param page 分页参数
     * @return 分页对象
     */
    Page<Source> query(QueryParam param, Page<Source> page);

    /**
     * 创建题目来源
     *
     * @param resources Source
     */
    void create(Source resources);

    /**
     * 更新题目来源
     *
     * @param resources Source
     */
    void update(Source resources);

    /**
     * 删除题目来源
     *
     * @param ids 题目来源主键集合不能为空
     */
    void delete(Set<Long> ids);
}
