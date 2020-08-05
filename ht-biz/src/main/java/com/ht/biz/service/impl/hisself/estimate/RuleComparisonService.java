package com.ht.biz.service.impl.hisself.estimate;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ht.biz.service.impl.hisself.FinanceCalculation;
import com.ht.commons.support.groovy.ScriptRule;
import com.ht.commons.support.groovy.ScriptRuleImpl;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.entity.biz.freeassess.HisSelfTesting;


/**
 * 规则对比设置服务类
 * 
 */
@SuppressWarnings("unchecked")
@Service("ruleComparisonService")
public class RuleComparisonService{
	
	
	public String getTable(HisSelfTesting testing){
		Map<String,Product> map = getMap(testing.getCity());
		StringBuffer table=new StringBuffer();
		if(map.size()!=0){
			Product number = map.get(Product.PType.number.name());
			Product quota = map.get(Product.PType.quota.name());
			Product peiyu_introduce=map.get(Product.PType.peiyu_introduce.name());
			Product gaoqi_introduce=map.get(Product.PType.gaoqi_introduce.name());
			Product qiye_detail=map.get(Product.PType.qiye_detail.name());
			Product gaopei_yes_no=map.get(Product.PType.gaopei_yes_no.name());
			Product gaoqi_yes_no=map.get(Product.PType.gaoqi_yes_no.name());
			
			table.append(tableHead(number.getName(),
								   quota.getName(),
					               peiyu_introduce.getName(),
					               gaoqi_introduce.getName(),
					               qiye_detail.getName(),
					               gaopei_yes_no.getName(),
					               gaoqi_yes_no.getName()));
		
			table.append(tableTr(testing,number.getFoundTime(),
					 			 quota.getFoundTime(),
								 peiyu_introduce.getFoundTime(),
					             gaoqi_introduce.getFoundTime(),
					             qiye_detail.getFoundTime(),
					             gaopei_yes_no.getFoundTime(),
					             gaoqi_yes_no.getFoundTime()));
			table.append(tableTr(testing,number.getTechField(),
					 			 quota.getTechField(),
								 peiyu_introduce.getTechField(),
					             gaoqi_introduce.getTechField(),
					             qiye_detail.getTechField(),
					             gaopei_yes_no.getTechField(),
					             gaoqi_yes_no.getTechField()));
			table.append(tableTr(testing,number.getPersForm(),
					  			 quota.getPersForm(),
								 peiyu_introduce.getPersForm(),
					             gaoqi_introduce.getPersForm(),
					             qiye_detail.getPersForm(),
					             gaopei_yes_no.getPersForm(),
					             gaoqi_yes_no.getPersForm()));
			table.append(tableTr(testing,number.getRdCost(),
					 			 quota.getRdCost(),
								 peiyu_introduce.getRdCost(),
					             gaoqi_introduce.getRdCost(),
					             qiye_detail.getRdCost(),
					             gaopei_yes_no.getRdCost(),
					             gaoqi_yes_no.getRdCost()));
			table.append(tableTr(testing,number.getHincome(),
					 			 quota.getHincome(),
								 peiyu_introduce.getHincome(),
					             gaoqi_introduce.getHincome(),
					             qiye_detail.getHincome(),
					             gaopei_yes_no.getHincome(),
					             gaoqi_yes_no.getHincome()));
			table.append(tableTr(testing,number.getCop(),
					 		     quota.getCop(),
								 peiyu_introduce.getCop(),
					             gaoqi_introduce.getCop(),
					             qiye_detail.getCop(),
					             gaopei_yes_no.getCop(),
					             gaoqi_yes_no.getCop()));
			table.append(tableTr(testing,number.getIntelProp(),
					 			 quota.getIntelProp(),
								 peiyu_introduce.getIntelProp(),
					             gaoqi_introduce.getIntelProp(),
					             qiye_detail.getIntelProp(),
					             gaopei_yes_no.getIntelProp(),
					             gaoqi_yes_no.getIntelProp()));
			table.append(tableTr(testing,number.getStach(),
					 			 quota.getStach(),
								 peiyu_introduce.getStach(),
					             gaoqi_introduce.getStach(),
					             qiye_detail.getStach(),
					             gaopei_yes_no.getStach(),
					             gaoqi_yes_no.getStach()));
			table.append(tableTr(testing,number.getRdManag(),
					 			 quota.getRdManag(),
								 peiyu_introduce.getRdManag(),
					             gaoqi_introduce.getRdManag(),
					             qiye_detail.getRdManag(),
					             gaopei_yes_no.getRdManag(),
					             gaoqi_yes_no.getRdManag()));
			table.append(tableTr(testing,number.getCompGrowth(),
					 			 quota.getCompGrowth(),
								 peiyu_introduce.getCompGrowth(),
					             gaoqi_introduce.getCompGrowth(),
					             qiye_detail.getCompGrowth(),
					             gaopei_yes_no.getCompGrowth(),
					             gaoqi_yes_no.getCompGrowth()));
		}
		if(testing.getCity().contains("苏州")){
			//table.append("<tr><th>指标</th><th>高企认定</th><th>高培入库</th><th>企业实际</th><th>高企</th><th>高培</th></tr>");
			//table.append(testing.getSztable());
		}
		return table.toString();
	}


