package com.fly4j.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.auth.entity.SysPermission;
import com.fly4j.auth.mapper.SysPermissionMapper;
import com.fly4j.auth.service.ISysPermissionService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<SysPermission> getByUserId(Long userId) {
        return this.baseMapper.selectByUserId(userId);
    }
}