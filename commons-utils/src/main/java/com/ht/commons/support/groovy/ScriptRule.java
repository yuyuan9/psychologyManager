package com.ht.commons.support.groovy;


import java.util.Map;

/**
 * 脚本规则
 * @author Administrator
 *
 */
public interface ScriptRule {
	public Object evalMehtodApplyCondition(String script,Object root);
	
	public Object evalDefaultScript(String script,Object root,Map<String,Object> bind);
}
