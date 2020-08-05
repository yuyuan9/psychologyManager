package com.ht.commons.support.sms;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ht.commons.support.sms.SMSHelp;
import com.ht.commons.support.sms.sdk.CCPRestSDK;

public class SMSHelpImpl implements SMSHelp{
	
	private static Logger logger = LoggerFactory.getLogger(SMSHelpImpl.class);

	@Override
	public boolean sendTest(String phone, String[] content, String templateId) {
		Map<String,Object> ret = sendNode(phone,content,templateId,true);
		return (ret.get(SMSConfig.SUCCESS)!=null && ObjectUtils.equals(ret.get(SMSConfig.SUCCESS), true));
	}

	public boolean send(String phone, String[] content, String templateId) {
		Map<String,Object> ret = sendNode(phone,content,templateId,false);
		return (ret.get(SMSConfig.SUCCESS)!=null && ObjectUtils.equals(ret.get(SMSConfig.SUCCESS), true));
	}

	@Override
	public String sendMsgTest(String phone, String[] content, String templateId) {
		Map<String,Object> ret = sendNode(phone,content,templateId,true);
		return (String)ret.get(SMSConfig.STATUS_MSG_KEY);
	}

	@Override
	public String sendMsg(String phone, String[] content, String templateId) {
		Map<String,Object> ret = sendNode(phone,content,templateId,false);
		return (String)ret.get(SMSConfig.STATUS_MSG_KEY);
	}
	
	
	/**
	 * 发送短信
	 * @param phone 手机号
	 * @param content 内容
	 * @param templateId 模板id
	 * @param test 是否是测试
	 * @return
	 */
	private Map<String,Object> sendNode(String phone,String[] content,String templateId,boolean test) {
		HashMap<String,Object> retMap=null; 
		Map<String,Object> ret=new HashMap<String,Object>();
		CCPRestSDK restAPI = null;
		try {
			restAPI = new CCPRestSDK();
			if(test){
				restAPI.init(SMSConfig.TEST_HOST, SMSConfig.TEST_PORT);
				restAPI.setAccount(SMSConfig.TEST_ACCOUNT, SMSConfig.TEST_ACCOUNT_TOKEN);// 初始化主帐号名称和主帐号令牌
				restAPI.setAppId(SMSConfig.TEST_APPID);// 初始化应用ID
			}else {
				restAPI.init(SMSConfig.HOST, SMSConfig.PORT);
				restAPI.setAccount(SMSConfig.ACCOUNT, SMSConfig.ACCOUNT_TOKEN);// 初始化主帐号名称和主帐号令牌
				restAPI.setAppId(SMSConfig.APPID);// 初始化应用ID
			}
			retMap = restAPI.sendTemplateSMS(phone, templateId, content);
			
			if(SMSConfig.STATUS_CODE.equals(retMap.get(SMSConfig.STATUS_CODE_KEY))) {
				// 正常返回输出data包体信息（map）
				HashMap<String, Object> data = (HashMap<String, Object>) retMap.get("data");
				Set<String> keySet = data.keySet();
				for (String key : keySet) {
					ret.put(key, data.get(key));
				}
				ret.put(SMSConfig.SUCCESS, true);
			}else {
				ret.put(SMSConfig.STATUS_CODE_KEY, retMap.get(SMSConfig.STATUS_CODE_KEY));
				ret.put(SMSConfig.STATUS_MSG_KEY, retMap.get(SMSConfig.STATUS_MSG_KEY));
				ret.put(SMSConfig.ERROR, true);
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("发送短信失败", "错误码=" + ret.get(SMSConfig.STATUS_CODE_KEY) + " 错误信息= " + ret.get(SMSConfig.STATUS_MSG_KEY));
		}
		
	    return ret;
	}
	 
	

}
