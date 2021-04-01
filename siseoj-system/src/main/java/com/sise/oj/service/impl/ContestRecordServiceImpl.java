package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.ContestRecord;
import com.sise.oj.domain.vo.ContestRankDetail;
import com.sise.oj.domain.vo.ContestRankVo;
import com.sise.oj.enums.Constants;
import com.sise.oj.mapper.ContestRecordMapper;
import com.sise.oj.service.ContestRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cijee
 * @version 1.0
 */
@Service
public class ContestRecordServiceImpl extends BaseServiceImpl<ContestRecordMapper, ContestRecord> implements ContestRecordService {

    private final ContestRecordMapper contestRecordMapper;

    /**
     * 罚时状态
     * 只有解答错误了，才需要罚时，编译错误等其他情况不算
     */
    private static final List<Integer> penaltyStatus = Arrays.asList(
            Constants.Judge.PE.getStatus(),
            Constants.Judge.WA.getStatus(),
            Constants.Judge.TLE.getStatus(),
            Constants.Judge.MLE.getStatus(),
            Constants.Judge.RE.getStatus());

    public ContestRecordServiceImpl(ContestRecordMapper contestRecordMapper) {
        this.contestRecordMapper = contestRecordMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long uid, Integer score, Integer status, Long submitId, Long cid) {
        LambdaUpdateWrapper<ContestRecord> wrapper = Wrappers.lambdaUpdate(ContestRecord.class);
        // 如果AC
        if (Constants.Judge.AC.getStatus().equals(status)) {
            // 设置比赛记录为AC通过不罚时
            wrapper.set(ContestRecord::getStatus, Constants.Contest.RECORD_AC.getCode());
        } else if (penaltyStatus.contains(status)) {
            // 未AC，需要罚时
            wrapper.set(ContestRecord::getStatus, Constants.Contest.RECORD_NOT_AC_PENALTY.getCode());
        }
        // 设置分数
        wrapper.set(ContestRecord::getScore, score);
        // 设置评测记录表主键
        wrapper.eq(ContestRecord::getJudgeId, submitId)
               .eq(ContestRecord::getUid, uid)
               .eq(ContestRecord::getCid, cid);
        // 更新评测记录表
        contestRecordMapper.update(null, wrapper);
    }

    @Override
    public Page<ContestRankVo> getContestACMRank(Page<ContestRankVo> page, Long cid) {
        // 查询指定比赛的所有提交记录
        LambdaQueryWrapper<ContestRecord> wrapper = Wrappers.lambdaQuery(ContestRecord.class);
        // 构造条件
        wrapper.eq(ContestRecord::getCid, cid);
        wrapper.isNotNull(ContestRecord::getStatus);
        // 按照比赛开始后的提交时间排序
        wrapper.orderByAsc(ContestRecord::getTime);
        List<ContestRecord> contestRecordList = contestRecordMapper.selectList(wrapper);
        int size = (int) page.getSize();
        // 当前的下标
        int current = page.getCurrent() > 1 ? (int) ((page.getCurrent() - 1) * size) : 0;
        // 设置总数
        int count = contestRecordList.size();
        page.setTotal(count);
        // 分页
        List<ContestRecord> pageList = new ArrayList<>();
        for (int i = 0; i < size && i < count - current; i++) {
            pageList.add(contestRecordList.get(i + current));
        }
        page.setRecords(calculateACMRank(pageList));
        return page;
    }

    private List<ContestRankVo> calculateACMRank(List<ContestRecord> contestRecordList) {
        List<ContestRankVo> contestRankVoList = new ArrayList<>();
        // 用户名<--->数组下标 映射
        Map<Long, Integer> userIdMap = new HashMap<>();
        int index = 0;
        for (ContestRecord contestRecord : contestRecordList) {
            ContestRankVo contestRankVo;
            // 如果该用户id是第一次出现
            if (!userIdMap.containsKey(contestRecord.getUid())) {
                // 初始化ContestRankVo
                contestRankVo = new ContestRankVo();
                contestRankVo.setUsername(contestRecord.getUsername());
                // 设置罚时
                contestRankVo.setRunningTime(0);
                // 设置题数
                contestRankVo.setScore(0);
                // 设置题目详情
                Map<String, ContestRankDetail> details = new HashMap<>();
                contestRankVo.setDetails(details);
                userIdMap.put(contestRecord.getUid(), index++);
                contestRankVoList.add(contestRankVo);
            } else {
                // 拿到ContestRankVo
                contestRankVo = contestRankVoList.get(userIdMap.get(contestRecord.getUid()));
            }
            // 获取对应的题目信息，没有就取默认值{score, runningTime}
            ContestRankDetail problemDetails = contestRankVo.getDetails().getOrDefault(contestRecord.getDisplayId().toString(), new ContestRankDetail());
            // 因为是按照提交的时间排序的，如果某一用户的那道题AC的话，后面的提交则无效
            Integer score = problemDetails.getScore();
            // 该题目提交通过
            if (score.compareTo(0) > 0) {
                continue;
            }
            if (Constants.Contest.RECORD_AC.getCode().equals(contestRecord.getStatus())) {
                // 计算处罚时间（错误一次罚时20分钟）罚时(s)=错误次数*20*60+题目AC耗时
                int time = score * -1 * 20 * 60 + contestRecord.getTime().intValue();
                problemDetails.setRunningTime(time);
                problemDetails.setScore(score * -1);
                // 题目解决数+1
                contestRankVo.setScore(contestRankVo.getScore() + 1);
            }
            // 未AC通过记录错误次数
            else if (Constants.Contest.RECORD_NOT_AC_PENALTY.getCode().equals(contestRecord.getStatus())) {
                problemDetails.setScore(score - 1);
            }
            contestRankVo.getDetails().put(contestRecord.getDisplayId().toString(), problemDetails);
        }
        // 降序排AC数量，升序排罚时
        return contestRankVoList.stream().sorted(Comparator.comparing(ContestRankVo::getScore, Comparator.reverseOrder())
                .thenComparing(ContestRankVo::getRunningTime)).collect(Collectors.toList());
    }
}
