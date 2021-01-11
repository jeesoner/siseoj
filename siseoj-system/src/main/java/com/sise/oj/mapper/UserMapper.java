package com.sise.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.oj.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author CiJee
 * @version 1.0
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User selectByName(String username);
}
