package com.ht.biz.service.impl.hisself;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ht.commons.utils.Tools;
import org.apache.commons.lang3.ObjectUtils;

import com.ht.commons.support.mysql.DBConnection;

/*
 * 测试
 */
public class TestFC {
	
	private String id;
	
	private Integer x;
	
	private Integer y1;
	
	private Integer y2;

	// 近三年销售
	private Integer saleFirstYear;
	private double saleFirstValue = 0f;
	private Integer saleSecondYear;
	private double saleSecondValue = 0f;
	private Integer saleThirdYear;
	private double saleThirdValue = 0f;

	// 近三年总资产（变更成近三年净资产）
	private Integer assetFirstYear;
	private double assetFirstValue = 0f;
	private Integer assetSecondYear;
	private double assetSecondValue = 0f;
	private Integer assetThirdYear;
	private double assetThirdValue = 0f;

	// 近三年利润总额
	private Integer profitFirstYear;
	private double profitFirstValue = 0f;
	private Integer profitSecondYear;
	private double profitSecondValue = 0f;
	private Integer profitThirdYear;
	private double profitThirdValue = 0f;

	public Integer getSaleFirstYear() {
		return saleFirstYear;
	}

	public void setSaleFirstYear(Integer saleFirstYear) {
		this.saleFirstYear = saleFirstYear;
	}

	public double getSaleFirstValue() {
		return saleFirstValue;
	}

	public void setSaleFirstValue(double saleFirstValue) {
		this.saleFirstValue = saleFirstValue;
	}

	public Integer getSaleSecondYear() {
		return saleSecondYear;
	}

	public void setSaleSecondYear(Integer saleSecondYear) {
		this.saleSecondYear = saleSecondYear;
	}

	public double getSaleSecondValue() {
		return saleSecondValue;
	}

	public void setSaleSecondValue(double saleSecondValue) {
		this.saleSecondValue = saleSecondValue;
	}

	public Integer getSaleThirdYear() {
		return saleThirdYear;
	}

	public void setSaleThirdYear(Integer saleThirdYear) {
		this.saleThirdYear = saleThirdYear;
	}

	public double getSaleThirdValue() {
		return saleThirdValue;
	}

	public void setSaleThirdValue(double saleThirdValue) {
		this.saleThirdValue = saleThirdValue;
	}

	public Integer getAssetFirstYear() {
		return assetFirstYear;
	}

	public void setAssetFirstYear(Integer assetFirstYear) {
		this.assetFirstYear = assetFirstYear;
	}

	public double getAssetFirstValue() {
		return assetFirstValue;
	}

	public void setAssetFirstValue(double assetFirstValue) {
		this.assetFirstValue = assetFirstValue;
	}

	public Integer getAssetSecondYear() {
		return assetSecondYear;
	}

	public void setAssetSecondYear(Integer assetSecondYear) {
		this.assetSecondYear = assetSecondYear;
	}

	public double getAssetSecondValue() {
		return assetSecondValue;
	}

	public void setAssetSecondValue(double assetSecondValue) {
		this.assetSecondValue = assetSecondValue;
	}

	public Integer getAssetThirdYear() {
		return assetThirdYear;
	}

	public void setAssetThirdYear(Integer assetThirdYear) {
		this.assetThirdYear = assetThirdYear;
	}

	public double getAssetThirdValue() {
		return assetThirdValue;
	}

	public void setAssetThirdValue(double assetThirdValue) {
		this.assetThirdValue = assetThirdValue;
	}

	public Integer getProfitFirstYear() {
		return profitFirstYear;
	}

	public void setProfitFirstYear(Integer profitFirstYear) {
		this.profitFirstYear = profitFirstYear;
	}

	public double getProfitFirstValue() {
		return profitFirstValue;
	}

	public void setProfitFirstValue(double profitFirstValue) {
		this.profitFirstValue = profitFirstValue;
	}

	public Integer getProfitSecondYear() {
		return profitSecondYear;
	}

	public void setProfitSecondYear(Integer profitSecondYear) {
		this.profitSecondYear = profitSecondYear;
	}

	public double getProfitSecondValue() {
		return profitSecondValue;
	}

	public void setProfitSecondValue(double profitSecondValue) {
		this.profitSecondValue = profitSecondValue;
	}

	public Integer getProfitThirdYear() {
		return profitThirdYear;
	}

	public void setProfitThirdYear(Integer profitThirdYear) {
		this.profitThirdYear = profitThirdYear;
	}

	public double getProfitThirdValue() {
		return profitThirdValue;
	}

