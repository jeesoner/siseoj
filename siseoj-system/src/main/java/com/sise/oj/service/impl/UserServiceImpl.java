package com.sise.oj.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.config.FileProperties;
import com.sise.oj.domain.User;
import com.sise.oj.domain.UserRecord;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.exception.DataExistException;
import com.sise.oj.mapper.UserMapper;
import com.sise.oj.service.OnlineUserService;
import com.sise.oj.service.RoleService;
import com.sise.oj.service.UserRecordService;
import com.sise.oj.service.UserService;
import com.sise.oj.util.FileUtils;
import com.sise.oj.util.SecurityUtils;
import com.sise.oj.util.StringUtils;
import com.sise.oj.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.*;

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

    private final FileProperties fileProperties;

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
        if (!CollectionUtils.isEmpty(resources.getRoles())) {
            user.setIsAdmin(true);
        }
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String username, String encodePassword) {
        User user = userMapper.selectByName(username);
        ValidationUtils.isNull(user, "用户", "用户名", username);
        // 设置新密码
        user.setPassword(encodePassword);
        // 更新
        userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String newEmail) {
        User user = userMapper.selectByName(username);
        ValidationUtils.isNull(user, "用户", "用户名", username);
        // 设置新邮箱
        user.setEmail(newEmail);
        // 更新
        userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> updateAvatar(MultipartFile avatar) {
        User user = userMapper.selectByName(SecurityUtils.getCurrentUsername());
        // 获取头像的真实路径
        String oldPath = user.getAvatarPath();
        // 将二进制数据保存到头像文件中
        File file = FileUtils.upload(avatar, fileProperties.getPath().getAvatar());
        user.setAvatar(Objects.requireNonNull(file).getName());
        user.setAvatarPath(Objects.requireNonNull(file).getPath());
        userMapper.updateById(user);
        // 删除旧的头像
        if (StringUtils.isNotBlank(oldPath)) {
            FileUtil.del(oldPath);
        }
        @NotBlank String username = user.getUsername();
        // 返回头像地址
        return new HashMap<String, String>(1) {{
            put("avatar", file.getName());
        }};
    }
}
