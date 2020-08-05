package com.ht.shiro.service.regioncompany;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.shiro.regioncompany.RegionCompany;

public interface RegionCompanyService extends IService<RegionCompany> {
	/**
	 * 查找
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<RegionCompany> findListPage(MyPage page)throws Exception;

	RegionCompany getbynumber(String number);
}
