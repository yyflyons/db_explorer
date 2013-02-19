package com.ifunshow.antelopeframe.web.base;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;

/**
 * 基础service 所有业务Service都继承此类
 * @author yyflyons-于亚丰
 */

public class BaseController {
	
	public ServletContext getServletContext(){
		return ContextLoader.getCurrentWebApplicationContext().getServletContext();
	}
	
	public String getContextPath(){
		return this.getServletContext().getContextPath();
	}
	
	public String getRealPath(String path){
		return this.getServletContext().getRealPath(path);
	}
	
	public Object getAttribute(String name){
		return this.getServletContext().getAttribute(name);
	}
	
}
