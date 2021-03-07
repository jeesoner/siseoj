package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.Tag;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.exception.DataExistException;
import com.sise.oj.mapper.TagMapper;
import com.sise.oj.service.TagService;
import com.sise.oj.util.StringUtils;
import com.sise.oj.util.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

/**
 * TagServiceImpl
 *
 * @author Cijee
 * @version 1.0
 */
@Service
public class TagServiceImpl extends BaseServiceImpl<TagMapper, Tag> implements TagService {

    private final TagMapper tagMapper;

    public TagServiceImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public Page<Tag> query(QueryParam param, Page<Tag> page) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(param.getId())) {
            wrapper.eq(Tag::getId, param.getId());
        }
        if (StringUtils.isNoneBlank(param.getKeyword())) {
            wrapper.like(Tag::getName, param.getKeyword());
        }
        return tagMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Tag resources) {
        if (tagMapper.selectOne(Wrappers.lambdaQuery(Tag.class).eq(Tag::getName, resources.getName())) != null) {
            throw new DataExistException(Tag.class, "name", resources.getName());
        }
        tagMapper.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Tag resources) {
        Tag tag = tagMapper.selectById(resources.getId());
        ValidationUtils.isNull(tag, "标签", "id", resources.getId());
        // 更新标签
        tagMapper.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        tagMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verification(Set<Long> ids) {
        if (tagMapper.countByProblems(ids) > 0) {
            throw new BadRequestException("所选标签存在题目关联，请解除关联再试！");
        }
    }
}
