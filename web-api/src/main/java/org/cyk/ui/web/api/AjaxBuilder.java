package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public class AjaxBuilder extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7938368076994108266L;

	private FormOneData<?, ?, ?, ?, ?, ?> form;
	private String fieldName,event,classSelectorSymbol="$",classSelectorFormat="%s(.%s)",processed;
	private String[] crossedFieldNames;
	private String[] updatedFieldNames;
	private Class<?> valueClass;
	private ListenValueMethod<Object> method;
	private WebInput<?, ?, ?, ?> input;
	
	@SuppressWarnings("unchecked")
	public <TYPE> AjaxBuilder method(Class<TYPE> valueClass,ListenValueMethod<TYPE> method){
		this.valueClass = valueClass;
		this.method = (ListenValueMethod<Object>) method;
		return this;
	}
	
	public AjaxBuilder classSelectorSymbol(String classSelectorSymbol){
		this.classSelectorSymbol = classSelectorSymbol;
		return this;
	}
	
	public AjaxBuilder classSelectorFormat(String classSelectorFormat){
		this.classSelectorFormat = classSelectorFormat;
		return this;
	}
	
	public AjaxBuilder form(FormOneData<?, ?, ?, ?, ?, ?> form){
		this.form = form;
		return this;
	}

	public AjaxBuilder fieldName(String fieldName){
		this.fieldName = fieldName;
		input = form.findInputByClassByFieldName(WebInput.class, fieldName);
		return this;
	}

	public AjaxBuilder event(String event){
		this.event = event;
		return this;
	}
	
	public AjaxBuilder crossedFieldNames(String...names){
		this.crossedFieldNames = names;
		return this;
	}
	
	public AjaxBuilder updatedFieldNames(String...names){
		this.updatedFieldNames = names;
		return this;
	}
	
	public <TYPE> AjaxListener build(){
		final Set<String> processes = new HashSet<>();
		processes.add(getClassSelector(input));
		if(crossedFieldNames!=null)
			for(String crossFieldName : crossedFieldNames)
				processes.add(getClassSelector(form.findInputByClassByFieldName(WebInput.class, crossFieldName)));
		
		input.setAjaxListener(new AjaxListener.Adapter.Default(event) {
			private static final long serialVersionUID = 4750417275636910265L;
			@Override
			public void listen() {
				@SuppressWarnings("unchecked")
				TYPE value = (TYPE) form.findInputByClassByFieldName(Input.class, fieldName).getValue();
				method.execute(value);
			}

			@Override
			public String getProcess() {
				return StringUtils.join(processes,",");
			}
		});
		
		Set<String> updated = new HashSet<>();
		if(updatedFieldNames!=null){
			for(String updatedFieldName : updatedFieldNames)
				updated.add(getClassSelector(form.findInputByClassByFieldName(WebInput.class, updatedFieldName)));
			input.getAjaxListener().setUpdate(StringUtils.join(updated,","));
		}
		
		return null;
	}
	
	/**/
	
	private String getClassSelector(WebInput<?, ?, ?, ?> input){
		return String.format(classSelectorFormat, classSelectorSymbol,input.getUniqueCssClass());
	}
	
	/**/
	
}
