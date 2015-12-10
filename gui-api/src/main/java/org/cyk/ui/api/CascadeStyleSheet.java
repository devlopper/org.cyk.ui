package org.cyk.ui.api;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;

@Getter @Setter
public class CascadeStyleSheet implements Serializable {

	private static final long serialVersionUID = 738142431416512052L;

	private static final String CLAZZ_SEPARATOR = Constant.CHARACTER_SPACE.toString();
	private static final String INLINE_SEPARATOR = Constant.CHARACTER_SEMI_COLON.toString();
	
	private String clazz=Constant.EMPTY_STRING,inline=Constant.EMPTY_STRING;
	
	public CascadeStyleSheet addClasses(String...classes){
		clazz = CommonUtils.getInstance().concatenate(clazz, classes, CLAZZ_SEPARATOR);
		return this;
	}
	
	public CascadeStyleSheet addClass(String aClass){
		return addClasses(aClass);
	}
	
	public CascadeStyleSheet addInlines(String...inlines){
		inline = CommonUtils.getInstance().concatenate(inline, inlines, INLINE_SEPARATOR);
		return this;
	}
	
	public CascadeStyleSheet addInline(String inline){
		return addInlines(inline);
	}
	
	/**/
	
	
	
}
