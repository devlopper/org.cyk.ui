package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Named
public class JavaScriptHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4662624027559597185L;
	
	private static final String OPEN_WINDOW_FORMAT = "window.open('%s', '%s', 'location=no,menubar=no,titlebar=no,toolbar=no,width=%s, height=%s');";
	
	private static final String INSTRUCTION_SEPARATOR = ";";
	private static JavaScriptHelper INSTANCE;
	public static JavaScriptHelper getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		INSTANCE = this;
	}
	
	public String add(String source,String instruction){
		return formatInstruction(source)+formatInstruction(instruction);
	}
	
	public String formatInstruction(String instruction){
		StringBuilder builder= new StringBuilder(instruction);
		if(!StringUtils.endsWith(builder, INSTRUCTION_SEPARATOR))
			builder.append(INSTRUCTION_SEPARATOR);
		
		return builder.toString();
	}
	
	public String windowHref(String url){
		return "window.location='"+url+"';";
	}
	
	public String hide(String path){
		return "$('"+path+"').hide();";
	}
	
	public String hide(String[] styleClasses) {
		String script = "";
		for(String styleClasse : styleClasses)
		if(StringUtils.isNotBlank(styleClasse))
			script = add(script,hide("."+styleClasse));
		return script;
	}
	
	public String openWindow(String id,String url,Integer width,Integer height){
		return String.format(OPEN_WINDOW_FORMAT, url,id,width,height);
	}
	
	public String update(WebInput<?, ?, ?, ?> input,Object value){
		if( Boolean.TRUE.equals(((Input<?, ?, ?, ?, ?, ?>)input).getReadOnly()) )
			return "$('."+input.getUniqueCssClass()+"').html('"+value+"');";
		else
			return "$('."+input.getUniqueCssClass()+"').val('"+value+"');";
	}
	
	

}
