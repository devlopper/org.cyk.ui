package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Named
public class JavaScriptHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4662624027559597185L;
	
	public String windowHref(String url){
		return "window.location='"+url+"'";
	}
	
	protected String hide(String path){
		return "$('"+path+"').hide();";
	}

}
