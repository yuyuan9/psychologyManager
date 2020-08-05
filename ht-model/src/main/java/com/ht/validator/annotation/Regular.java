package com.ht.validator.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
/*
 * 不对空值进行验证
 */
public @interface Regular {
	String value()  default "";
	String errmsg() default "";
	RegType type() default RegType.empty;
	
	public static enum RegType{
		empty(""),
		email("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$"),
		phone("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\\d{8}$"),
		id_card("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)"),
		number("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	   
		public String value;
		RegType(String _value){
			this.value=_value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
}
	
