package com.sise.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.oj.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    Set<Role> findByUserId(Long id);

}
