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
	private String fieldName,event,classSelectorSymbol="$",classSelectorFormat="%s(.%s)";
	private Set<String> requiredFieldNamesForBuilding = new HashSet<>();
	private Set<String> crossedFieldNames = new HashSet<>();
	private Set<String> updatedFieldNames = new HashSet<>();
	private Set<String> processed = new HashSet<>();
	private Set<String> updated = new HashSet<>();
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
		for(String v : names)
			if(v!=null)
				crossedFieldNames.add(v);
		return this;
	}
	
	public AjaxBuilder requiredFieldNamesForBuilding(String...names){
		for(String v : names)
			if(v!=null)
				requiredFieldNamesForBuilding.add(v);
		return this;
	}
	
	public AjaxBuilder updatedFieldNames(String...names){
		for(String v : names)
			if(v!=null)
				updatedFieldNames.add(v);
		return this;
	}
	
	public <TYPE> void build(){
		Boolean buildable = null;
		if(requiredFieldNamesForBuilding==null)
			buildable = Boolean.TRUE;
		else{
			buildable = Boolean.FALSE;
			for(String fieldName : requiredFieldNamesForBuilding){
				WebInput<?, ?, ?, ?> input = form.findInputByClassByFieldName(WebInput.class, fieldName);
				buildable = buildable || input != null;	
			}
		}
		if(input==null /*|| !Boolean.TRUE.equals(buildable)*/)
			return;
		input.setAjaxListener(new AjaxListener.Adapter.Default(event) {
			private static final long serialVersionUID = 4750417275636910265L;
			@Override
			public void listen() {
				@SuppressWarnings("unchecked")
				TYPE value = (TYPE) form.findInputByClassByFieldName(Input.class, fieldName).getValue();
				method.execute(value);
			}
		});
		
		processed.add(getClassSelector(input));
		if(crossedFieldNames!=null)
			for(String crossFieldName : crossedFieldNames){
				WebInput<?, ?, ?, ?> input = form.findInputByClassByFieldName(WebInput.class, crossFieldName);
				if(input!=null)
					processed.add(getClassSelector(form.findInputByClassByFieldName(WebInput.class, crossFieldName)));
			}
		input.getAjaxListener().setProcess(StringUtils.join(processed,","));
		
		if(updatedFieldNames!=null){
			for(String updatedFieldName : updatedFieldNames){
				WebInput<?, ?, ?, ?> input = form.findInputByClassByFieldName(WebInput.class, updatedFieldName);
				if(input!=null)
					updated.add(getClassSelector(input));
			}
		}
		input.getAjaxListener().setUpdate(StringUtils.join(updated,","));
	}
	
	/**/
	
	private String getClassSelector(WebInput<?, ?, ?, ?> input){
		return String.format(classSelectorFormat, classSelectorSymbol,input.getUniqueCssClass());
	}
	
	/**/
	
}
