package com.ht.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.commons.utils.UuidUtil;
import com.ht.entity.base.BaseEntity;

public class TreeEntityUtil {
	
	public static TreeEntity  getTreeEntity(TreeEntity entity) {
		if(ObjectUtils.isEmpty(entity.getId())) {
			entity.setCreatedate(new Date());
			entity.setCreateid("");
		}else {
			entity.setLastmodified(new Date());
		}
		return entity;
	}

}
