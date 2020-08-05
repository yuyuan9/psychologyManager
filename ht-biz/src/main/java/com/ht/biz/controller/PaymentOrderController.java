package com.ht.biz.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.GuaranteeMoneyService;
import com.ht.biz.service.PaymentOrderService;
import com.ht.biz.service.RechargeService;
import com.ht.biz.util.GenerateQrCodeUtil;
import com.ht.biz.util.JsSdkUtil;
import com.ht.biz.util.PaymentOrderUtil;
import com.ht.biz.util.XMLUtil;
import com.ht.biz.util.plugin.alipayDirect.AlipayDirectPlugin;
import com.ht.biz.util.plugin.alipayDirect.PaymentPlugin.NotifyMethod;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.PaymentOrder;
import com.ht.entity.biz.honeymanager.Recharge;
import com.ht.entity.biz.honeymanager.RewardRule.Code;
import com.ht.entity.biz.product.GuaranteeMoney;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.RewardUtil;
//
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "PaymentOrderController", description = "支付中心前端管理")
@Controller
@RequestMapping(value="/beetl/payment")
public class PaymentOrderController extends BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired 
	PaymentOrderService paymentOrderService;
	
	@Autowired
	AlipayDirectPlugin alipayDirectPlugin;
	
	@Autowired
	RechargeService rechargeService;
	
	@Autowired
	private GuaranteeMoneyService guaranteeMoneyService;
	
	@RequestMapping(value="rechargelist")
	@ResponseBody
	public Respon rechargelist()throws Exception{
		Respon respon = this.getRespon();
		try{
			QueryWrapper<Recharge> qw=new QueryWrapper<Recharge>();
			qw.eq("onlinestatus", 1);
			qw.orderByAsc("honey");
			List<Recharge> list=rechargeService.list(qw);
			respon.success(list);
		}catch(Exception e){
			e.printStackTrace();
			respon.error(e.getMessage());
		}
		return respon;
	} 
		@RequestMapping(value="rechargeDetail")
		@ResponseBody
		public Respon rechargeDetail(String id)throws Exception{
			Respon respon = this.getRespon();
			try{
				if(StringUtils.isNotBlank(id)){
					QueryWrapper<Recharge> qw=new QueryWrapper<Recharge>();
					qw.eq("id", id);
					Recharge recharge=rechargeService.getById(id);
					respon.success(recharge);
				}
			}catch(Exception e){
				e.printStackTrace();
				respon.error(e.getMessage());
			}
			return respon;
		} 
		
		
		@RequestMapping("paymentDetail")
		@ResponseBody
		public Respon paymentDetail(String orderId,String paymentNo)throws Exception{
			Respon respon = this.getRespon();
			try{
				PageData pd = new PageData();
				pd.add("businessId", orderId)
				  .add("businessType", "t_order")
				  .add("paymentNo", paymentNo);
				
				PageData pos = paymentOrderService.findById(pd);
				respon.success(pos);
			}catch(Exception e){
				e.printStackTrace();
				respon.error();
			}
			return respon;
		}
		
		/**
		 * 去支付 paymentCash(1:预付款|2：尾款|3：保证金|4:honey 充值)
		 * 修改后的支付  paymentCash (5：预付款 |6:尾款)
		 * @param id   需要生成的id记录
		 * @param type 类型
		 * @return
		 * @throws Exception
		 */
		@ApiOperation(value="支付统一跳转接口")
		@ApiImplicitParams({
	        @ApiImplicitParam(paramType="query",name = "payPrice", value = "保证金额",  dataType = "double"),
	        @ApiImplicitParam(paramType="query",name = "paymentCash", value = "充值类型(1:预付款|2：尾款|3：保证金|4:honey 充值|5：预付款 |6:尾款)",  dataType = "Integer"),
	        @ApiImplicitParam(paramType="query",name = "project", value = "保证金项目",  dataType = "string"),
		})
		@RequestMapping("payment")
		@ResponseBody
		public Respon payment()throws Exception{
			Respon respon = this.getRespon();
			try{
				PageData pd = new PageData();
				pd = this.getPageData();
				int paymentCash = Integer.parseInt(pd.getString("paymentCash"));
				double payPrice = pd.get("payPrice")==null?0d:Double.valueOf(String.valueOf(pd.get("payPrice")));
				if (pd.containsKey("contractPrice")) {
				}
		
				if (paymentCash == 1) {
				} else if(paymentCash==5){ //新预付款
				}else if (paymentCash == 2) {
				} else if (paymentCash == 6) { //新尾款
				} else if (paymentCash == 3) {
					
				}
				PaymentOrder po = null;
				if (paymentCash == 3) {
					po =PaymentOrderUtil.get("t_sys_service", pd.getString("businessId"), payPrice);
					po.setPaymentNo(Const.getOrderNo());
					po.setPaymenTtitle("保证金" + payPrice + "元");
					po.setPaymentType(pd.getString("paymentType"));
					po.setPaymentCash(paymentCash+"");
					po.setCreatedate(new Date());
					po.setCreateid(LoginUserUtils.getUserId());
					po.setRegionid(LoginUserUtils.getLoginUser().getCompanyid());
					paymentOrderService.save(po);
					GuaranteeMoney gtm=new GuaranteeMoney();
					gtm.setProject(pd.getString("project"));
					gtm.setCast(payPrice);
					gtm.setBeginDate(new Date());
					gtm.setStatus(3);
					gtm.setEndDate(DateUtil.addYear(new Date(), 1));
					gtm.setCreatedate(new Date());
					gtm.setCreateid(LoginUserUtils.getUserId());
					gtm.setRegionid(LoginUserUtils.getLoginUser().getCompanyid());
					gtm.setPaymentOrderId(po.getId());
					guaranteeMoneyService.save(gtm);
				} else if (paymentCash == 2||paymentCash==6) {
		
				} else if (paymentCash == 1||paymentCash==5) {
					
				}else if(paymentCash==4){
					Recharge recharge=rechargeService.getById(pd.getString("id"));
					if(recharge!=null){
						po =PaymentOrderUtil.get("t_recharge", pd.getString("id"), recharge.getNewmoney());
						po.setPaymentNo(Const.getOrderNo());
						po.setPaymenTtitle("honey充值" +  recharge.getNewmoney() + "元");
						po.setPaymentType(pd.getString("paymentType"));
						po.setPaymentCash(paymentCash + "");
						po.setCreatedate(new Date());
						po.setHoneys(recharge.getHoney());
						po.setCreateid(LoginUserUtils.getUserId());
						po.setRegionid(LoginUserUtils.getLoginUser().getCompanyid());
						paymentOrderService.save(po);
					}
				}
		
				pd.put("payPrice", payPrice);
				pd.put("paymentCash", paymentCash);
				// 支付订单记录表记录id
				pd.put("paymentOrderId", (po != null) ? po.getId() : null);
				
				Map<String ,Object> map = new HashMap<String,Object>();
				map.put("pd", pd);
				map.put("po", po);
				
				respon.success(map);
			
			}catch(Exception e){
				e.printStackTrace();
				respon.error();
			}
			return respon;
		}
		/**
		 * 跳转到支付宝
		 */
		@RequestMapping(value = "/jumping/{id}")
		public ModelAndView submit(@PathVariable String id, BigDecimal amount, HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			ModelAndView mv=new ModelAndView();
			try {
				
				PageData p = new PageData();
				p.put("id", id);
				p = paymentOrderService.findById(p);
				String sn = p.getString("paymentNo");
				String requestUrl = alipayDirectPlugin.getRequestUrl();
				Map<String, Object> parameterMap = alipayDirectPlugin.getParameterMap(sn, "", request);
				// ObjectMapper objectMapper=new ObjectMapper();
				// String str=objectMapper.writeValueAsString(parameterMap);
				model.addAttribute("requestUrl", requestUrl);
				model.addAttribute("requestMethod", alipayDirectPlugin.getRequestMethod());
				model.addAttribute("requestCharset", alipayDirectPlugin.getRequestCharset());
				model.addAttribute("parameterMap", parameterMap);
				mv.addObject("requestUrl", requestUrl);
				mv.addObject("requestMethod", alipayDirectPlugin.getRequestMethod());
				mv.addObject("requestCharset", alipayDirectPlugin.getRequestCharset());
				mv.addObject("parameterMap", parameterMap);
				// logger.info(requestUrl);
				// logger.info(str);
				if (StringUtils.isNotEmpty(alipayDirectPlugin.getRequestCharset())) {
					response.setContentType("text/html; charset=" + alipayDirectPlugin.getRequestCharset());
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mv.setViewName("user/jumping");
			return mv;
		}
		/**
		 * 支付宝异步通知
		 */
		@RequestMapping("/asyncNotify/{notifyMethod}/{sn}")
		@ResponseBody
		public String asyncNotify(@PathVariable NotifyMethod notifyMethod, @PathVariable String sn,
				HttpServletRequest request, ModelMap model) {
			String msg = "fail";
			try {
				PageData pd = new PageData();
				pd = this.getPageData();
				pd.put("paymentNo", sn);
				pd = paymentOrderService.findById(pd);
				Integer status = (Integer) pd.get("status"); // 支付状态，0表示已下单，未支付，1表示已支付
				// Order order = orderService.findOrderSn(sn);
				ObjectMapper objectMapper = new ObjectMapper();
				Map m = request.getParameterMap();
				String str = objectMapper.writeValueAsString(m);
				String extra_common_param = request.getParameter("extra_common_param").toString();
				logger.info("即时到帐异步订单号:" + sn + "=>" + str + "(extra_common_param)=" + extra_common_param);
				if (pd != null) {
					if (alipayDirectPlugin != null) {
						if (alipayDirectPlugin.verifyNotify(sn, notifyMethod, request)) { // 回调成功，编写业务处理逻辑
							if (status != 1) { // 修改订单状态
								PageData pds = new PageData();
								pds.put("paymentDate", new Date());
								pds.put("paymentAmount", request.getParameter("total_fee"));
								pds.put("transactionNo", request.getParameter("trade_no"));
								pds.put("status", "1");
								pds.put("paymentNo", sn);
								pds.put("businessType", pd.getString("businessType"));
								pds.put("businessId", pd.getString("businessId"));
								pds.put("createId", pd.getString("createId"));
								pds.put("paymentCash", pd.getString("paymentCash"));
								//paymentOrderService.handle(pds);
								paymentOrderService.edit(pds);
								Map<String,Object> map=getLoginInfo();
								if(StringUtils.equals(pd.getString("paymentCash"), "4")){
									Recharge r=rechargeService.getById(pd.getString("businessId"));
									RewardUtil.disHoney(String.valueOf(Code.honey_recharge), Double.valueOf(r.getHoney()), String.valueOf(map.get("userId")), String.valueOf(map.get("userId")), String.valueOf(map.get("companyid")));
								}else if(StringUtils.equals(pd.getString("paymentCash"), "3")){
									PageData pdd = new PageData();
									pdd.add("paymentOrderId", pd.getString("id"));
									pdd.add("status", 1);
									guaranteeMoneyService.updateStatus(pdd);
								}
								
								msg = "success";
							} else { // 如果该订单已经支付
								msg = "success";
							}
						}
					}
				} else {
					logger.info("获取订单失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e.getMessage());
			}
			logger.info("sn:" + sn + "=>返回值：" + msg);
			return msg;
		}
		
		/**
		 * 同步通知
		 */
		@RequestMapping("/notify/{notifyMethod}/{sn}")
		public String notify(@PathVariable NotifyMethod notifyMethod, @PathVariable String sn, HttpServletRequest request,
				ModelMap model) {
			try {
				String msg = "fail";
				PageData pd = new PageData();
				pd = this.getPageData();
				pd.put("paymentNo", sn);
				pd = paymentOrderService.findById(pd);
				String paymentCash = pd.getString("paymentCash");
				Integer status = (Integer) pd.get("status"); // 支付状态，0表示已下单，未支付，1表示已支付
				ObjectMapper objectMapper = new ObjectMapper();
				Map m = request.getParameterMap();
				String sync = objectMapper.writeValueAsString(m);
				String extra_common_param = request.getParameter("extra_common_param").toString();
				logger.info("即时到帐同步订单号:" + sn + "=>" + sync + "(extra_common_param)=" + extra_common_param);
				if (pd != null) {
					if (alipayDirectPlugin.verifyNotify(sn, notifyMethod, request)) { // 回调成功，编写业务处理逻辑
						msg = "success";
						if (status != 1) { // 修改订单状态
							PageData pds = new PageData();
							pds.put("paymentDate", new Date());
							pds.put("paymentAmount", request.getParameter("total_fee"));
							pds.put("transactionNo", request.getParameter("trade_no"));
							pds.put("status", "1");
							pds.put("paymentNo", sn);
							pds.put("businessType", pd.getString("businessType"));
							pds.put("businessId", pd.getString("businessId"));
							pds.put("createId", pd.getString("createId"));
							pds.put("paymentCash", pd.getString("paymentCash"));
							//paymentOrderService.handle(pds);
							paymentOrderService.edit(pds);
							Map<String,Object> map=getLoginInfo();
							if(StringUtils.equals(pd.getString("paymentCash"), "4")){
								Recharge r=rechargeService.getById(pd.getString("businessId"));
								RewardUtil.disHoney(String.valueOf(Code.honey_recharge), Double.valueOf(r.getHoney()), String.valueOf(map.get("userId")), String.valueOf(map.get("userId")), String.valueOf(map.get("companyid")));
							}else if(StringUtils.equals(pd.getString("paymentCash"), "3")){
								PageData pdd = new PageData();
								pdd.add("paymentOrderId", pd.getString("id"));
								pdd.add("status", 1);
								guaranteeMoneyService.updateStatus(pdd);
							}
						}
						model.addAttribute("notifyMessage", alipayDirectPlugin.getNotifyMessage(sn, notifyMethod, request));
					}
					// }
				} else {
					throw new Exception("获取订单失败");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";

		}
		
		/**
		 * 跳转到微信支付
		 */
		@RequestMapping(value = "/weixinJumping/{id}")
		@ResponseBody
		public Respon weixinJumping(@PathVariable String id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
			String code_url = "";
			Respon respon  = this.getRespon();
			try {
				PageData pageData = new PageData();
				pageData.put("id", id);
				pageData = paymentOrderService.findById(pageData);
				//code_url = JsSdkUtil.getQrCodeUrl(pageData, request, response);
				model.addAttribute("code_url", code_url);
				model.addAttribute("pageData", pageData);
				respon.success(model);
			} catch (Exception e) {
				e.printStackTrace();
				respon.error(e.getMessage());
			}
			
	        return respon;
			//return "ht/order/weixinCode";
		}
		/**
		 * 微信支付查询订单是否支付成功，如果支付成功，进行跳转
		 * 
		 * @throws Exception
		 */
		@RequestMapping(value = "/queryOrderWeiXin/{id}")
		@ResponseBody
		public String queryOrderWeiXin(@PathVariable String id, HttpServletRequest request, HttpServletResponse response,
				ModelMap model) throws Exception {
			// logger.info("id="+id);
			// System.out.println("id="+id);
			PageData pageData = new PageData();
			pageData.put("id", id);
			pageData = paymentOrderService.findById(pageData);
			// System.out.println("pageData="+pageData.toString());
			Integer status = (Integer) pageData.get("status");
			if (status == 1) {
				return "success";
			} else {
				return "fail";
			}

		}
		/**
		 * 生成二维码
		 */
		@RequestMapping(value = "/getQRCode")
		@ResponseBody
		public void getQRCode(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
			PageData pd = new PageData();
			pd = this.getPageData();
			String code_url = pd.getString("code_url");
			GenerateQrCodeUtil.encodeQrcode(code_url, response);

			// logger.info("<-------------生成二维码成功------------->");
		}
		/**
		 * 微信扫描支付回调方法
		 * 
		 * @throws Exception
		 */
		@RequestMapping(value = "/weixinNotifyUrl")
		@ResponseBody
		public void weixinNotifyUrl(HttpServletRequest request, HttpServletResponse response, ModelMap model)
				throws Exception {
			try {
				 logger.info("<-------------支付成功微信返回结果开始------------->");
				 logger.info("<-------------数据==------------->");
				PrintWriter out = response.getWriter();
				InputStream inStream = request.getInputStream();
				ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = inStream.read(buffer)) != -1) {
					outSteam.write(buffer, 0, len);
				}
				outSteam.close();
				inStream.close();
				String result = new String(outSteam.toByteArray(), "utf-8");
				Map<String, String> map = null;
				 logger.info("异步返回result="+result);
				map = XMLUtil.doXMLParse(result);
				 logger.info("异步返回map="+map.toString());

				// 此处调用订单查询接口验证是否交易成功
				String out_trade_no = map.get("out_trade_no");
				 logger.info("out_trade_no=====>>>"+out_trade_no);
				PageData pd = new PageData();
				pd.put("paymentNo", out_trade_no);
				pd = paymentOrderService.findById(pd);
				 logger.info("pd=====>>>"+pd.toString());
				boolean isSucc = JsSdkUtil.reqOrderquery(pd, map);
				 logger.info(isSucc);
				// 支付成功，商户处理后同步返回给微信参数
				if (!isSucc) {
					// 支付失败
					 logger.info("isSucc===支付失败"+!isSucc);
					System.out.println("支付失败");
				} else {
					 logger.info("===============付款成功==============");
					// System.out.println("===============付款成功==============");
					// 付款成功进行业务逻辑处理
					Integer status = (Integer) pd.get("status"); // 支付状态，0表示已下单，未支付，1表示已支付
					 logger.info("status====>"+status);
					// 修改订单状态
					if (status != 1) {
						PageData pds = new PageData();
						String totalFee = map.get("total_fee");
						String transaction_id = map.get("transaction_id");
						pds.put("paymentDate", new Date());
						pds.put("paymentAmount", totalFee);
						pds.put("transactionNo", transaction_id);
						pds.put("paymentType", "WeChat");
						pds.put("status", "1");
						pds.put("paymentNo", out_trade_no);
						pds.put("businessType", pd.getString("businessType"));
						pds.put("businessId", pd.getString("businessId"));
						pds.put("createId", pd.getString("createId"));
						pds.put("paymentCash", pd.getString("paymentCash"));
						//paymentOrderService.handle(pds);
						paymentOrderService.edit(pds);
						if(StringUtils.equals(pd.getString("paymentCash"), "4")){
							Recharge r=rechargeService.getById(pd.getString("businessId"));
							RewardUtil.disHoney(String.valueOf(Code.honey_recharge), Double.valueOf(r.getHoney()), pd.getString("createId"), pd.getString("createId"), pd.getString("createId"));
						}else if(StringUtils.equals(pd.getString("paymentCash"), "3")){
							PageData pdd = new PageData();
							pdd.add("paymentOrderId", pd.getString("id"));
							pdd.add("status", 1);
							guaranteeMoneyService.updateStatus(pdd);
						}
					}
					// logger.info("status====>"+status);
					// 业务逻辑处理完，返回success给微信后台
					String noticeStr = JsSdkUtil.setXML("SUCCESS", "OK");
					out.print(new ByteArrayInputStream(noticeStr.getBytes(Charset.forName("UTF-8"))));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*
		 * 线下支付
		 */
		@RequestMapping(value="linePayment")
		@ResponseBody
		public Respon linePayment(String paymentOrderId,String phone)throws Exception{
			Respon respon  = this.getRespon();
			try{
				PaymentOrder p=new PaymentOrder();
				p.setPhone(phone);
				p.setId(paymentOrderId);
				paymentOrderService.updateById(p);
//				paymentOrderService.editPhone(new PageData()
//						           .add("id", paymentOrderId)
//						           .add("phone", phone));
				respon.success(null);
			}catch(Exception e){
				e.printStackTrace();
				respon.error();
			}
			
			return respon;
		}
//		/*
//		 * 测试支付
//		 */
//		@RequestMapping(value="testpayment")
//		public ModelAndView testpayment(String paymentOrderId,String orderId)throws Exception{
//			try{
//				paymentOrderService.edit(new PageData()
//					   .add("id", paymentOrderId)
//					   .add("paymentDate", new Date())
//					   .add("status", "1"));
//				orderService.edit(new PageData()
//						.add("id", orderId)
//						.add("status", 1));
//				return this.viewName("/mycenter/order/payment_success");
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			return null;
//		}
}
