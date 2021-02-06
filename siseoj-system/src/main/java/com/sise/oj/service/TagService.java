package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.Tag;
import com.sise.oj.domain.param.TagQueryParam;

import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
public interface TagService extends BaseService<Tag> {

    /**
     * 分页查询标签
     * @param param 查询参数
     * @param page 分页参数
     * @return 分页对象
     */
    Page<Tag> query(TagQueryParam param, Page<Tag> page);

    /**
     * 创建标签
     *
     * @param resources Tag
     */
    void create(Tag resources);

    /**
     * 更新标签
     *
     * @param resources Tag
     */
    void update(Tag resources);

    /**
     * 删除标签
     *
     * @param ids 标签主键集合不能为空
     */
    void delete(Set<Long> ids);
}
