package com.ht.entity.biz.solr.recore;

import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

//收费弹框记录
@TableName("t_cast_box")
public class CastBox extends BaseEntity{
	@TableField(value="userId")
	private String userId;//用户
	private String phone;//用户手机号
	private String module;//模块
	private String code;//编码
	private String remark;//备注
	public String getUserId() {
		return userId;
	}
	public String getModule() {
		return module;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public enum Box{
		policylib_current("政策库翻页扣费提示"),
		projectlib_current("立项库翻页扣费提示"),
		;
		private String value;
		Box(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public static String getValue(String name) {
			String s=null;
			Box[] bs=Box.values();
			for(Box b:bs){
				if(StringUtils.equals(b.name(), name)){
					s=b.value;
					break;
				}
			}
			return s;
		}
	}
	public static void main(String[] args) {
		System.out.println(Box.policylib_current);
		System.out.println(Box.policylib_current.name());
		System.out.println(Box.policylib_current.value);
		System.out.println("=============================");
		System.out.println(Box.getValue("policylib_current"));
		System.out.println(Box.getValue("policylib"));
	}
}
