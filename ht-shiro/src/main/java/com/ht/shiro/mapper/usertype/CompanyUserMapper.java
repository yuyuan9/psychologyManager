package com.ht.shiro.mapper.usertype;



import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.SysPermission;
import com.ht.entity.shiro.usertype.CompanyUser;
import com.ht.vo.shiro.CompanyUserVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jied
 */
public interface CompanyUserMapper extends BaseMapper<CompanyUser> {

	
	Page<PageData> findlist(MyPage mypage)throws Exception;

	PageData findById(PageData pd);
}
