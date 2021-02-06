package com.sise.oj.service.impl;

import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.JudgeResultInfo;
import com.sise.oj.domain.JudgeStatus;
import com.sise.oj.domain.dto.JudgeDto;
import com.sise.oj.enums.JudgeResult;
import com.sise.oj.mapper.JudgeResultInfoMapper;
import com.sise.oj.mapper.JudgeStatusMapper;
import com.sise.oj.service.JudgeStatusService;
import com.sise.oj.util.DateUtils;
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
public class JudgeStatusServiceImpl extends BaseServiceImpl<JudgeStatusMapper, JudgeStatus>
        implements JudgeStatusService {

    private final JudgeStatusMapper judgeStatusMapper;

    private final JudgeResultInfoMapper judgeResultInfoMapper;

    public JudgeStatusServiceImpl(JudgeStatusMapper judgeStatusMapper, JudgeResultInfoMapper judgeResultInfoMapper) {
        this.judgeStatusMapper = judgeStatusMapper;
        this.judgeResultInfoMapper = judgeResultInfoMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JudgeStatus create(JudgeDto judgeDto) {
        JudgeStatus judgeStatus = new JudgeStatus();
        // 设置提交时间
        judgeStatus.setSubmitTime(new Date());
        judgeStatus.setPid(judgeDto.getPid());
        judgeStatus.setUsername(judgeDto.getUsername());
        judgeStatus.setCode(judgeDto.getCode());
        judgeStatus.setLanguage(judgeDto.getLanguage());
        // 设置代码长度
        judgeStatus.setCodeLength(judgeStatus.getCode().length());
        judgeStatus.setResult(JudgeResult.PENDING.getCode());
        judgeDto.setCid(judgeDto.getCid());
        judgeStatusMapper.insert(judgeStatus);
        return judgeStatus;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void error(JudgeStatus judgeStatus) {
        // 更新评测记录中的评测结果为ERROR （提交错误）
        judgeStatus.setResult(JudgeResult.ERROR.getCode());
        judgeStatusMapper.updateById(judgeStatus);
        // 更新评测结果信息
        JudgeResultInfo judgeResultInfo = new JudgeResultInfo();
        judgeResultInfo.setJudgeId(judgeStatus.getId());
        // 错误信息
        judgeResultInfo.setResultInfo("评测机可能断开连接。失败于 " + DateUtils.getCurrentDate());
        judgeResultInfo.setTime(new Date());
        judgeResultInfoMapper.insert(judgeResultInfo);
    }
}
