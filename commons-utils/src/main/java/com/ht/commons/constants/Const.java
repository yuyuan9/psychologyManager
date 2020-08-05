package com.ht.commons.constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.context.ApplicationContext;

/**
 * 静态变量配置类
 * @author jied
 *
 */
public class Const {
	public static  long maxipload = 10485760;
	public static  long sysmaxipload = 20971520;
    public  static String adminupload ="高企云";

	//reids 保存key前缀
    public  static String redisusertype="redisusertype";
	//filemanager
	public static String uploadpath = "http://47.92.70.12:9090/fileUpload";
	public static String filepaht = "http://47.92.70.12:9090";
	public static String zipfile = "http://47.92.70.12:9090/filezip";

//	public static String uploadpath = "http://localhost:9090/fileUpload";
//	public static String filepaht = "http://localhost:9090";
//	public static String zipfile = "http://localhost:9090/filezip";

	public static int RETURN_ERROR=-1;
	public static String SESSION_USER="session_user";

	public static final String SESSION_SECURITY_CODE = "sessionSecCode";

	public static  final  String errorString ="static/statics/errr.pdf";
	public static  final  String ciku ="static/statics/ciku.dic";
	public static final String wenku="com.fh.entity.system.Library";

	public static final String ziyuanbao ="com.ht.entity.biz.library.ResourcePag";
	public static final String SESSION_SECURITY_Path = "result_paht";
	//*************对接solr引擎项目*************//
	//public static String jsonrpcUrl="http://127.0.0.1:8085/jsonrpc";
	//public static String jsonrpcUrl="http://47.92.26.13:8085/jsonrpc";
	public static String jsonrpcUrl="http://47.92.70.12:7080/jsonrpc";
	//public static String solrUrl="http://admin:2019gqy123@localhost:8099/solr/";
	public static String solrUrl="http://admin:2019gqy123@47.92.70.12/solr/";
	public static String solrCore_policylib="policylib";
	public static String solrCore_projectlib="projectlib";
	public static String solrCore_community="community";
	public static String solrCore_comments="comments";
	public static String solrCore_reply="reply";
	public static String solrCore_compnew="compnew";
	public static String solrCore_comppatentrainpat="comppatentrainpat";
	public static String solrCore_compsoftware="compsoftware";
	public static String solrCore_compbrand="compbrand";
	public static String solrCore_policydig="policydig";
	public static String solrCore_library="library";
	public static Integer poli_pro_counts=20;//政策立项免费查询次数
	public static Integer company_info_rz=50;//高企查询次数
	//*************对接solr引擎项目*************//

	//public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	/**
	 * json异常类型
	 * @author jied
	 *
	 */
	
	//支付宝相关配置
		public  static  final String  alipayKey="blkf2pxd3z3onqizg0g1c5oo2naclrr9";
		public  static  final  String alipayPartner="2088121907811170";
		public  static  final String alipayAccountName="广州高企云信息科技有限公司";
		public  static  final String alipayEmail="gaoqiyun@doctortech.com.cn";
		public  static  final String siteName="广州高企云信息科技有限公司";
		public  static  final String siteUrl="http://www.hights.cn";
		//public  static  final String siteUrl="http://localhost:8087";
		
		//生成支付宝订单号
		public  static String getOrderNo(){
			Date n = new Date();
			SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String currTime = outFormat.format(n);
			Random random=new Random();
		    int rm=random.nextInt(1000000000);
		    String no=currTime+rm;
		    return no;
		}
		
	
	
	public enum  Code{
		C_Key_Val("code","msg"),
		C10000("10000","success"),
		C10110("10110","error"),
		C10001("10001","token错误"),
		C10002("10002","用户无权限"),
		C10003("10003","代码异常"),
		C10119("10119","未登录");
		
		
		
		
		
		
		private String key;
		private String val;
		
		Code(String _key,String _val) {
			this.key=_key;
			this.val=_val;
		}

		public String getKey() {
			return key;
		} 

		public void setKey(String key) {
			this.key = key;  
		}

		public String getVal() {
			return val;
		}

		public void setVal(String val) {
			this.val = val;
		}
		
	
	}	

}

	
