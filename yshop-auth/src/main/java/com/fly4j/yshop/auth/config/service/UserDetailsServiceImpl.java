package com.fly4j.yshop.auth.config.service;

import com.fly4j.yshop.auth.entity.SysPermission;
import com.fly4j.yshop.auth.entity.SysUser;
import com.fly4j.yshop.auth.service.ISysPermissionService;
import com.fly4j.yshop.auth.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 自定义用户认证和授权
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysPermissionService iSysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = iSysUserService.getByUserName(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (sysUser != null) {
            List<SysPermission> permissions = iSysPermissionService.getByUserId(sysUser.getUserId());
            if(permissions!=null){
                List<String> perms = permissions.stream().map(item -> item.getPerms()).collect(Collectors.toList());
                perms.forEach(perm->{
                    grantedAuthorities.add(new SimpleGrantedAuthority(perm));
                });
            }
        }
        return new User(sysUser.getUserName(),sysUser.getPassword(),true,true,true,true,grantedAuthorities);
    }




}
