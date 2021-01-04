package com.sise.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.oj.domain.UserAuth;
import org.springframework.stereotype.Repository;

/**
 * @author Cijee
 * @version 1.0
 */
@Repository
public interface UserAuthMapper extends BaseMapper<UserAuth> {

    UserAuth selectByName(String username);

}
