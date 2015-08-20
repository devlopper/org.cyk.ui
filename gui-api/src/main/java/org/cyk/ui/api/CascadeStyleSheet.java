package org.cyk.ui.api;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CascadeStyleSheet implements Serializable {

	private static final long serialVersionUID = 738142431416512052L;

	private String clazz="",inline="";
	
	public CascadeStyleSheet addClasses(String...classes){
		clazz=clazz+StringUtils.join(classes,Constant.CHARACTER_SPACE);
		return this;
	}
	
	public CascadeStyleSheet addClass(String aClass){
		return addClasses(aClass);
	}
	
	public CascadeStyleSheet addInlines(String...inlines){
		inline=inline+StringUtils.join(inlines);
		return this;
	}
	
	public CascadeStyleSheet addInline(String inline){
		return addInlines(inline);
	}
	
}
