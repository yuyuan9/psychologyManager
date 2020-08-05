package com.ht.commons.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

public class SimpleHashUtil {
	
	public static String convertEncryptionPwd(String username,String pwd){
		return new SimpleHash("SHA-1",username,pwd).toString();
	}

}
