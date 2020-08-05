package com.ht.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.SysLog;

public interface SysLogService extends IService<SysLog>{
	public void save(PageData pd)throws Exception;
	public Integer findlistPage()throws Exception;
	public Integer findLoginDay(PageData pd)throws Exception;
	public boolean findLogin10Day(String userId)throws Exception;
	public boolean findLogin20Day(String userId)throws Exception;
	public boolean findLogin365Day(String userId)throws Exception;
	public PageData getLoginDayPD(String userId,int day)throws Exception;
	public void setLoginUser(String userId) throws Exception ;
}
