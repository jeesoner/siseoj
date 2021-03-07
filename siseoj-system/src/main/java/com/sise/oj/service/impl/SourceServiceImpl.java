package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.Source;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.exception.DataExistException;
import com.sise.oj.mapper.SourceMapper;
import com.sise.oj.service.SourceService;
import com.sise.oj.util.StringUtils;
import com.sise.oj.util.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

/**
 * SourceServiceImpl
 *
 * @author Cijee
 * @version 1.0
 */
@Service
public class SourceServiceImpl extends BaseServiceImpl<SourceMapper, Source> implements SourceService {
    
    private final SourceMapper sourceMapper;

    public SourceServiceImpl(SourceMapper sourceMapper) {
        this.sourceMapper = sourceMapper;
    }

    @Override
    public Page<Source> query(QueryParam param, Page<Source> page) {
        LambdaQueryWrapper<Source> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(param.getId())) {
            wrapper.eq(Source::getId, param.getId());
        }
        if (StringUtils.isNoneBlank(param.getKeyword())) {
            wrapper.like(Source::getName, param.getKeyword());
        }
        return sourceMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Source resources) {
        if (sourceMapper.selectOne(Wrappers.lambdaQuery(Source.class).eq(Source::getName, resources.getName())) != null) {
            throw new DataExistException(Source.class, "name", resources.getName());
        }
        sourceMapper.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Source resources) {
        Source source = sourceMapper.selectById(resources.getId());
        ValidationUtils.isNull(source, "题目来源", "id", resources.getId());
        // 更新题目来源
        sourceMapper.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        sourceMapper.deleteBatchIds(ids);
    }
}
