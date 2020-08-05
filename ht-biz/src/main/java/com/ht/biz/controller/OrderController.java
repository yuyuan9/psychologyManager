package com.ht.biz.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
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
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.freeassess.Testing;
import com.ht.entity.biz.honeymanager.PaymentOrder;
import com.ht.entity.biz.honeymanager.Recharge;
import com.ht.entity.biz.honeymanager.RewardRule.Code;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.RewardUtil;
import com.ht.validator.TestingValidator;


/**
 * 订单controller
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());

	
	@Autowired
	RechargeService rechargeService;

	@Autowired
	private PaymentOrderService paymentOrderService;

	@Autowired
	AlipayDirectPlugin alipayDirectPlugin;
	
	@Autowired
	private GuaranteeMoneyService guaranteeMoneyService;
	
	/**
	 * 去支付 paymentCash(1:预付款|2：尾款|3：保证金)
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/payment")
	public ModelAndView payment() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		int paymentCash = Integer.parseInt(pd.getString("paymentCash"));
		if (paymentCash != 3) {
			//pd = orderService.getPaymentOrderInfo(pd);
		}
		double payPrice = 0d;
		if (pd.containsKey("contractPrice")) {
		}
		if (paymentCash == 1) {
		} else if (paymentCash == 2) {
		} else if (paymentCash == 3) {
		}
		PaymentOrder po = null;
		if (paymentCash == 3) {
			po =PaymentOrderUtil.get("t_sys_service", pd.getString("businessId"), payPrice);
			po.setPaymentNo(Const.getOrderNo());
			po.setPaymenTtitle("保证金" + payPrice + "元");
			po.setPaymentType(paymentCash + "");
			po.setPaymentCash("alipay");
			po.setCreatedate(new Date());
			po.setCreateid(LoginUserUtils.getUserId());
			po.setRegionid(LoginUserUtils.getLoginUser().getCompanyid());
			paymentOrderService.save(po);
		} else if (paymentCash == 2) {
		} else if (paymentCash == 1) {
		}

		pd.put("payPrice", payPrice);
		pd.put("paymentCash", paymentCash);
		// 支付订单记录表记录id
		pd.put("paymentOrderId", (po != null) ? po.getId() : null);

		mv.addObject("pd", pd);
		mv.addObject("po", po);
		mv.setViewName("ht/order/payment");
		return mv;
	}

	

	/**
	 * 跳转到支付宝
	 */
	@RequestMapping(value = "/jumping/{id}")
	public String submit(@PathVariable String id, BigDecimal amount, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
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
			// logger.info(requestUrl);
			// logger.info(str);
			if (StringUtils.isNotEmpty(alipayDirectPlugin.getRequestCharset())) {
				response.setContentType("text/html; charset=" + alipayDirectPlugin.getRequestCharset());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "ht/order/jumping";
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
								pdd.add("paymentOrderId", pd.getString("businessId"));
								pdd.add("status", 1);
								guaranteeMoneyService.updateStatus(pdd);
							}
							System.out.println("pds->"+pds);
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
	public ModelAndView notify(@PathVariable NotifyMethod notifyMethod, @PathVariable String sn, HttpServletRequest request,
			ModelMap model) {
		ModelAndView mv=new ModelAndView();
		String msg = "fail";
		PageData pd = new PageData();
		try {
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
				// if(StringUtils.equalsIgnoreCase(AlipayDirectPlugin.class.getSimpleName(),request.getParameter("extra_common_param").toString())){//支付宝即时到账
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
		if(StringUtils.equals(pd.getString("paymentCash"), "4")){
			mv.setViewName("redirect:/ht-biz/usercenter/user/index?urls=honeys");
		}else if(StringUtils.equals(pd.getString("paymentCash"), "3")){
			mv.setViewName("redirect:/ht-biz/service/index/safeguard_manage");
		}
		
		return mv;

	}
	
	/*
	 * 微信h5支付
	 */
	
	@RequestMapping(value="/wxwappay/{id}",method={RequestMethod.GET,RequestMethod.POST})
	public Respon wxwappay(@PathVariable String id,HttpServletRequest request,HttpServletResponse response)throws Exception{
		Respon respon = this.getRespon();
		try{
			PageData pd = new PageData().add("id", id);
			pd = paymentOrderService.findById(pd);
			String url=JsSdkUtil.getWapPayUrl(pd,request,response);
			respon.success(url);
		}catch(Exception e){
			e.printStackTrace();
			respon.error(e.getMessage());
		}
		
		return respon;
		
	}

	/**
	 * 跳转到微信支付
	 */
	@RequestMapping(value = "/weixinJumping/{id}")
	@ResponseBody
	public Respon weixinJumping(@PathVariable String id, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		String code_url = "";
		Respon respon = this.getRespon();
		try {
			PageData pageData = new PageData();
			pageData.put("id", id);
			pageData = paymentOrderService.findById(pageData);
			code_url = JsSdkUtil.getQrCodeUrl(pageData, request, response);
			model.addAttribute("pageData", pageData);
			model.addAttribute("code_url", code_url);
			
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
	@RequestMapping(value = "/queryOrderWeiXin/{id}",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String queryOrderWeiXin(@PathVariable String id, HttpServletRequest request, HttpServletResponse response,ModelMap model){
		 try {
			 System.out.println("开始走/order/queryOrderWeiXin接口");
			logger.info("id="+id);
			 System.out.println("id="+id);
			PageData pageData = new PageData();
			pageData.put("id", id);
			pageData = paymentOrderService.findById(pageData);
			//修改订单的状态病发包
			 System.out.println("pageData="+pageData.toString());
			System.out.println("一走queryOrderWeiXin接口");
			System.out.println("status="+pageData.get("status"));
			Integer status = (Integer) pageData.get("status");
			if (status == 1) {
				System.out.println("status=1");
				return "success";
			} else {
				System.out.println("status=0");
				return "fail";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return "fail";

	}
	
	@RequestMapping(value = "/getWeixinSuccess")
	@ResponseBody
	public Respon getWeixinSuccess(String id)throws Exception{
		Respon respon = this.getRespon();
		try{
			System.out.println("开始走/order/getWeixinSuccess接口");
			PageData pageData = new PageData();
			pageData.put("id", id);
			pageData = paymentOrderService.findById(pageData);
			System.out.println("pageData="+pageData.toString());
			respon.success(pageData);
		}catch(Exception e){
			respon.error(e.getMessage());
			e.printStackTrace();
		}
		return respon;
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
			 System.out.println("<-------------支付成功微信返回结果开始------------->");
			 System.out.println("<-------------数据==------------->");
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
			 System.out.println("异步返回result="+result);
			map = XMLUtil.doXMLParse(result);
			 logger.info("异步返回map="+map.toString());
			 System.out.println("异步返回map="+map.toString());
			// 此处调用订单查询接口验证是否交易成功
			String out_trade_no = map.get("out_trade_no");
			 logger.info("out_trade_no=====>>>"+out_trade_no);
			 System.out.println("out_trade_no=====>>>"+out_trade_no);
			PageData pd = new PageData();
			pd.put("paymentNo", out_trade_no);
			pd = paymentOrderService.findById(pd);
			 logger.info("pd=====>>>"+pd.toString());
			 System.out.println("pd=====>>>"+pd.toString());
			boolean isSucc = JsSdkUtil.reqOrderquery(pd, map);
			 logger.info(isSucc);
			 System.out.println(isSucc);
			// 支付成功，商户处理后同步返回给微信参数
			if (!isSucc) {
				// 支付失败
				 logger.info("isSucc===支付失败"+!isSucc);
				System.out.println("支付失败");
			} else {
				 logger.info("===============付款成功==============");
				 System.out.println("===============付款成功==============");
				// 付款成功进行业务逻辑处理
				Integer status = (Integer) pd.get("status"); // 支付状态，0表示已下单，未支付，1表示已支付
				 logger.info("status====>"+status);
				 System.out.println("status====>"+status);
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
					logger.info("weixinpay->"+pds);
					System.out.println("print_weixinpay->"+pds);
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
				//Map<String,Object> map2=getLoginInfo();
				//System.out.println("map2="+map2);
				//Recharge r=rechargeService.getById(pd.getString("businessId"));
				//RewardUtil.disHoney(String.valueOf(Code.honey_recharge), Double.valueOf(r.getHoney()), pd.getString("createId"), pd.getString("createId"), pd.getString("createId"));
				//logger.info("status====>"+status);
				// 业务逻辑处理完，返回success给微信后台
				String noticeStr = JsSdkUtil.setXML("SUCCESS", "OK");
				out.print(new ByteArrayInputStream(noticeStr.getBytes(Charset.forName("UTF-8"))));
			}
			System.out.println("完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 去评价
	 * 
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/evaluate")
//	public ModelAndView evaluate() throws Exception {
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = this.getPageData();
//		pd = orderService.findById(pd);
//		mv.addObject("pd", pd);
//		mv.setViewName("ht/order/evaluate");
//		return mv;
//	}

	/*
	 * 订单详情
	 */
//	@RequestMapping(value = "/orderInfoDetail", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
//	public @ResponseBody String orderInfoDetail() throws Exception {
//		PageData pd = orderInfoService.findById(this.getPageData());
//		return getResult(1, "success", pd);
//	}

//	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
//	public ModelAndView cancel() throws Exception {
//		ModelAndView mv = this.getModelAndView();
//		mv.setViewName("ht/order/cancel");
//		return mv;
//	}

	/**
	 * 手工验证
	 * 
	 * @param pd
	 * @return
	 */
//	@SuppressWarnings("all")
//	private boolean validateObject(PageData pd) {
//		Testing test = new Testing();
//		Iterator<String> iters = pd.keySet().iterator();
//		StringBuffer msgs = new StringBuffer();
//		boolean returnval=true;
//		while (iters.hasNext()) {
//			String key = iters.next();
//			try {
//				Field field=ReflectHelper.getFieldByFieldName(test, key);
//				Object value = null;
//				
//				if(field!=null){
//					if(Integer.class.equals(field.getType()) || int.class.equals(field.getType())){
//						value= Integer.valueOf(pd.getString(key));
//					}else if(Double.class.equals(field.getType()) || double.class.equals(field.getType())){
//						value = Double.valueOf(pd.getString(key));
//					}else if(Date.class.equals(field.getType())){
//						value = DateUtil.fomatDate(pd.getString(key));
//					}else if(Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())){
//						value = Boolean.valueOf(pd.getString(key));
//					}else if(String.class.equals(field.getType())){
//						value = pd.getString(key);
//					}
//					
//					ReflectHelper.setValueByFieldName(test, key, value);
//					
//					Object objValue = ReflectHelper.getValueByFieldName(test, key);
//					if(objValue==null){
//						msgs.append(MsgUtil.appendTips(Message.required, key));
//						returnval=false;
//					}
//				}
//			} catch (Exception en) {
//				msgs.append(MsgUtil.appendTips("填写错误", key));
//				returnval=false;
//				en.printStackTrace();
//			}
//		}
//		return returnval;
//	}

	public static void main(String[] args) {
		System.out.println(JSONUtils.toJSONString(new PageData().add("1", "fdasfdsa")));
	}
	/**
	 * 校验企业是否下过单, 而且订单并没有失败
	 */
//	@RequestMapping(value = "/checkCompany")
//	@ResponseBody
//	public String checkCompany() {
//		try {
//			PageData pd = new PageData();
//			pd = this.getPageData();
//			if(StringUtils.isEmpty(pd.getString("companyName"))){
//				return "true";
//			}else{
//				 pd=orderService.findByCompanyName(pd);
//				 if (pd == null) {
//					  return "true";
//				   } else {
//					 return "false";
//				 }	
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "false";
//	}
//	
//	@RequestMapping(value="/oneKeyOrder",method=RequestMethod.POST)
//	@ResponseBody
//	public String oneKeyOrder(Model model){
//		try {
//			if(!getLoginUser().getIsTest()){
//				return getFailureResult("非法操作！");
//			}
//			PageData pd=this.getPageData();
//			//把订单状态变成服务完成，待公示
//			pd.put("status",3);
//			orderService.adminChangeStatus(pd);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return getSuccessResult("操作成功！");
//	}
//	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
		if(binder.getTarget()!=null && Testing.class.equals(binder.getTarget().getClass())){
			 binder.setValidator(new TestingValidator());
		}
	}
	
//	@RequestMapping("/getAbleOrder")
//	@ResponseBody
//	public Respon getIOrdersByuserid(String id) throws Exception{
//		Respon respon = this.getRespon();
//		respon.setCode(0);
//		List<Order> list	=orderService.getOrdersByUserid(id);
//		respon.setData(list);
//		return respon;
//	}
}
