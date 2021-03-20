package com.sise.oj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.Contest;
import com.sise.oj.domain.Problem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Repository
public interface ContestMapper extends BaseMapper<Contest> {

    List<Problem> getProblemList(@Param("page") Page<Problem> page,
                                 @Param("keyword") String keyword,
                                 @Param("cid") Long cid);
}
