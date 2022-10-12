package com.dm.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.blog.domain.entity.UserRole;
import com.dm.blog.mapper.UserRoleMapper;
import com.dm.blog.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author dm
 * @since 2022-10-11 12:44:48
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

