package org.cyk.ui.api;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CascadeStyleSheet implements Serializable {

	private static final long serialVersionUID = 738142431416512052L;

	private String clazz="",inline="";
	
	public CascadeStyleSheet addClass(String aClazz){
		clazz=clazz+" "+aClazz;
		return this;
	}
	
	public CascadeStyleSheet addInline(String aInline){
		inline=inline+" "+aInline;
		return this;
	}
	
}
