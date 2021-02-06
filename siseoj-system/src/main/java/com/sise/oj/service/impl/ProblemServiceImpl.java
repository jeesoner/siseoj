package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.*;
import com.sise.oj.domain.param.ProblemQueryParam;
import com.sise.oj.exception.DataExistException;
import com.sise.oj.mapper.*;
import com.sise.oj.service.ProblemService;
import com.sise.oj.util.StringUtils;
import com.sise.oj.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ProblemServiceImpl
 *
 * @author Cijee
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProblemServiceImpl extends BaseServiceImpl<ProblemMapper, Problem> implements ProblemService {

    private final ProblemMapper problemMapper;
    private final LevelMapper levelMapper;
    private final TagMapper tagMapper;
    private final SourceMapper sourceMapper;
    private final ProblemInfoMapper problemInfoMapper;

    @Override
    public List<Problem> queryAll() {
        List<Problem> problems = problemMapper.selectList(null);
        List<Source> sourceList = sourceMapper.selectList(null);
        List<Level> levelList = levelMapper.selectList(null);
        Map<Long, Level> levelMap = levelList.stream().collect(Collectors.toMap(Level::getId, item -> item));
        Map<Long, Source> sourceMap = sourceList.stream().collect(Collectors.toMap(Source::getId, item -> item));
        for (Problem item : problems) {
            // 设置问题难度等级
            if (Objects.nonNull(item.getLevelId())) {
                item.setLevel(levelMap.get(item.getLevelId()));
            }
            // 设置来源
            if (Objects.nonNull(item.getSourceId())) {
                item.setSource(sourceMap.get(item.getSourceId()));
            }
        }
        return problems;
    }

    @Override
    public Page<Problem> queryAll(ProblemQueryParam param, Page<Problem> page) {
        // 根据查询条件进行分页查询
        LambdaQueryWrapper<Problem> wrapper = new LambdaQueryWrapper<>();
        // 如果有题目名称（也有可能是题号）
        String title = param.getTitle();
        if (!StringUtils.isBlank(title)) {
            // 如果是整数
            if (StringUtils.isInteger(title)) {
                wrapper.eq(Problem::getPid, Long.parseLong(title));
            }
            // 如果是字符串
            else {
                wrapper.like(Problem::getTitle, title);
            }
        }
        // 如果有标签
        if (!Objects.nonNull(param.getTagId())) {

        }

        Page<Problem> problemPage = problemMapper.selectPage(page, wrapper);
        List<Problem> problems = problemPage.getRecords();

        List<Level> levelList = levelMapper.selectList(null);
        List<Source> sourceList = sourceMapper.selectList(null);

        Map<Long, Level> levelMap = levelList.stream().collect(Collectors.toMap(Level::getId, item -> item));
        Map<Long, Source> sourceMap = sourceList.stream().collect(Collectors.toMap(Source::getId, item -> item));

        for (Problem item : problems) {
            // 设置来源
            if (Objects.nonNull(item.getSourceId())) {
                item.setSource(sourceMap.get(item.getSourceId()));
            }
            // 设置问题难度等级
            if (Objects.nonNull(item.getLevelId())) {
                item.setLevel(levelMap.get(item.getLevelId()));
            }
        }
        return problemPage;
    }

    @Override
    public Problem findById(Long id) {
        Problem problem = problemMapper.selectById(id);
        // 如果没有找到对应的题目，抛出异常
        ValidationUtils.isNull(problem, "Problem", "pid", id);
        if (Objects.nonNull(problem.getLevelId())) {
            problem.setLevel(levelMapper.selectById(problem.getLevelId()));
        }
        if (Objects.nonNull(problem.getSourceId())) {
            problem.setSource(sourceMapper.selectById(problem.getSourceId()));
        }
        if (Objects.nonNull(problem.getProblemInfoId())) {
            problem.setProblemInfo(problemInfoMapper.selectById(problem.getProblemInfoId()));
        }
        List<Tag> tags = tagMapper.selectByProblemId(problem.getPid());
        if (!tags.isEmpty()) {
            problem.setTags(tags);
            problem.setTagIds(tags.stream().map(Tag::getId).collect(Collectors.toList()));
        }
        return problem;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Problem problem) {
        if (problemMapper.selectByTitle(problem.getTitle()) != null) {
            throw new DataExistException(Problem.class, "title", problem.getTitle());
        }
        // 题目详情
        ProblemInfo problemInfo = problem.getProblemInfo();
        // 设置默认值
        problem.setAcceptCount(0);
        problem.setAttemptCount(0);
        problem.setVisible(true);
        if (Objects.isNull(problem.getSourceId())) {
            problem.setSourceId(1L); // 默认为siseoj
        }
        // 插入到题目详情表
        problemInfoMapper.insert(problemInfo);
        // 设置题目详情表主键
        problem.setProblemInfoId(problemInfo.getId());
        // 插入到题目表中
        problemMapper.insert(problem);

        // 获取插入后的题目主键
        Long pid = problem.getPid();
        // 设置题目标签
        if (!CollectionUtils.isEmpty(problem.getTagIds())) {
            problemMapper.insertProblemTag(pid, problem.getTagIds());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Problem resources) {
        Problem problem = problemMapper.selectById(resources.getPid());
        // 如果没有找到对应的题目，抛出异常
        ValidationUtils.isNull(problem, "Problem", "pid", resources.getPid());
        // 更新题目标签关系表
        List<Long> tagIds = resources.getTagIds(); // client: 2 3 4  database:  3 4 5
        if (CollectionUtils.isEmpty(tagIds)) {
            problemMapper.deleteProblemTagByPid(resources.getPid());
        } else {
            Set<Long> newTags = new HashSet<>(tagIds);
            // 查询已有的tagsId
            Set<Long> oldTags = problemMapper.selectTagIdByPid(resources.getPid());
            // 判断是否互为子集
            if (newTags.size() != oldTags.size() || !newTags.containsAll(oldTags)) {
                // 更新数据库
                problemMapper.deleteProblemTagByPid(resources.getPid());
                problemMapper.insertProblemTag(resources.getPid(), tagIds);
            }
        }
        // 更新题目详情表
        ProblemInfo problemInfo = resources.getProblemInfo();
        problemInfoMapper.updateById(problemInfo);
        // 更新题目表
        problemMapper.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        problemMapper.deleteBatchIds(ids);
        Set<Long> set = new HashSet<>();
        for (Long pid : ids) {
            // 查询对应的题目
            Problem problem = problemMapper.selectById(pid);
            // 将题目详情表的主键添加到set中
            set.add(problem.getProblemInfoId());
            // 删除对应的题目标签表关系
            problemMapper.deleteProblemTagByPid(problem.getPid());
        }
        // 删除题目详情表
        if (!set.isEmpty()) {
            problemInfoMapper.deleteBatchIds(set);
        }
        // 删除题目表
        problemMapper.deleteBatchIds(ids);
    }
}
