package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Named
public class JavaScriptHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4662624027559597185L;
	
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
	
	

}
