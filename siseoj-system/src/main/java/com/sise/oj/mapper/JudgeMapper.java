package com.sise.oj.mapper;

import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.Judge;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}
