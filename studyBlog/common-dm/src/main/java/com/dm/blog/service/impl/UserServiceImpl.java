package com.dm.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.UserDto;
import com.dm.blog.domain.entity.Role;
import com.dm.blog.domain.entity.User;
import com.dm.blog.domain.entity.UserRole;
import com.dm.blog.domain.vo.*;
import com.dm.blog.enums.AppHttpCodeEnum;
import com.dm.blog.exception.SystemException;
import com.dm.blog.mapper.UserMapper;
import com.dm.blog.service.RoleService;
import com.dm.blog.service.UserRoleService;
import com.dm.blog.service.UserService;
import com.dm.blog.utils.BeanCopyUtils;
import com.dm.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-05 16:54:44
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {

        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户全部信息
        User user = getById(userId);
        //封装为userInfoVo对象
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //返回响应体
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        //根据id更新
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {

        //对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        } else if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        } else if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        } else if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }

        //对数据进行查重
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        } else if (nickNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        } else if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //对密码进行加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, UserDto userDto) {
        //封装查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(userDto.getUserName()), User::getUserName, userDto.getUserName());
        wrapper.eq(StringUtils.hasText(userDto.getPhonenumber()), User::getPhonenumber, userDto.getPhonenumber());
        wrapper.eq(StringUtils.hasText(userDto.getStatus()), User::getPhonenumber, userDto.getStatus());

        //进行分页
        Page<User> userPage = new Page<>(pageNum, pageSize);
        page(userPage, wrapper);

        //封装返回
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(userPage.getRecords(), UserVo.class);
        PageVo<UserVo> pageVo = new PageVo<>(userVos, userPage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addUser(UserDto userDto) {

        //进行条件过滤
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (phoneExist(user.getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //对密码进行加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //存入user表
        save(user);

        //存入user-role表
        Long[] roleIds = userDto.getRoleIds();
        if (!ObjectUtils.isEmpty(roleIds)) {
            List<UserRole> userRoles = Arrays.stream(roleIds)
                    .map(roleId -> new UserRole(user.getId(), roleId))
                    .collect(Collectors.toList());
            userRoleService.saveBatch(userRoles);
        }

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUsers(List<Long> userIds) {
        if (userIds.contains(SecurityUtils.getUserId())) {
            return ResponseResult.errorResult(500, "不能删除当前你正在使用的用户");
        } else {
            removeByIds(userIds);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserInfoAndRoleIds(Long userId) {

        //查询用户所属roleIds
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        List<UserRole> list = userRoleService.list(wrapper);
        List<Long> roleIds = list.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());

        //查询所有roles
        List<Role> roles = roleService.list(new LambdaQueryWrapper<Role>().eq(Role::getStatus, SystemConstants.STATUS_NORMAL));
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roles, RoleVo.class);

        //查询用户信息
        User user = getById(userId);
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);

        //封装对象
        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(roleIds, roleVos, userVo);

        return ResponseResult.okResult(vo);
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UserDto userDto) {
        //user表改
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        updateById(user);

        //user-role表改,先删除，后插入
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userDto.getId());
        userRoleService.remove(wrapper);

        Long[] roleIds = userDto.getRoleIds();
        if (!ObjectUtils.isEmpty(roleIds)) {
            List<UserRole> userRoles = Arrays.stream(roleIds)
                    .map(roleId -> new UserRole(userDto.getId(), roleId))
                    .collect(Collectors.toList());
            userRoleService.saveBatch(userRoles);
        }

        return ResponseResult.okResult();
    }

    private boolean phoneExist(String phonenumber) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhonenumber, phonenumber);
        return count(wrapper) == 0 ? false : true;
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return count(wrapper) == 0 ? false : true;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNickName, nickName);
        return count(wrapper) == 0 ? false : true;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        return count(wrapper) == 0 ? false : true;
    }
}

