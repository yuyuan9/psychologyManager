package com.ht.commons.support.groovy;


import java.util.Map;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;



/**
 * 脚本规则实现类
 * @author Administrator
 */
public class ScriptRuleImpl implements ScriptRule{
	
	private static String APPLY_CONDITION_METHOD="applyCondition";
	
	private static String SCRIPT_METHOD="scriptMethod";
	
	
	
	private ScriptEngineManager factory=new ScriptEngineManager();
	
	private ScriptEngine engine;

	public ScriptEngineManager getFactory() {
		return factory;
	}

	public void setFactory(ScriptEngineManager factory) {
		this.factory = factory;
	}
	
	
	public Object evalMehtodApplyCondition(String script,Object root){
		if(StringUtils.isBlank(script)){
			return false;
		}
		return evalScript(APPLY_CONDITION_METHOD,script,root);
	}
	
	@Override
	public Object evalDefaultScript(String script, Object root,Map<String,Object> bind) {
		if(StringUtils.isBlank(script)){
			return null;
		}
		StringBuffer method = new StringBuffer();
		method.append("def scriptMethod(root){");
		method.append(script);
		method.append("}");
		
	
		
		return evalScript(SCRIPT_METHOD,method.toString(),root,bind);
	}
	
	//执行方法
	private Object evalScript(String method,String script,Object root,Map<String,Object> bind){
			initEngine();
			System.out.println(script);
			try {
				Bindings bindings=engine.createBindings();
				bindings.putAll(bind);
				engine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
				engine.eval(script);
				return ((Invocable)engine).invokeFunction(method, root);
			} catch (ScriptException e) {
				System.out.println("脚本错误");
				e.printStackTrace();
			}catch (NoSuchMethodException  e) {
				System.out.println("脚本错误方法错误");
				e.printStackTrace();
			}
			return null;
	}
	
	//执行方法
	private Object evalScript(String method,String script,Object root){
		initEngine();
		System.out.println(script);
		try {
			engine.eval(script);
			return ((Invocable)engine).invokeFunction(method, root);
		} catch (ScriptException e) {
			System.out.println("脚本错误");
			e.printStackTrace();
		}catch (NoSuchMethodException  e) {
			System.out.println("脚本错误方法错误");
			e.printStackTrace();
		}
		return null;
	}
	
	private synchronized void initEngine(){
		if(this.engine==null){
			this.engine=factory.getEngineByName("groovy");
		}
	}

	

	
	
}
