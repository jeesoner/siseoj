package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sise.oj.annotation.TryAgain;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.ProblemCount;
import com.sise.oj.exception.TryAgainException;
import com.sise.oj.mapper.ProblemCountMapper;
import com.sise.oj.service.ProblemCountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * ProblemCountServiceImpl
 *
 * @author Cijee
 * @version 1.0
 */
@Service
public class ProblemCountServiceImpl extends BaseServiceImpl<ProblemCountMapper, ProblemCount> implements ProblemCountService {

    private final ProblemCountMapper problemCountMapper;

    public ProblemCountServiceImpl(ProblemCountMapper problemCountMapper) {
        this.problemCountMapper = problemCountMapper;
    }

    @TryAgain
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public void updateCount(Long pid, boolean accept) {
        // 查询最新版本的题目记录
        ProblemCount problemCount = problemCountMapper
                .selectOne(Wrappers.lambdaQuery(ProblemCount.class).eq(ProblemCount::getPid, pid));
        problemCount.setTotal(problemCount.getTotal() + 1);
        // 如果题目通过
        if (accept) {
            problemCount.setAccept(problemCount.getAccept() + 1);
        }
        boolean result = problemCountMapper.updateById(problemCount) != 1;
        if (result) {
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 保存失败，抛出重试异常
            throw new TryAgainException("插入数据库失败，系统开始重试....");
        }
    }

    @Override
    public int acceptCount() {
        return problemCountMapper.acceptCount();
    }

    @Override
    public int totalCount() {
        return problemCountMapper.totalCount();
    }
}
