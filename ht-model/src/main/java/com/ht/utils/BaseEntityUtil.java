package com.ht.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.ht.commons.utils.UuidUtil;
import com.ht.entity.base.BaseEntity;

public class BaseEntityUtil {
	
	public static BaseEntity  getBaseEntity(BaseEntity entity) {
		if(StringUtils.isBlank(entity.getId())) {
			entity.setCreatedate(new Date());
			entity.setCreateid("");
			entity.setRegionid("");
		}else {
			entity.setLastmodified(new Date());
		}
		return entity;
	}

}
