package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.User;
import com.sise.oj.domain.UserRecord;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.exception.DataExistException;
import com.sise.oj.mapper.UserMapper;
import com.sise.oj.service.OnlineUserService;
import com.sise.oj.service.RoleService;
import com.sise.oj.service.UserRecordService;
import com.sise.oj.service.UserService;
import com.sise.oj.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 用户服务实现类
 *
 * @author CiJee
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    private final UserRecordService userRecordService;

    private final RoleService roleService;

    private final OnlineUserService onlineUserService;

    @Override
    public User findByName(String username) {
        return userMapper.selectByName(username);
    }

    @Override
    public User findById(Long id) {
        User user = userMapper.selectById(id);
        ValidationUtils.isNull(user, "用户", "id", id);
        return user;
    }

    @Override
    public Page<User> list(QueryParam param, Page<User> page) {
        List<User> userList = userMapper.getUserList(page, param.getKeyword(), param.getEnabled());
        page.setRecords(userList);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(User resources) {
        // 创建用户前，先查看有没有重复的用户名
        if (userMapper.selectByName(resources.getUsername()) != null) {
            throw new DataExistException("该用户名已存在");
        }
        if (userMapper.selectByEmail(resources.getEmail()) != null) {
            throw new DataExistException("该邮箱已经被注册过了");
        }
        userMapper.insert(resources);
        // user_record 插入数据
        UserRecord userRecord = new UserRecord();
        userRecord.setRating(0);
        userRecord.setSubmissions(0);
        userRecord.setUid(resources.getId());
        userRecordService.save(userRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adminUpdate(User resources) {
        User user = userMapper.selectById(resources.getId());
        ValidationUtils.isNull(user, "用户", "id", resources.getId());
        User user1 = userMapper.selectByEmail(resources.getEmail());
        if (user1 != null && !user.getEmail().equals(user1.getEmail())) {
            throw new DataExistException("该邮箱已经被用过了");
        }
        user.setNickname(resources.getNickname());
        user.setGender(resources.getGender());
        user.setEmail(resources.getEmail());
        user.setPhone(resources.getPhone());
        user.setEnabled(resources.getEnabled());
        // 更新权限
        roleService.updateUserRole(user.getId(), resources.getRoles());
        // 如果用户被禁用，则清除用户登录信息
        if (!resources.getEnabled()) {
            onlineUserService.kickOutForUsername(resources.getUsername());
        }
        userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        User user = userMapper.selectById(resources.getId());
        ValidationUtils.isNull(user, "用户", "id", resources.getId());
        User user1 = userMapper.selectByEmail(resources.getEmail());
        if (user1 != null && !user.getEmail().equals(user1.getEmail())) {
            throw new DataExistException("该邮箱已经被用过了");
        }
        user.setNickname(resources.getNickname());
        user.setGender(resources.getGender());
        user.setEmail(resources.getEmail());
        user.setPhone(resources.getPhone());
        user.setMotto(resources.getMotto());
        user.setSchool(resources.getSchool());
        user.setCourse(resources.getCourse());
        user.setGithub(resources.getGithub());
        user.setBlog(resources.getBlog());
        userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        userMapper.deleteBatchIds(ids);
    }
}
