package com.sise.oj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.UserRecord;
import com.sise.oj.domain.dto.RankDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Repository
public interface UserRecordMapper extends BaseMapper<UserRecord> {

    /**
     * 获取Rating排行
     */
    List<RankDto> getRatingRank(@Param("page") Page<RankDto> page,
                              @Param("username") String username);

    /**
     * 获取AC排行
     */
    List<RankDto> getAcRank(@Param("page") Page<RankDto> page,
                                @Param("username") String username);
}
