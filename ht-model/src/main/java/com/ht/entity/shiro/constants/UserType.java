package com.ht.entity.shiro.constants;

/**
 * 用户类型
 * @author jied
 *
 */
public enum UserType {

	///后改用户
	ADMIN("ADMIN","系统管理员"),//系统管理员
	REGION_COMPANY("REGION_COMPANY","区域公司"),//區域公司
	
	///前台用户
	EXPERT("EXPERT","专家"),//专家
	SERVICE_PROVIDER("SERVICE_PROVIDER","服务商"),//服务商
	COMPANY_USER("COMPANY_USER","企业用戶"),//企业用戶
	DEFAULT_USER("DEFAULT_USER","普通用户"),
	GOVERMENT_USER("GOVERMENT_USER","政府用户"),;



	private String  code;

	private String value;


	UserType(String  code, String value) {
		this.code = code;
		this.value = value;
	}


	public static String getCode(String  code) {
		UserType[] imageFormatTypes = values();
		for (UserType imageFormatType : imageFormatTypes) {
			if (imageFormatType.code().equals(code)) {
				return imageFormatType.code();
			}
		}
		return null;
	}

	public static String getValue(String code) {
		UserType[] imageFormatTypes = values();
		for (UserType imageFormatType : imageFormatTypes) {
			if (imageFormatType.code().equals(code)) {
				return imageFormatType.value();
			}
		}
		return null;
	}

	public String  code() {
		return code;
	}

	public String value() {
		return value;
	}
}
