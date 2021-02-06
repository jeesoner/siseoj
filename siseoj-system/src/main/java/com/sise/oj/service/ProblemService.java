package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.Problem;
import com.sise.oj.domain.param.ProblemQueryParam;

import java.util.List;
import java.util.Set;

/**
 * ProblemService
 *
 * @author Cijee
 * @version 1.0
 */
public interface ProblemService extends BaseService<Problem> {

    /**
     * 查询全部
     *
     * @return List
     */
    List<Problem> queryAll();

    /**
     * 根据条件查询全部
     *
     * @param param 查询条件
     * @param page 分页条件
     * @return Page
     */
    Page<Problem> queryAll(ProblemQueryParam param, Page<Problem> page);

    /**
     * 根据主键查询题目
     *
     * @param id 题目主键
     * @return ProblemVo
     */
    Problem findById(Long id);

    /**
     * 新增题目
     *
     * @param resources 题目
     */
    void create(Problem resources);

    /**
     * 更新题目
     *
     * @param resources 题目
     */
    void update(Problem resources);

    /**
     * 删除题目
     * @param ids 题目主键集合不能为空
     */
    void delete(Set<Long> ids);
}
