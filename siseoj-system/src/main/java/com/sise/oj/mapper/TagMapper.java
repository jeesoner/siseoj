package com.sise.oj.mapper;

import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * TagMapper类
 *
 * @author Cijee
 * @version 1.0
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectByProblemId(Long id);

    int countByProblems(@Param("ids") Set<Long> ids);
}
