package org.cyk.ui.web.api;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public class AjaxBuilder extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7938368076994108266L;

	private FormOneData<?, ?, ?, ?, ?, ?> form;
	private String fieldName;
	private String event;
	private String[] crossedFieldNames;
	private String[] updatedFieldNames;
	private Class<?> valueClass;
	private ListenValueMethod<?> method;
	
	public <TYPE> AjaxBuilder method(Class<TYPE> valueClass,ListenValueMethod<TYPE> method){
		this.valueClass = valueClass;
		this.method = method;
		return this;
	}
	
	public AjaxBuilder form(FormOneData<?, ?, ?, ?, ?, ?> form){
		this.form = form;
		return this;
	}

	public AjaxBuilder fieldName(String fieldName){
		this.fieldName = fieldName;
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
	
	public AjaxListener build(){
		/*
		WebInput<?, ?, ?, ?> webInput = form.findInputByClassByFieldName(WebInput.class, fieldName);
		
		final Set<String> processes = new HashSet<>();
		processes.add(classSelector(webInput));
		for(String crossFieldName : crossedFieldNames)
			processes.add(classSelector(form.findInputByClassByFieldName(WebInput.class, crossFieldName)));
		
		AjaxListener ajaxAdapter = new AjaxAdapter(event) {
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
		};
		
		Set<String> updated = new HashSet<>();
		updated.add(classSelector(webInput));
		for(String updatedFieldName : updatedFieldNames)
			updated.add(classSelector(form.findInputByClassByFieldName(WebInput.class, updatedFieldName)));
		ajaxAdapter.setUpdate(StringUtils.join(updated,","));
		
		form.findInputByClassByFieldName(WebInput.class, fieldName).setAjaxListener(ajaxAdapter);
		return ajaxAdapter;
		*/
		return null;
	}
	
	/**/
	
	
	
}
