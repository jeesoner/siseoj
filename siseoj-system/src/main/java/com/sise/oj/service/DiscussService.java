package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.Discuss;
import com.sise.oj.domain.dto.DiscussDto;
import com.sise.oj.domain.param.QueryParam;

/**
 * @author Cijee
 * @version 1.0
 */
public interface DiscussService extends BaseService<Discuss> {

    Page<DiscussDto> query(Page<DiscussDto> page, QueryParam param);

    /**
     * 创建帖子
     *
     * @param resources 实体
     */
    void create(DiscussDto resources);

    /**
     * 根据ID获取帖子
     *
     * @return DiscussDto
     */
    DiscussDto findById(Long id);

    /**
     * 更新帖子
     *
     * @param resources 实体
     */
    void update(DiscussDto resources);

    /**
     * 删除帖子
     */
    void delete(Long id);
}
