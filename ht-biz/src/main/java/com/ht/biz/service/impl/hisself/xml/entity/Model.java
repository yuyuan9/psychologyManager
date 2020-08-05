package com.ht.biz.service.impl.hisself.xml.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

public class Model {
	/*
	 * 类型
	 */
	@XmlAttribute
	private String type;
	/*
	 * 表达式
	 */
	@XmlAttribute
	private String expression;
	/*
	 * 模型列表
	 */
	private List<ModelValue> modelValueList = new ArrayList<ModelValue>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public List<ModelValue> getModelValueList() {
		return modelValueList;
	}

	public void setModelValueList(List<ModelValue> modelValueList) {
		this.modelValueList = modelValueList;
	}

	@Override
	public String toString() {
		return "Model [type=" + type + ", expression=" + expression + ", modelValueList=" + modelValueList + "]";
	}
	
	

}
