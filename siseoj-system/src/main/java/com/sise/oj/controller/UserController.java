package com.sise.oj.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sise.oj.base.ResultJson;
import com.sise.oj.config.RsaProperties;
import com.sise.oj.domain.User;
import com.sise.oj.domain.UserRecord;
import com.sise.oj.domain.vo.UserEmailVo;
import com.sise.oj.domain.vo.UserPasswordVo;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.service.UserAcceptService;
import com.sise.oj.service.UserRecordService;
import com.sise.oj.service.UserService;
import com.sise.oj.util.RsaUtils;
import com.sise.oj.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "用户接口")
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final UserAcceptService userAcceptService;
    private final UserRecordService userRecordService;
    // 密钥配置类
    private final RsaProperties rsaProperties;
    // 密码编码器
    private final PasswordEncoder passwordEncoder;

    @ApiOperation("更新用户信息")
    @PutMapping
    public ResultJson<String> update(@Validated(User.Update.class) @RequestBody User user) {
        userService.update(user);
        return ResultJson.success("更新用户信息成功");
    }

    @ApiOperation("获取用户信息")
    @GetMapping
    public ResultJson<User> get(@RequestParam Long uid) {
        User user = userService.findById(uid);
        // 设置用户做题情况
        user.setUserAccepts(userAcceptService.findByUserId(user.getId()));
        // 设置用户记录
        user.setUserRecord(userRecordService.getOne(Wrappers.lambdaQuery(UserRecord.class)
                .eq(UserRecord::getUid, uid)));
        return ResultJson.success(user);
    }

    @ApiOperation("修改密码")
    @PostMapping("/update-password")
    public ResultJson<String> updatePassword(@RequestBody UserPasswordVo userPasswordVo) throws Exception {
        String oldPass = RsaUtils.decryptByPrivateKey(rsaProperties.getPrivateKey(), userPasswordVo.getOldPassword());
        String newPass = RsaUtils.decryptByPrivateKey(rsaProperties.getPrivateKey(), userPasswordVo.getNewPassword());
        User user = userService.findById(SecurityUtils.getCurrentUserId());
        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(newPass, user.getPassword())) {
            throw new BadRequestException("修改失败，新密码不能与旧密码相同");
        }
        userService.updatePassword(user.getUsername(), passwordEncoder.encode(newPass));
        return ResultJson.success(null);
    }

    @ApiOperation("修改邮箱")
    @PostMapping("/update-email")
    public ResultJson<String> updateEmail(@RequestBody UserEmailVo userEmailVo) throws Exception {
        String password = RsaUtils.decryptByPrivateKey(rsaProperties.getPrivateKey(), userEmailVo.getPassword());
        User user = userService.findById(SecurityUtils.getCurrentUserId());
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("修改失败，密码错误");
        }
        userService.updateEmail(user.getUsername(), userEmailVo.getNewEmail());
        return ResultJson.success(null);
    }

    @ApiOperation("修改头像")
    @PostMapping("/update-avatar")
    public ResultJson<Map<String, String>> updateAvatar(@RequestParam MultipartFile avatar) {
        return ResultJson.success(userService.updateAvatar(avatar));
    }
}
