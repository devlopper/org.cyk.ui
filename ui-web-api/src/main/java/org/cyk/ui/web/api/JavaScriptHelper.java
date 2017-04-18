package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Named
/**
 * Helper class to get various javascript source code to be executed in page
 * @author Christian Yao Komenan
 *
 */
public class JavaScriptHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4662624027559597185L;
	
	private static final String OPEN_WINDOW_FORMAT = "window.open('%s', '%s', 'location=no,menubar=no,titlebar=no,toolbar=no,width=%s, height=%s');";
	private static final String WINDOW_LOCATION = "window.location = '%s';";
	
	private static final String INSTRUCTION_SEPARATOR = ";";
	private static final String DOUBLE_SPACE = Constant.CHARACTER_SPACE.toString()+Constant.CHARACTER_SPACE.toString();
	
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
		instruction=StringUtils.replace(instruction, DOUBLE_SPACE, Constant.CHARACTER_SPACE.toString());
		StringBuilder builder= new StringBuilder(instruction==null?Constant.EMPTY_STRING:instruction);
		if(StringUtils.isNotBlank(instruction) && !StringUtils.endsWith(builder, INSTRUCTION_SEPARATOR))
			builder.append(INSTRUCTION_SEPARATOR);
		return builder.toString();
	}
	
	public String windowHref(String url){
		return "window.location='"+url+"';";
	}
	
	public String windowBack(){
		return "window.history.back();";
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
		if( Boolean.TRUE.equals(((Input<?, ?, ?, ?, ?, ?>)input).getReadOnly()) && !Boolean.TRUE.equals(((Input<?, ?, ?, ?, ?, ?>)input).getKeepShowingInputOnReadOnly())){
			return "$('."+input.getCss().getUniqueClass()+"').html('"+value+"');";
		}else{
			return "$('."+input.getCss().getUniqueClass()+"').val('"+value+"');";
		}
	}
	
	public String getFormControlVisibility(Control<?, ?, ?, ?, ?> control,Boolean visible){
		if(control==null)
			return Constant.EMPTY_STRING;
		//We suppose that form is 2 columns based
		//TODO should work with more than 2 columns
		return "$('."+control.getCss().getUniqueClass()+"').closest('table').closest('tr')."+(Boolean.TRUE.equals(visible)?"show":"hide")+"();";
	}
	
	public String getFormInputVisibility(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName,Boolean visible){
		return getFormControlVisibility(form.getInputByFieldName(fieldName), visible);
	}
	
	public String getWindowLocation(String url){
		return String.format(WINDOW_LOCATION, url);
	}

}
