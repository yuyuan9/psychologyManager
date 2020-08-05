package com.ht.biz.service.impl.hisself;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ht.biz.service.impl.hisself.xml.entity.Model;
import com.ht.biz.service.impl.hisself.xml.entity.ModelValue;
import com.ht.biz.service.impl.hisself.xml.impl.ReadXmlImpl;
import com.ht.commons.utils.Tools;


/*
 * 单列调用
 */
public class FinanceCalculation {
	
	public static String KEY_NET_ASSET="netasset";
	
	public static String KEY_SALES_REVENUE="salesrevenue";
	
	public static String KEY_YEAR_AVG_PROFIT="yearavgprofit";

	private Map<String, Model> models = new HashMap<String, Model>();

	// 计算目标源
	public Object target;

	public FinanceCalculation(Object _target) {
		this.target = _target;
		initModels();
	}
	/*
	 * 根据类型来计算财务三大利润数值
	 */
	public double calcValue(String type){
		double value= executeModelExpr(getModel(type));
		if(value<=0d){
			return 0d;
		}
		//System.out.println(value);
		return calcModelValue(type, value*100);
	}
	
	public double calcPercent(String type){
		double value= executeModelExpr(getModel(type));
		if(value<=0d){
			return 0d;
		}
		BigDecimal bvalue = new BigDecimal(value).setScale(BigDecimal.ROUND_HALF_DOWN, 2);
		return bvalue.doubleValue();
	}
	

	private Model getModel(String type) {
		return models.get(type);
	}
	/*
	 * 计算模型值
	 */
	private double calcModelValue(String type, double value) {
		Model model = getModel(type);
		double returnVal = 0d;
		for (ModelValue mv : model.getModelValueList()) {
			if (value >= mv.getMin() && value < mv.getMax()) {
				returnVal = mv.getVal();
				break;
			}
		}
		return returnVal;
	}
	/*
	 * 执行模型表达式
	 */
	private double executeModelExpr(Model model) {
		//System.out.println("================================");
		//System.out.println(model.getExpression());
		//System.out.println(Tools.replaceTemplate(model.getExpression()));
		String value = Tools.parseTemplate(Tools.replaceTemplate(model.getExpression()), target);
		try {
			value=StringUtils.replace(value, ",", "");
			return Double.valueOf(value);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	/*
	 * 初始化模型
	 */
	private void initModels() {
		List<Model> list = ReadXmlImpl.getModels();
		for (Model m : list) {
			models.put(m.getType(), m);
		}
	}

}
