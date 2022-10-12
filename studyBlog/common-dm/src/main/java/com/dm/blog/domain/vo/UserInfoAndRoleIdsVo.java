package com.dm.blog.domain.vo;


import com.dm.blog.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRoleIdsVo {

    private List<Long> roleIds;

    private List<RoleVo> roles;

    private UserVo user;

}
