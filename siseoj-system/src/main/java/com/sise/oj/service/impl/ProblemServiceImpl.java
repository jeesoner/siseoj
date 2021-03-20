package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.*;
import com.sise.oj.domain.dto.ProblemDto;
import com.sise.oj.domain.param.ProblemQueryParam;
import com.sise.oj.domain.vo.ProblemInfoVo;
import com.sise.oj.domain.vo.ProblemVo;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.exception.BusinessException;
import com.sise.oj.judge.client.FileSystemHttpClient;
import com.sise.oj.mapper.ProblemMapper;
import com.sise.oj.service.*;
import com.sise.oj.util.SecurityUtils;
import com.sise.oj.util.StringUtils;
import com.sise.oj.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private final ProblemCaseService problemCaseService;
    private final ProblemCountService problemCountService;
    private final ProblemTagService problemTagService;
    private final SourceService sourceService;
    private final TagService tagService;
    private final FileSystemHttpClient fileSystemHttpClient;

    @Override
    @Transactional
    public Page<Problem> adminList(ProblemQueryParam param, Page<Problem> page) {
        LambdaQueryWrapper<Problem> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(param.getKeyword())) {
            queryWrapper
                    .like(Problem::getTitle, param.getKeyword()).or()
                    .like(Problem::getAuth, param.getKeyword()).or()
                    .like(Problem::getPid, param.getKeyword());
        }
        return problemMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Page<ProblemVo> list(ProblemQueryParam param, Page<ProblemVo> page) {
        // 关键词查询不为空
        String keyword = param.getKeyword();
        if (!StringUtils.isBlank(keyword)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(keyword);
            if (isNum.matches()) { // 利用正则表达式判断keyword是否为纯数字
                param.setPid(Long.valueOf(keyword));
            }
        }
        // 分页查询
        List<ProblemVo> problemList = problemMapper.getProblemList(page, param.getKeyword(),
                param.getDifficulty(), param.getSourceId(), param.getTid(), param.getPid());

        // 查询所有的题目来源
        List<Source> sourceList = sourceService.list();
        Map<Long, Source> sourceMap = sourceList.stream().collect(Collectors.toMap(Source::getId, item -> item));

        // 添加题目的来源
        for (ProblemVo problemVo : problemList) {
            if (Objects.nonNull(problemVo.getSourceId())) {
                problemVo.setSource(sourceMap.get(problemVo.getSourceId()));
            }
        }
        return page.setRecords(problemList);
    }

    @Override
    public ProblemInfoVo getInfo(Long id) {
        // 查询题目详情，题目标签，题目来源，题目统计情况
        Problem problem = problemMapper.selectById(id);
        // 如果没有找到对应的题目，抛出异常
        ValidationUtils.isNull(problem, "题目", "题号", id);
        // 判断是否公开题目
        if (problem.getAuth() != 1) {
            throw new BadRequestException(ResultCode.FORBIDDEN, "该题号对应题目并非公开题目，不支持访问！");
        }

        // 获取题目来源信息
        Source source = sourceService.getById(problem.getSourceId());

        // 获取题目标签
        LambdaQueryWrapper<ProblemTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProblemTag::getPid, problem.getPid());
        // 获取题目对应的标签id
        List<Long> tagIds = new LinkedList<>();
        problemTagService.list(wrapper).forEach(problemTag -> tagIds.add(problemTag.getTid()));
        // 获取题目对应的标签名称
        List<Tag> tags = null;
        if (!CollectionUtils.isEmpty(tagIds)) {
            tags = tagService.listByIds(tagIds);
        }
        // 获取题目的提交记录
        ProblemCount problemCount = problemCountService
                .getOne(Wrappers.lambdaQuery(ProblemCount.class).eq(ProblemCount::getPid, problem.getPid()));

        // 将数据统一写入到一个Vo返回数据实体类中
        return new ProblemInfoVo(problem, source, tags, problemCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(ProblemDto resources) {
        // 插入到题目表中
        resources.getProblem().setAuthor(SecurityUtils.getCurrentUsername());
        boolean addProblem = problemMapper.insert(resources.getProblem()) == 1;
        Long pid = resources.getProblem().getPid();
        // 插入到题目标签表
        boolean addProblemTag = true;
        List<Tag> tags = resources.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            for (Tag tag : tags) {
                if (tag.getId() == null) {
                    tagService.save(tag);
                }
            }
            List<ProblemTag> problemTags = new ArrayList<>();
            tags.forEach(tag -> problemTags.add(new ProblemTag(pid, tag.getId())));
            addProblemTag = problemTagService.saveBatch(problemTags);
        }
        resources.getProblemCases().forEach(problemCase -> {
            problemCase.setPid(pid);
            problemCase.setStatus(true);
        });
        boolean addProblemCase = problemCaseService.saveBatch(resources.getProblemCases());
        if (addProblem && addProblemTag && addProblemCase) {
            // 成功新增题目后，上传评测数据到服务器
            fileSystemHttpClient.submitJudgeCase(pid, resources.getProblemCases());
            // 如果存在特判代码，则上传到服务器
            if (StringUtils.isNotEmpty(resources.getProblem().getSpecialCode())) {
                fileSystemHttpClient.submitSpj(pid, resources.getProblem().getSpecialCode());
            }
        } else {
            throw new BusinessException("插入题目失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProblemDto resources) {
        // 题号
        Long pid = resources.getProblem().getPid();
        Problem problem = problemMapper.selectById(pid);
        // 如果没有找到对应的题目，抛出异常
        ValidationUtils.isNull(problem, "题目", "题号", pid);

        Problem problem1 = problemMapper.selectByTitle(resources.getProblem().getTitle());
        // 更新题目信息
        problemMapper.updateById(resources.getProblem());

        /*
         * 处理题目标签的增加与删除
         */
        // 前端提交的题目标签集合
        List<Tag> tags = resources.getTags();
        boolean addTagResult = true, delTagResult = true;
        // 前端的标签id集合
        Set<Long> newTagIds = new HashSet<>();
        if (tags != null) {
            for (Tag tag : tags) {
                // 处理新增的标签
                if (tag.getId() == null) {
                    boolean result = tagService.save(tag);
                    if (result) { // 保存成功，添加
                        newTagIds.add(tag.getId());
                    }
                } else {
                    newTagIds.add(tag.getId());
                }
            }
        }
        // 原有的标签id集合
        Set<Long> oldTagIds = problemTagService.list(Wrappers.lambdaQuery(ProblemTag.class).eq(ProblemTag::getPid, pid))
                .stream().map(ProblemTag::getTid).collect(Collectors.toSet());
        // 需要删除的id集合
        Set<Long> resultIds = new HashSet<>(oldTagIds);
        resultIds.removeAll(newTagIds);
        if (resultIds.size() > 0) {
            delTagResult = problemTagService.remove(Wrappers.lambdaQuery(ProblemTag.class)
                    .eq(ProblemTag::getPid, pid)
                    .in(ProblemTag::getTid, resultIds));
        }
        // 需要新增的id集合
        List<ProblemTag> addProblemTag = new LinkedList<>();
        resultIds.clear();
        resultIds.addAll(newTagIds);
        resultIds.removeAll(oldTagIds);
        if (resultIds.size() > 0) {
            for (Long resultId : resultIds) {
                addProblemTag.add(new ProblemTag(pid, resultId));
            }
            addTagResult = problemTagService.saveBatch(addProblemTag);
        }
        if (!addTagResult || !delTagResult) {
            throw new BusinessException("增加题目标签错误");
        }

        /*
         * 处理题目样例表的增加与删除
         */
        boolean problemCaseUpdate = false; // 题目样例表是否更新
        List<ProblemCase> problemCases = resources.getProblemCases();
        // 前端的样例id集合
        Set<Long> newCaseIds = new HashSet<>();
        // 原有的样例集合
        Map<Long, ProblemCase> oldProblemCaseMap = problemCaseService.list(Wrappers.lambdaQuery(ProblemCase.class).eq(ProblemCase::getPid, pid))
                .stream().collect(Collectors.toMap(ProblemCase::getId, item -> item));

        List<ProblemCase> caseAddList = new LinkedList<>(); // 待增加的样例表集合
        Set<Long> caseDelSet = new HashSet<>(oldProblemCaseMap.keySet()); // 待删除的样例id集合
        List<ProblemCase> caseUpdateList = new LinkedList<>(); // 待更新的样例表集合

        for (ProblemCase problemCase : problemCases) {
            // 处理新增的样例id
            if (problemCase.getId() == null) {
                problemCase.setPid(pid);
                problemCase.setStatus(true);
                caseAddList.add(problemCase); // 增加到待增加队列
            } else {
                newCaseIds.add(problemCase.getId());
                ProblemCase oldProblemCase = oldProblemCaseMap.get(problemCase.getId());
                // 如果前端的id和原有的id相同，判断是否要加入待修改集合
                if (oldProblemCase != null) {
                    if (!StringUtils.equals(problemCase.getInputCase(), oldProblemCase.getInputCase()) ||
                            !StringUtils.equals(problemCase.getOutputCase(), oldProblemCase.getOutputCase())) {
                        caseUpdateList.add(problemCase);
                    }
                }
            }
        }
        // 计算待删除的样例id集合
        caseDelSet.removeAll(newCaseIds);
        boolean delCaseResult = true, updateCaseResult = true, addCaseResult = true;

        // 执行批量删除
        if (caseDelSet.size() > 0) {
            problemCaseUpdate = true;
            delCaseResult = problemCaseService.removeByIds(caseDelSet);
        }
        // 执行批量增加
        if (caseAddList.size() > 0) {
            problemCaseUpdate = true;
            addCaseResult = problemCaseService.saveBatch(caseAddList);
        }
        // 执行批量更新
        if (caseUpdateList.size() > 0) {
            problemCaseUpdate = true;
            updateCaseResult = problemCaseService.updateBatchById(caseUpdateList);
        }
        // 操作数据库成功
        if (addCaseResult && updateCaseResult && delCaseResult) {
            // 上传数据
            if (problemCaseUpdate) {
                caseUpdateList.addAll(caseAddList);
                fileSystemHttpClient.submitJudgeCase(pid, caseUpdateList);
                if (!StringUtils.equals(problem.getSpecialCode(), resources.getProblem().getSpecialCode())) {
                    fileSystemHttpClient.submitSpj(pid, resources.getProblem().getSpecialCode());
                }
            }
        } else {
            throw new BusinessException("更新题目失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAuth(Problem resources) {
        // 题号
        Problem problem = problemMapper.selectById(resources.getPid());
        // 如果没有找到对应的题目，抛出异常
        ValidationUtils.isNull(problem, "题目", "题号", resources.getPid());
        problem.setAuth(resources.getAuth());
        problemMapper.updateById(problem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        problemMapper.deleteBatchIds(ids);
    }
}
