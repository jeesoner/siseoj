package com.sise.oj.service;

import com.sise.oj.base.BaseService;
import com.sise.oj.domain.ContestProblem;
import com.sise.oj.domain.vo.ContestProblemVo;

import java.util.Date;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
public interface ContestProblemService extends BaseService<ContestProblem> {

    /**
     * 根据比赛ID查询比赛题目
     *
     * @param id 比赛主键
     * @return List
     */
    List<ContestProblem> findByContestId(Long id);

    /**
     * 前端OJ：根据比赛和起始时间查询题目列表和提交情况
     *
     * @return
     */
    List<ContestProblemVo> getContestProblemList(Long cid, Date startTime);

    /**
     * 根据比赛ID和题目ID查询比赛题目
     *
     * @param cid 比赛ID
     * @param pid 题目ID
     * @return ContestProblem
     */
    ContestProblem findByContestIdAndPid(Long cid, Long pid);

    ContestProblem findByContestIdAndDisplayId(Long cid, Integer displayId);

    /**
     * 增加比赛题目
     *
     * @param resources ContestProblem
     */
    void insert(ContestProblem resources);

    /**
     * 通过比赛ID和题目ID删除题目
     *
     * @param cid 比赛ID
     * @param pid 题目ID
     */
    void deleteByContestIdAndPid(Long cid, Long pid);
}