	public void setProfitThirdValue(double profitThirdValue) {
		this.profitThirdValue = profitThirdValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY1() {
		return y1;
	}

	public void setY1(Integer y1) {
		this.y1 = y1;
	}

	public Integer getY2() {
		return y2;
	}

	public void setY2(Integer y2) {
		this.y2 = y2;
	}

	@Override
	public String toString() {
		return "TestFC [id=" + id + ", saleFirstYear=" + saleFirstYear + ", saleFirstValue=" + saleFirstValue
				+ ", saleSecondYear=" + saleSecondYear + ", saleSecondValue=" + saleSecondValue + ", saleThirdYear="
				+ saleThirdYear + ", saleThirdValue=" + saleThirdValue + ", assetFirstYear=" + assetFirstYear
				+ ", assetFirstValue=" + assetFirstValue + ", assetSecondYear=" + assetSecondYear
				+ ", assetSecondValue=" + assetSecondValue + ", assetThirdYear=" + assetThirdYear + ", assetThirdValue="
				+ assetThirdValue + ", profitFirstYear=" + profitFirstYear + ", profitFirstValue=" + profitFirstValue
				+ ", profitSecondYear=" + profitSecondYear + ", profitSecondValue=" + profitSecondValue
				+ ", profitThirdYear=" + profitThirdYear + ", profitThirdValue=" + profitThirdValue + "]";
	}

	public static void main(String[] args) {
//		TestFC test = null;
//		List<TestFC> testlist = new ArrayList<TestFC>();
//
//		try {
//			DBConnection connection = new DBConnection(false);
//			String sql = "select * from t_gov_company_info";
//			ResultSet result = connection.executeQuerySql(sql);
//			while (result.next()) {
//				test = new TestFC();
//				test.setId(result.getString("id"));
//
//				test.setX(result.getInt("x"));
//				test.setY1(result.getInt("y1"));
//				test.setY1(result.getInt("y2"));
//
//				test.setSaleFirstValue(result.getDouble("one_sale"));
//				test.setSaleSecondValue(result.getDouble("two_sale"));
//				test.setSaleThirdValue(result.getDouble("three_sale"));
//
//				test.setAssetFirstValue(result.getDouble("one_netassets"));
//				test.setAssetSecondValue(result.getDouble("two_netassets"));
//				test.setAssetThirdValue(result.getDouble("three_netassets"));
//
//				testlist.add(test);
//
//				//System.out.println(test.toString());
//			}
//			connection.close(connection.getConn(), null, result);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		for (TestFC tfc : testlist) {
			/*
			 FinanceCalculation fc = new FinanceCalculation(tfc);
			double a=fc.calcValue(FinanceCalculation.KEY_NET_ASSET);
			double b=fc.calcValue(FinanceCalculation.KEY_SALES_REVENUE);
			String update_sql0="update t_gov_company_info t set financetotal='%s' where id='%s'";
			update_sql0=String.format(update_sql0, a+b,tfc.getId());
			System.out.println(update_sql0+";");
			*/
			
			/*
			 * X=0且Y=0，                    0
				X=0且0＜Y＜3，                5
				X=0且2＜Y＜5，               10
				X=0且Y＞5，                  20
				X=0且Y＞20，                 24
				1≤X≤5             25
				X＞5，                         27
			 */
		/*	int x=ObjectUtils.defaultIfNull(tfc.getX(), 0);
			int y =ObjectUtils.defaultIfNull(tfc.getY1(), 0) +ObjectUtils.defaultIfNull(tfc.getY2(), 0);
			double total=0;
			if(x==0 && y==0) {
				total=0;
			}else if(x==0 && (y>0 && y<3)) {
				total=5;
			}else if(x==0 && (y>2 && y<5)) {
				total=10;
			}else if(x==0 && ( y>5 && y<=20)) {
				total=20;
			}else if(x==0 && (y>20)) {
				total=24;
			}else if(x>=1 && x<=5) {
				total=25;
			}else if(x>5) {
				total=27;
			}
			
			String update_sql0="update t_gov_company_info t set intelletotal='%s' where id='%s'";
			update_sql0=String.format(update_sql0, total,tfc.getId());
			System.out.println(update_sql0+";");
		}*/

			TestFC tfc = new TestFC();

			tfc.setAssetFirstValue(10);
			tfc.setAssetSecondValue(100);
			tfc.setAssetThirdValue(10000);
			tfc.setSaleFirstValue(10);
			tfc.setSaleSecondValue(100);
			tfc.setSaleThirdValue(10000);

			FinanceCalculation fc = new FinanceCalculation(tfc);
			double a = fc.calcValue(FinanceCalculation.KEY_NET_ASSET);
			double b = fc.calcValue(FinanceCalculation.KEY_SALES_REVENUE);
			double c = fc.calcValue(FinanceCalculation.KEY_YEAR_AVG_PROFIT);
			System.out.println("a:" + a);
			System.out.println("b:" + b);
			System.out.println("c:" + c);
			System.out.println(b + a + c);
	/*		String t = "[#if (obj.saleFirstValue?? @and obj.saleFirstValue gt 0) @and (obj.saleSecondValue?? @and obj.saleSecondValue gt 0)]${((((obj.saleSecondValue/obj.saleFirstValue)+(obj.saleThirdValue/obj.saleSecondValue))/2)-1)?double}[#elseif (obj.saleFirstValue?? @and obj.saleFirstValue lte 0) @and (obj.saleSecondValue?? @and obj.saleSecondValue gt 0)]${((obj.saleThirdValue/obj.saleSecondValue)-1)?double}[#else]0[/#if]";

			TestFC test = null;
			String v = Tools.parseTemplate(Tools.replaceTemplate(t), test);
			System.out.println(v);*/
/*			System.out.println(b);
			System.out.println(c);
			System.out.println(a + b + c);*/

	}
}
