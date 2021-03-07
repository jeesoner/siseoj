package com.sise.oj.service.impl;

import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.Judge;
import com.sise.oj.domain.JudgeCase;
import com.sise.oj.domain.dto.JudgeDto;
import com.sise.oj.enums.JudgeResult;
import com.sise.oj.mapper.JudgeCaseMapper;
import com.sise.oj.mapper.JudgeMapper;
import com.sise.oj.service.JudgeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * JudgeStatusServiceImpl
 *
 * @author Cijee
 * @version 1.0
 */
@Service
public class JudgeServiceImpl extends BaseServiceImpl<JudgeMapper, Judge>
        implements JudgeService {

    private final JudgeMapper judgeMapper;

    private final JudgeCaseMapper judgeCaseMapper;

    public JudgeServiceImpl(JudgeMapper judgeMapper, JudgeCaseMapper judgeCaseMapper) {
        this.judgeMapper = judgeMapper;
        this.judgeCaseMapper = judgeCaseMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Judge create(JudgeDto judgeDto) {
        Judge judge = new Judge();
        // 设置提交时间
        judge.setSubmitTime(new Date());
        judge.setPid(judgeDto.getPid());
        judge.setUsername(judgeDto.getUsername());
        judge.setCode(judgeDto.getCode());
        judge.setLanguage(judgeDto.getLanguage());
        // 设置代码长度
        judge.setCodeLength(judge.getCode().length());
        judge.setStatus(JudgeResult.PENDING.getCode());
        judgeDto.setCid(judgeDto.getCid());
        judgeMapper.insert(judge);
        return judge;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void error(Judge judge) {
        // 更新评测记录中的评测结果为ERROR （提交错误）
        judge.setStatus(JudgeResult.ERROR.getCode());
        judgeMapper.updateById(judge);
        // 更新评测结果信息
        JudgeCase judgeCase = new JudgeCase();
        judgeCase.setJudgeId(judge.getId());
        // 错误信息
        //judgeCase("评测机可能断开连接。失败于 " + DateUtils.getCurrentDate());
        judgeCaseMapper.insert(judgeCase);
    }
}
