package com.sise.oj.mapper;

import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.ContestProblem;
import com.sise.oj.domain.vo.ContestProblemVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Repository
public interface ContestProblemMapper extends BaseMapper<ContestProblem> {

    /**
     * 根据比赛和起始时间查询题目列表和提交情况
     *
     * @param cid 比赛ID
     * @param startTime 起始时间，用于查询提交情况
     * @return List
     */
    List<ContestProblemVo> getContestProblemList(@Param("cid") Long cid, @Param("startTime") Date startTime);
}
