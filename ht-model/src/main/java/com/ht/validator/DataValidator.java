package com.ht.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ht.commons.utils.ReflectHelper;
import com.ht.validator.annotation.CompareNum;
import com.ht.validator.annotation.Date;
import com.ht.validator.annotation.Num;
import com.ht.validator.annotation.Regular;
import com.ht.validator.annotation.Regular.RegType;
import com.ht.validator.annotation.Required;


@SuppressWarnings("all")
public class DataValidator<T> implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		Class<T> cls = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return cls.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		try {
			Class<?> clazz = target.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				Object o = ReflectHelper.getValueByFieldName(target, field.getName());
				Annotation[] annotations = field.getDeclaredAnnotations();
				for (Annotation a : annotations) {
					if (a instanceof Date) {
						date(o, field.getName(), errors);
					} else if (a instanceof Num) {
						num(o, field.getName(), errors);
					} else if (a instanceof Required) {
						Required r = (Required) a;
						required(o, field.getName(), errors, r.errmsg());
					} else if (a instanceof Regular) {
						Regular r = (Regular) a;
						regular(o, field.getName(), errors, r);
					}else if( a instanceof CompareNum){
						CompareNum c = (CompareNum) a;
						compareValue(o, field.getName(), errors,c,target);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * 比较大小
	 */
	private void compareValue(Object value,String field,Errors errors,CompareNum c,Object target){
		String strValue = String.valueOf(value);
		try{
			if(!StringUtils.isBlank(strValue)){
				System.out.println(c.maxField());
				Object maxvalue = ReflectHelper.getValueByFieldName(target, c.maxField());
				double _value=Double.valueOf(strValue);
				double _maxvalue=Double.valueOf(String.valueOf(maxvalue));
				if(_value>_maxvalue){
					errors.rejectValue(field, null, c.error());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	private void date(Object value, String field, Errors errors) {
		try {
			java.util.Date d = (java.util.Date) value;
		} catch (Exception e) {
			errors.rejectValue(field, null, Message.date);
		}
	}
	


	/*
	 * 是否为数字
	 */
	private void num(Object value, String field, Errors errors) {
		String strValue = String.valueOf(value);
		if(value instanceof Double){
			java.text.NumberFormat NF = java.text.NumberFormat.getInstance();   
			NF.setGroupingUsed(false);
			strValue=NF.format(value);
		}
		//boolean check = strValue.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		boolean check=strValue.matches("([0-9]\\d*\\.?\\d*)|((-)?[0-9]\\d*\\.?\\d*)");
		if (!check) {
			errors.rejectValue(field, null, Message.num);
		}

	}

	private void required(Object value, String field, Errors errors, String errmsg) {
		String strValue = String.valueOf(value);
		if (StringUtils.isBlank(strValue) || StringUtils.equals("null", strValue)) {
			if (StringUtils.isBlank(errmsg)) {
				errors.rejectValue(field, null, Message.required);
			} else {
				errors.rejectValue(field, null, errmsg);
			}
		}
	}
	
	
	private void regular(Object value, String field, Errors errors, Regular r) {
		String strValue = String.valueOf(value);
		if (StringUtils.isBlank(strValue) || StringUtils.equals("null", strValue)) {
			// StringUtils.isBlank(cs)
		} else {
			Pattern p = null;
			if(RegType.empty.name().equals(r.type().name())){
				p=Pattern.compile(r.value());
			}else{
				p=Pattern.compile(r.type().getValue());
			}
			Matcher m = p.matcher(strValue);
			boolean check = m.matches();
			if (!check) {
				if(StringUtils.isBlank(r.errmsg())){
					errors.rejectValue(field, null, Message.err);
				}else{
					errors.rejectValue(field, null, r.errmsg());
				}
			}
		}
	}

}
