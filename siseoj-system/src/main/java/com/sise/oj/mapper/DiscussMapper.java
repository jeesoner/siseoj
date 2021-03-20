package com.sise.oj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.Discuss;
import com.sise.oj.domain.dto.DiscussDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Repository
public interface DiscussMapper extends BaseMapper<Discuss> {

    List<DiscussDto> list(@Param("page") Page<DiscussDto> page, @Param("title") String title);

    DiscussDto findById(Long id);
}
