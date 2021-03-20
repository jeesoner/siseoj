package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.Discuss;
import com.sise.oj.domain.dto.DiscussDto;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.mapper.DiscussMapper;
import com.sise.oj.service.DiscussService;
import com.sise.oj.util.SecurityUtils;
import com.sise.oj.util.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Service
public class DiscussServiceImpl extends BaseServiceImpl<DiscussMapper, Discuss> implements DiscussService {

    private final DiscussMapper discussMapper;

    public DiscussServiceImpl(DiscussMapper discussMapper) {
        this.discussMapper = discussMapper;
    }

    @Override
    public Page<DiscussDto> query(Page<DiscussDto> page, QueryParam param) {
        List<DiscussDto> result = discussMapper.list(page, param.getKeyword());
        page.setRecords(result);
        return page;
    }

    @Override
    public void create(DiscussDto resources) {
        Discuss discuss = new Discuss();
        discuss.setUid(SecurityUtils.getCurrentUserId());
        discuss.setTitle(resources.getTitle());
        discuss.setDescription(resources.getDescription());
        discuss.setContent(resources.getContent());
        discuss.setEnable(true);
        discuss.setPriority(0);
        discussMapper.insert(discuss);
    }

    @Override
    public DiscussDto findById(Long id) {
        DiscussDto discussDto = discussMapper.findById(id);
        ValidationUtils.isNull(discussDto, "帖子", "ID", id);
        return discussDto;
    }

    @Override
    public void update(DiscussDto resources) {
        Discuss discuss = discussMapper.selectById(resources.getId());
        ValidationUtils.isNull(discuss, "帖子", "ID", resources.getId());
        discuss.setTitle(resources.getTitle());
        discuss.setDescription(resources.getDescription());
        discuss.setContent(resources.getContent());
        discussMapper.insert(discuss);
    }

    @Override
    public void delete(Long id) {
        discussMapper.deleteById(id);
    }
}
