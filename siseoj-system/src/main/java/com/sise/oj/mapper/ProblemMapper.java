package com.sise.oj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.Problem;
import com.sise.oj.domain.Tag;
import com.sise.oj.domain.vo.ProblemVo;
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
     * 分页查询
     *
     * @param page 分页对象
     * @param keyword 搜索关键字
     * @param difficulty 难度
     * @param tid 标签外键
     * @return ProblemVo
     */
    List<ProblemVo> getProblemList(Page<ProblemVo> page,
                                   @Param("keyword")String keyword,
                                   @Param("difficulty")Integer difficulty,
                                   @Param("sourceId")Long sourceId,
                                   @Param("tid")Long tid,
                                   @Param("pid")Long pid);

    /**
     * 根据题目id查询标签
     *
     * @param pid 题目主键
     * @return 标签集合
     */
    List<Tag> getProblemTag(Long pid);

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
