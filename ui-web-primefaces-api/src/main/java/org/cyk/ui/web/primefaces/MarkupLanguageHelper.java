package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

public class MarkupLanguageHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String BUTTON = "button";
	
	public static void setOnClick(Object object,String script,Boolean isButtonType){
		if(Boolean.TRUE.equals(isButtonType)){
			((org.cyk.utility.common.helper.MarkupLanguageHelper.Attributes)((Object)((AbstractBean)object).getPropertiesMap())).setType(BUTTON);
			((org.cyk.utility.common.helper.MarkupLanguageHelper.Attributes)((Object)((AbstractBean)object).getPropertiesMap())).setAjax(Boolean.FALSE.toString());
		}
		((org.cyk.utility.common.helper.MarkupLanguageHelper.Attributes)((Object)((AbstractBean)object).getPropertiesMap())).setOnClick(script);
	}
	
	public static void setOnClick(Object object,String script){
		setOnClick(object, script, Boolean.TRUE);
	}
	
	public static void setAjax(Object object,Object value){
		((org.cyk.utility.common.helper.MarkupLanguageHelper.Attributes)((Object)((AbstractBean)object).getPropertiesMap())).setAjax(value == null ? null : value.toString());
	}
	
	public static void setGlobal(Object object,Object value){
		((org.cyk.utility.common.helper.MarkupLanguageHelper.Attributes)((Object)((AbstractBean)object).getPropertiesMap())).setGlobal(value == null ? null : value.toString());
	}
}
