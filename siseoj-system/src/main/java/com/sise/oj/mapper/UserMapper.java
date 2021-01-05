package com.sise.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.oj.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CiJee
 * @version 1.0
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<User> selectByUsernameAndPassword(String username, String password);

    List<User> selectByUsername(String username);

}
