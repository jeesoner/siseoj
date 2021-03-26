package com.sise.oj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.Judge;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JudgeStatusMapper类
 *
 * @author Cijee
 * @version 1.0
 */
@Repository
public interface JudgeMapper extends BaseMapper<Judge> {

    /**
     * 更新评测状态
     */
    void updateStatusById(@Param("status") Integer status, @Param("id") Long id);

    /**
     * 获取比赛评测记录
     *
     * @param page -
     * @param cid -
     * @param uid -
     * @param displayId -
     * @param username -
     * @param status -
     * @param beforeContestSubmit -
     */
    List<Judge> getContestJudgeList(@Param("page") Page<Judge> page,
                                    @Param("cid") Long cid,
                                    @Param("uid") Long uid,
                                    @Param("displayId") Long displayId,
                                    @Param("username") String username,
                                    @Param("status") Integer status,
                                    @Param("beforeContestSubmit") Boolean beforeContestSubmit);

    /**
     * 获取今天评测记录数
     *
     * @return Integer
     */
    Integer getTodayJudgeNum();
}
