package com.sise.oj.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.User;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Api(tags = "系统：用户管理接口")
public class UserManagerController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PreAuthorize("@el.check('user:list')")
    @ApiOperation("分页查询用户")
    @GetMapping
    public ResultJson<Page<User>> list(QueryParam param, Page<User> page) {
        return ResultJson.success(userService.list(param, page));
    }

    @PreAuthorize("@el.check('user:list')")
    @ApiOperation("根据id获取用户")
    @GetMapping("/{id}")
    public ResultJson<User> get(@PathVariable Long id) {
        return ResultJson.success(userService.findById(id));
    }

    @PreAuthorize("@el.check('user:add')")
    @ApiOperation("新增用户")
    @PostMapping
    public ResultJson<String> create(@Validated @RequestBody User user) {
        // 设置默认密码
        user.setPassword(passwordEncoder.encode("123abc"));
        userService.create(user);
        return ResultJson.success("新增用户成功，默认密码为123abc");
    }

    @PreAuthorize("@el.check('user:edit')")
    @ApiOperation("修改用户")
    @PutMapping
    public ResultJson<String> update(@Validated(User.Update.class) @RequestBody User user) {
        // 更新用户
        userService.adminUpdate(user);
        return ResultJson.success("更新用户成功");
    }

    @PreAuthorize("@el.check('user:del')")
    @ApiOperation("删除用户")
    @DeleteMapping
    public ResultJson<String> delete(@RequestBody Set<Long> ids) {
        userService.delete(ids);
        return ResultJson.success("删除用户成功");
    }
}