	/**
	 * 动态执行java代码
	 * @param script
	 * @return
	
   public String evalScript(String script,HisSelfTesting testing){
	   ScriptRule scriptrule = new ScriptRuleImpl();
	   Object retval=scriptrule.evalMehtodApplyCondition(script, testing);
	   return String.valueOf(retval);
   } */
	
	public Map<String,Product> getMap(String city){
		
		ReadXml<Product> readxml = ReadXml.getInstance(Product.class);
		List<Product> list = readxml.getList();
		Map<String,Product> map = convertMap(list);
		map=filterMap(map,city);
		return map;
	}
	

	private Map<String,Product> convertMap(List<Product> list){
		Map<String,Product> map = new HashMap<String,Product>();
		for(Product p:list){
			System.out.println(p.getType());
			map.put(p.getType(), p);
		}
		return map;
	}
	
	private Map<String,Product> filterMap(Map<String,Product> map,String city){
		Map<String,Product> ret_map = new LinkedHashMap<String,Product>();
		if(StringUtils.contains(city, "广州")){
			ret_map.put(Product.PType.number.name(), map.get(Product.PType.number.name()));
			ret_map.put(Product.PType.quota.name(), map.get(Product.PType.quota.name()));
			ret_map.put(Product.PType.peiyu_introduce.name(), map.get(Product.PType.gaoqi_introduce.name()));
			ret_map.put(Product.PType.gaoqi_introduce.name(), map.get(Product.PType.guangzhou_xiaojuren_ruku.name()));
			ret_map.put(Product.PType.qiye_detail.name(), map.get(Product.PType.guangzhou_qiye_detail.name()));
			ret_map.put(Product.PType.gaopei_yes_no.name(), map.get(Product.PType.guangzhou_gaoqirending_yes_no.name()));
			ret_map.put(Product.PType.gaoqi_yes_no.name(), map.get(Product.PType.xiaojuren_yes_no.name()));
		}else if(StringUtils.contains(city, "金华")){
			ret_map.put(Product.PType.number.name(), map.get(Product.PType.number.name()));
			ret_map.put(Product.PType.quota.name(), map.get(Product.PType.quota.name()));
			ret_map.put(Product.PType.peiyu_introduce.name(), map.get(Product.PType.jinhua_shigaorending.name()));
			ret_map.put(Product.PType.gaoqi_introduce.name(), map.get(Product.PType.gaoqi_introduce.name()));
			ret_map.put(Product.PType.qiye_detail.name(), map.get(Product.PType.jinhua_qiye_detail.name()));
			ret_map.put(Product.PType.gaopei_yes_no.name(), map.get(Product.PType.jinhua_shigao_yes_no.name()));
			ret_map.put(Product.PType.gaoqi_yes_no.name(), map.get(Product.PType.jinhua_guogao_yes_no.name()));
		}else if(StringUtils.contains(city, "淮安")){
			ret_map.put(Product.PType.number.name(), map.get(Product.PType.number.name()));
			ret_map.put(Product.PType.quota.name(), map.get(Product.PType.quota.name()));
			ret_map.put(Product.PType.peiyu_introduce.name(), map.get(Product.PType.peiyu_introduce.name()));
			ret_map.put(Product.PType.gaoqi_introduce.name(), map.get(Product.PType.gaoqi_introduce.name()));
			ret_map.put(Product.PType.qiye_detail.name(), map.get(Product.PType.qiye_detail.name()));
			ret_map.put(Product.PType.gaopei_yes_no.name(), map.get(Product.PType.huaian_gaopei_yes_no.name()));
			ret_map.put(Product.PType.gaoqi_yes_no.name(), map.get(Product.PType.huaian_gaoqi_yes_no.name()));
		}else if(StringUtils.contains(city, "苏州")){
			ret_map.put(Product.PType.number.name(), map.get(Product.PType.number.name()));
			ret_map.put(Product.PType.quota.name(), map.get(Product.PType.quota.name()));
			ret_map.put(Product.PType.peiyu_introduce.name(), map.get(Product.PType.suzhou_gaoqirending.name()));
			ret_map.put(Product.PType.gaoqi_introduce.name(), map.get(Product.PType.suzhou_gaopeiruku.name()));
			ret_map.put(Product.PType.qiye_detail.name(), map.get(Product.PType.suzhou_qiye_detail.name()));
			ret_map.put(Product.PType.gaopei_yes_no.name(), map.get(Product.PType.suzhou_gaoqi.name()));
			ret_map.put(Product.PType.gaoqi_yes_no.name(), map.get(Product.PType.suzhou_gaopei.name()));
		}else{
			ret_map.put(Product.PType.number.name(), map.get(Product.PType.number.name()));
			ret_map.put(Product.PType.quota.name(), map.get(Product.PType.quota.name()));
			ret_map.put(Product.PType.peiyu_introduce.name(), map.get(Product.PType.suzhou_gaoqirending.name()));
			ret_map.put(Product.PType.gaoqi_introduce.name(), map.get(Product.PType.suzhou_gaopeiruku.name()));
			ret_map.put(Product.PType.qiye_detail.name(), map.get(Product.PType.suzhou_qiye_detail.name()));
			ret_map.put(Product.PType.gaopei_yes_no.name(), map.get(Product.PType.suzhou_gaoqi.name()));
			ret_map.put(Product.PType.gaoqi_yes_no.name(), map.get(Product.PType.quanguo_gaopei.name()));
		}
		
		return ret_map;
	}
	
