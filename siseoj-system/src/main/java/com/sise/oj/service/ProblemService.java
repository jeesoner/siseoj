package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.Problem;
import com.sise.oj.domain.dto.ProblemDto;
import com.sise.oj.domain.param.ProblemQueryParam;
import com.sise.oj.domain.vo.ProblemInfoVo;
import com.sise.oj.domain.vo.ProblemVo;

import java.util.Set;

/**
 * ProblemService
 *
 * @author Cijee
 * @version 1.0
 */
public interface ProblemService extends BaseService<Problem> {

    /**
     * 管理员查询所有题目
     *
     * @param param 查询条件
     * @param page 分页条件
     * @return Page
     */
    Page<Problem> adminList(ProblemQueryParam param, Page<Problem> page);

    /**
     * 用户查询所有题目
     *
     * @param param 查询条件
     * @param page 分页条件
     * @return Page
     */
    Page<ProblemVo> list(ProblemQueryParam param, Page<ProblemVo> page);

    /**
     * 用户查询题目详细信息
     *
     * @param id 题目主键
     * @return ProblemInfoVo
     */
    ProblemInfoVo getInfo(Long id);

    /**
     * 创建题目
     *
     * @param resources 题目
     */
    void insert(ProblemDto resources);

    /**
     * 更新题目
     *
     * @param resources 题目
     */
    void update(ProblemDto resources);

    /**
     * 删除题目
     * @param ids 题目主键集合不能为空
     */
    void delete(Set<Long> ids);

    /**
     * 更新题目权限
     * @param resources
     */
    void updateAuth(Problem resources);
}
