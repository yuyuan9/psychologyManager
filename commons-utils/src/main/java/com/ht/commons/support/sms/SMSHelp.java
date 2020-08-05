package com.ht.commons.support.sms;

public interface SMSHelp {
	
	boolean sendTest(String phone,String[] content,String templateId);
	
	boolean send(String phone,String[] content,String templateId);
	
	String sendMsgTest(String phone,String[] content,String templateId);
	
	String sendMsg(String phone,String[] content,String templateId);

}
