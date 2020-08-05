package com.ht.commons.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class MyPage<T> extends Page<T> {
	
	private PageData pd;

	public PageData getPd() {
		return pd;
	}

	public void setPd(PageData pd) {
		this.pd = pd;
	}

	public MyPage(PageData pd) {
		super();
		this.pd = pd;
	}
	
	public MyPage() {
		super();
	}
	
	

}
