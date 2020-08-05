package com.ht.biz.service.impl.hisself.xml.entity;

import javax.xml.bind.annotation.XmlAttribute;

public class ModelValue {

	/*
	 * 最小值
	 */
	@XmlAttribute
	private double min;
	/*
	 * 最大值
	 */
	@XmlAttribute
	private double max;
	/*
	 * 取值
	 */
	@XmlAttribute
	private double val;

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

}
