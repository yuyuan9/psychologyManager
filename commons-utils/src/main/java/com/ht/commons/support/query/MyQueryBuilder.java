package com.ht.commons.support.query;


import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.commons.utils.StringUtil;

/**
 * 条件查询处理接口 1、解决由PageData(Map 集合传过来的参数) 2、传参数默认为[name]$eq 3、[name]$like 转义为like查询
 * 
 * @author jied
 *
 */
public class MyQueryBuilder<T> {

	private QueryWrapper<T> querywarpper;

	private Map pd;
	
	public MyQueryBuilder(Map pd) {
		this.pd = pd;
	}

	public MyQueryBuilder(QueryWrapper querywarpper, Map pd) {
		this.querywarpper = querywarpper;
		this.pd = pd;
	}

	public MyQueryBuilder() {

	}

	public QueryWrapper builder() {
		if (querywarpper == null) {
			querywarpper = new QueryWrapper();
		}

		if (pd == null) {
			return querywarpper;
		} else {
			Iterator<String> iter = pd.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				convertMapKey(key,pd.get(key));
			}
		}

		return querywarpper;
	}
	/*
	 * 转换map 键值
	 */
	private void convertMapKey(String key,Object value) {
		if(StringUtils.endsWithIgnoreCase(key, QueryType.$like.name())) {
			querywarpper.like(getKey(key,QueryType.$like.name()), value);
		}else if(StringUtils.endsWithIgnoreCase(key, QueryType.$likeor.name())){
			String[] arr=getArray(key,QueryType.$likeor);
			if(arr.length==1) {
				querywarpper.like(arr[0], value);
			}else if(arr.length==2) {
				querywarpper.and(wrapper->wrapper.like(arr[0], value).or().like(arr[1], value));
			}else if(arr.length==3) {
				querywarpper.and(wrapper->wrapper.like(arr[0], value).or().like(arr[1], value));
			}else if(arr.length==4) {
				querywarpper.and(wrapper->wrapper.like(arr[0], value).or()
						                         .like(arr[1], value).or()
						                         .like(arr[2], value).or()
						                         .like(arr[3],value));
			}else if(arr.length==5) {
				querywarpper.and(wrapper->wrapper.like(arr[0], value).or()
						                         .like(arr[1], value).or()
						                         .like(arr[2], value).or()
						                         .like(arr[3],value).or().like(arr[4], value));
			}
			
		}
		else if(StringUtils.endsWithIgnoreCase(key, QueryType.$eq.name())) {
			querywarpper.eq(getKey(key,QueryType.$eq.name()), value);
		}else if(StringUtils.endsWithIgnoreCase(key, QueryType.$gt.name())) {
			querywarpper.gt(getKey(key,QueryType.$gt.name()), Long.valueOf(String.valueOf(value)));
		}else if(StringUtils.endsWithIgnoreCase(key, QueryType.$lt.name())) {
			querywarpper.lt(getKey(key,QueryType.$lt.name()), Long.valueOf(String.valueOf(value)));
		}else if(StringUtils.endsWithIgnoreCase(key, QueryType.$lte.name())) {
			querywarpper.le(getKey(key,QueryType.$lte.name()), Long.valueOf(String.valueOf(value)));
		}else if(StringUtils.endsWithIgnoreCase(key, QueryType.$gte.name())) {
			querywarpper.ge(getKey(key,QueryType.$gte.name()), Long.valueOf(String.valueOf(value)));
		}else if(StringUtils.endsWithIgnoreCase(key, QueryType.desc$.name())) {
			querywarpper.orderByDesc(getArray(key,QueryType.desc$));
		}else if(StringUtils.endsWithIgnoreCase(key, QueryType.asc$.name())) {
			querywarpper.orderByAsc(getArray(key,QueryType.asc$));
		}
		
	}
	
	private String getKey(String key,String delstr) {
		return StringUtils.remove(key, delstr);
	}
	
	/**
	 * 分号分隔 排序  如  desc$a;b   asc$c;e
	 * @param key
	 * @param delstr
	 * @return
	 */
	/*private String[] getOrderBy(String key,QueryType value) {
		if(QueryType.desc$.name().equals(value.name())) {
			String trim_value=StringUtils.trim(StringUtils.remove(key, value.name()));
			return StringUtil.getStringArray(trim_value);
		}else {
			String trim_value=StringUtils.trim(StringUtils.remove(key, QueryType.asc$.name()));
			return StringUtil.getStringArray(trim_value);
		}
	}*/
	
	private String[] getArray(String key,QueryType value) {
		String trim_value=StringUtils.trim(StringUtils.remove(key, value.name()));
		return StringUtil.getStringArray(trim_value);
	}
	
	public enum QueryType{
		$like,//模糊
		$likeor,//多个字段or模糊 最多只可以传递4个
		$eq,//等于
		$gt,//大于
		$lt,//小于
		$lte,//小于等于
		$gte,//大于等于
		desc$,//降序
		asc$//升序
	}

}
