package com.sise.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.oj.domain.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CiJee
 * @version 1.0
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    List<UserInfo> selectByUsernameAndPassword(String username, String password);

}
