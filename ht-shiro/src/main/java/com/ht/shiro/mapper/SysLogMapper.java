package com.ht.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.SysLog;

public interface SysLogMapper extends BaseMapper<SysLog>{
	public void save(PageData pd)throws Exception;
	public Integer findlistPage()throws Exception;
	public Integer findLoginDay(PageData pd)throws Exception;
}
