package com.sise.oj.mapper;

import com.sise.oj.domain.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * 用户信息Mapper
 *
 * @author CiJee
 * @version 1.0
 */
@Repository
public interface UserInfoMapper {

    UserInfo selectById(Long id);

}
