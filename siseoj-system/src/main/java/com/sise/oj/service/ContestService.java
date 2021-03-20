package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.Contest;
import com.sise.oj.domain.ContestProblem;
import com.sise.oj.domain.Problem;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.domain.vo.ProblemInfoVo;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
public interface ContestService extends BaseService<Contest> {

    /**
     * 分页查询比赛
     *
     * @param page 分页参数
     * @param param 查询参数
     * @return Page
     */
    Page<Contest> list(Page<Contest> page, QueryParam param);

    /**
     * 检查用户是否有查看该比赛的权限
     *
     * @param contest 当前比赛
     * @param uid 当前用户id
     * @param isRoot 是否超级管理员
     */
    void checkContestAuth(Contest contest, Long uid, boolean isRoot);

    /**
     * 检查用户是否有提交该比赛题目的权限
     *
     * @param contest 比赛
     * @param uid 当前用户id
     * @param password 密码
     */
    void checkJudgeAuth(Contest contest, Long uid, String password);

    /**
     * 根据ID查询比赛
     *
     * @param id 主键
     * @return Contest
     */
    Contest findById(Long id);

    /**
     * 新建比赛
     *
     * @param resources Contest
     */
    void insert(Contest resources);

    /**
     * 更新比赛
     *
     * @param resources Contest
     */
    void update(Contest resources);

    /**
     * 只能删除未开始的比赛
     *
     * @param id Contest
     */
    void delete(Long id);

    /**
     * 分页查询比赛的题目信息
     *
     * @param page 分页参数
     * @param param 查询参数
     * @return Page
     */
    Page<Problem> getProblemList(Page<Problem> page, QueryParam param);

    /**
     * 获取比赛题目的详情信息
     *
     * @param cid 比赛ID
     * @param displayId 题目显示的ID
     * @return ProblemInfoVo
     */
    ProblemInfoVo getContestProblemDetails(Contest contest, Integer displayId);

    /**
     * 更新比赛题目列表
     *
     * @param resources List
     * @param cid 比赛ID
     */
    void updateContestProblemList(List<ContestProblem> resources, Long cid);
}
