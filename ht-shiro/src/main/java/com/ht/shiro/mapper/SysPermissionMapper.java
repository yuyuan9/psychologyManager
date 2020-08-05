package com.ht.shiro.mapper;



import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.SysPermission;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jied
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    //List<SysPermission> selectPermByUser(UserInfo userInfo);
	
	List<SysPermission> findPermsByRoleId(Integer roleid);
	
	List<SysPermission> findPermsMenuByUserId(String userid);

	List<SysPermission> findList(MyPage mypage);

    List<SysPermission> findbuttonPermsByRoleId(Integer roleid);

	List<SysPermission> findallPermsByRoleId(Integer roleid);
}
