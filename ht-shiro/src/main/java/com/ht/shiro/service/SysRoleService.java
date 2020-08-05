package com.ht.shiro.service;


import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.SysRole;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
public interface SysRoleService extends IService<SysRole> {

    
    Page<SysRole> findListpage(MyPage page)throws Exception;
    
    boolean update(SysRole role)throws Exception;
    
    boolean deleted(Integer id)throws Exception;
    
} 
