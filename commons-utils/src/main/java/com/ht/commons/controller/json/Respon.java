package com.ht.commons.controller.json;

import com.ht.commons.utils.MyPage;
import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.commons.constants.Const.Code;

import java.util.List;

/**
 * JSON通用返回
 * 
 * @author jied
 *
 */
public class Respon {
	private String code;
	private String msg = "";
	private Object data;
	private Object reserveData;

	public Object getReserveData() {
		return reserveData;
	}

	public Respon setReserveData(Object reserveData) {
		this.reserveData = reserveData;
		return this;
	}

	public Respon() {

	}

	public Respon(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public Respon setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Respon setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Respon setData(Object data) {
		this.data = data;
		return this;
	}

	public Respon success(Object data) {
		this.setCode(Code.C10000.getKey());
		this.setMsg(Code.C10000.getVal());
		this.setData(data);
		return this;
	}

	public Respon error(Exception e) {
		this.setCode(Code.C10110.getKey());
		this.setMsg(e.getMessage());
		return this;
	}

	public Respon error(Object obj) {
		this.setCode(Code.C10110.getKey());
		this.setMsg(Code.C10110.getVal());
		this.setData(obj);
		return this;
	}

	public Respon error(String errorMsg) {
		this.setCode(Code.C10110.getKey());
		if (StringUtils.isBlank(errorMsg)) {
			this.setMsg(Code.C10110.getVal());
		} else {
			this.setMsg(errorMsg);
		}
		return this;
	}
	public Respon loginerror(String errorMsg) {
		this.setCode(Code.C10119.getKey());
		if (StringUtils.isBlank(errorMsg)) {
			this.setMsg(Code.C10119.getVal());
		} else {
			this.setMsg(errorMsg);
		}
		return this;
	}
	public Respon error() {
		return error(StringUtils.EMPTY);
	}

	public Respon success(Object data,String url) {
		this.setCode(Code.C10000.getKey());
		this.setMsg(Code.C10000.getVal());
		this.setData(data);
		this.setReserveData(url);
		return this;
	}
	public Respon success(Object data,Page page) {
		this.setCode(Code.C10000.getKey());
		this.setMsg(Code.C10000.getVal());
		this.setData(data);
		this.setReserveData(page);
		return this;
	}
	public Respon success(Object data,Object page) {
		this.setCode(Code.C10000.getKey());
		this.setMsg(Code.C10000.getVal());
		this.setData(data);
		this.setReserveData(page);
		return this;
	}
}