	private String tableHead(String ...val){
		StringBuffer head = new StringBuffer();
		head.append("<tr align='center'>")
		    .append("<th>%s</th>")
		    .append("<th>%s</th>")
		    .append("<th>%s</th>")
		    .append("<th>%s</th>")
		    .append("<th>%s</th>")
		    .append("<th>%s</th>")
		    .append("<th>%s</th>")
		    .append("</tr>");
		return String.format(head.toString(), val);
	}
	
	private String tableTr(HisSelfTesting testing,String ...val){
		StringBuffer tr = new StringBuffer();
		tr.append("<tr align='center'>")
		  .append("<td>%s</td>")
		  .append("<td>%s</td>")
		  .append("<td>%s</td>")
		  .append("<td>%s</td>")
		  .append("<td>%s</td>")
		  .append("<td>%s</td>")
		  .append("<td>%s</td>")
		  .append("</tr>");
		 ScriptRule scriptrule = new ScriptRuleImpl();
		 List arr = new ArrayList();
		 
		 Map<String,Object> bind = getBind(testing);
		 for(String script:val){
			 System.out.println("script:"+script);
			 Object retval=scriptrule.evalDefaultScript(script, testing,bind);
			 System.out.println("结果："+retval);
			 arr.add(retval);
		 }
		 
		return String.format(tr.toString(), arr.toArray());
	}
	
	private Map<String,Object> getBind(HisSelfTesting testing){
		FinanceCalculation fc=new FinanceCalculation(testing);
		Map<String,Object> bind=new HashMap<String,Object>();
		bind.put("Date", "java.util.Date");
		bind.put("DateUtil",new DateUtil());
		bind.put("defaultSFT", new java.text.SimpleDateFormat("yyyy-MM-dd"));
		bind.put("yearSFT", new java.text.SimpleDateFormat("yyyy"));
		bind.put("BigDecimal", "java.math.BigDecimal");
		bind.put("netasset", fc.calcValue(FinanceCalculation.KEY_NET_ASSET));
		bind.put("salesrevenue", fc.calcValue(FinanceCalculation.KEY_SALES_REVENUE));
		
		
		return bind;
	}
	
	
	
	
	
	
	
	
}
