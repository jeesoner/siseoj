package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.ContestProblem;
import com.sise.oj.domain.vo.ContestProblemVo;
import com.sise.oj.exception.DataExistException;
import com.sise.oj.mapper.ContestProblemMapper;
import com.sise.oj.service.ContestProblemService;
import com.sise.oj.service.ProblemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Service
public class ContestProblemServiceImpl extends BaseServiceImpl<ContestProblemMapper, ContestProblem> implements ContestProblemService {

    private final ContestProblemMapper contestProblemMapper;
    private final ProblemService problemService;

    public ContestProblemServiceImpl(ContestProblemMapper contestProblemMapper, ProblemService problemService) {
        this.contestProblemMapper = contestProblemMapper;
        this.problemService = problemService;
    }

    @Override
    public List<ContestProblem> findByContestId(Long id) {
        List<ContestProblem> contestProblems = contestProblemMapper.selectList(Wrappers.lambdaQuery(ContestProblem.class)
                .eq(ContestProblem::getCid, id)
                .orderByAsc(ContestProblem::getDisplayId));
        contestProblems.forEach(contestProblem -> contestProblem.setTitle(problemService.getById(contestProblem.getPid()).getTitle()));
        return contestProblems;
    }

    @Override
    public List<ContestProblemVo> getContestProblemList(Long cid, Date startTime) {
        return contestProblemMapper.getContestProblemList(cid, startTime);
    }

    @Override
    public ContestProblem findByContestIdAndPid(Long cid, Long pid) {
        return contestProblemMapper.selectOne(Wrappers.lambdaQuery(ContestProblem.class)
                .eq(ContestProblem::getCid, cid)
                .eq(ContestProblem::getPid, pid));
    }

    @Override
    public ContestProblem findByContestIdAndDisplayId(Long cid, Integer displayId) {
        return contestProblemMapper.selectOne(Wrappers.lambdaQuery(ContestProblem.class)
                .eq(ContestProblem::getCid, cid)
                .eq(ContestProblem::getDisplayId, displayId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(ContestProblem resources) {
        ContestProblem contestProblem = contestProblemMapper.selectOne(Wrappers.lambdaQuery(ContestProblem.class)
                .eq(ContestProblem::getCid, resources.getCid())
                .eq(ContestProblem::getPid, resources.getPid()));
        if (contestProblem != null) {
            throw new DataExistException("不允许出现重复的题目[" + contestProblem.getPid() +"]!");
        }
        contestProblemMapper.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByContestIdAndPid(Long cid, Long pid) {
        contestProblemMapper.delete(Wrappers.lambdaQuery(ContestProblem.class)
                .eq(ContestProblem::getCid, cid)
                .eq(ContestProblem::getPid, pid));
    }
}
