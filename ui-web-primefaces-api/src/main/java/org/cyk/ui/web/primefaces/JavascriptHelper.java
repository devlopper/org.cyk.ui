package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

public class JavascriptHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String PRIMEFACES = "PF";
	public static final String GET_COMPONENT_BY_WIDGET_VAR_FORMAT = PRIMEFACES+"('%s')";
	public static final String CALL_COMPONENT_METHOD_FORMAT = GET_COMPONENT_BY_WIDGET_VAR_FORMAT+".%s(%s)";
	
	private static String getWidgetVar(Object component){
		return ((org.cyk.utility.common.helper.MarkupLanguageHelper.Attributes)((Object)((AbstractBean)component).getPropertiesMap())).getWidgetVar();
	}
	
	public static String getComponent(Object component){
		return String.format(GET_COMPONENT_BY_WIDGET_VAR_FORMAT,getWidgetVar(component));
	}
	
	public static String getMethodCall(Object component,String methodName,String parameters){
		return String.format(CALL_COMPONENT_METHOD_FORMAT,getWidgetVar(component),methodName,parameters);
	}
	
	public static String getMethodCall(Object component,String methodName){
		return getMethodCall(component, methodName, Constant.EMPTY_STRING);
	}
	
	public static String getFilterDatatable(Object datatable){
		return getMethodCall(datatable, FILTER);
	}
	
	/**/
	
	public static final String FILTER = "filter";
	
}
