package com.sise.oj.mapper;

import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.Problem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * ProblemMapper类
 *
 * @author CiJee
 * @version 1.0
 */
@Repository
public interface ProblemMapper extends BaseMapper<Problem> {

    /**
     * 插入pid和tagId到题目标签关系表
     *
     * @param pid 题目主键
     * @param tags 标签主键集合
     */
    void insertProblemTag(@Param("pid") Long pid, @Param("tags") List<Long> tags);

    /**
     * 根据pid删除题目标签关系
     *
     * @param pid 题目主键
     */
    void deleteProblemTagByPid(Long pid);

    /**
     * 根据pid和tid删除题目标签关系
     *
     * @param pid 题目主键
     * @param tid 标签主键
     */
    void deleteProblemTagByPidAndTid(@Param("pid") Long pid, @Param("tid") Long tid);

    /**
     * 根据题名查询题目
     *
     * @param title 题名
     * @return Problem
     */
    Problem selectByTitle(String title);

    /**
     * 查找该题目号的所有标签主键
     * @param pid
     * @return
     */
    Set<Long> selectTagIdByPid(Long pid);
}
