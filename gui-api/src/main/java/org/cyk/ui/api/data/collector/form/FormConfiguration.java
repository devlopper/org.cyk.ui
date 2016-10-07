package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.Crud;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter// @Setter 
public class FormConfiguration extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 218887156564236022L;
	
	private static final Map<Key,FormConfiguration> MAP = new HashMap<>();
	
	/* Key */
	private Key key;
	
	/* Value */
	private Set<String> fieldNames,requiredFieldNames,excludedFieldNames;
	
	private Collection<ControlSetListener<?, ?, ?, ?, ?, ?>> controlSetListeners;
	
	private Boolean hasName(Collection<String> collection,String name){ 
		return hasNoNames(collection) || collection.contains(name);
	}
	private Boolean hasNoNames(Collection<String> collection){ 
		return collection==null || collection.isEmpty();
	}
	
	public Boolean hasNoFieldNames(){ 
		return hasNoNames(fieldNames);
	}
	public Boolean hasFieldName(String fieldName){ 
		return hasName(fieldNames, fieldName);
	}
	
	public Boolean hasNoRequiredFieldNames(){ 
		return hasNoNames(requiredFieldNames);
	}
	public Boolean hasRequiredFieldName(String fieldName){
		return requiredFieldNames!=null && hasName(requiredFieldNames, fieldName);
	}
	
	public Boolean hasNoExcludedFieldNames(){ 
		return hasNoNames(excludedFieldNames);
	}
	public Boolean hasExcludedFieldName(String fieldName){ 
		return excludedFieldNames!=null && excludedFieldNames.contains(fieldName);
	}
	
	public FormConfiguration addFieldNames(String...names){
		if(fieldNames==null)
			fieldNames = new LinkedHashSet<>();
		if(names!=null){
			addFieldNames(Arrays.asList(names));
		}
		return this;
	}
	public FormConfiguration addFieldNames(Collection<String> names){
		if(fieldNames==null)
			fieldNames = new LinkedHashSet<>();
		if(names!=null){
			fieldNames.addAll(names);
			if(excludedFieldNames!=null)
				excludedFieldNames.removeAll(names);
		}
		return this;
	}
	
	public FormConfiguration deleteFieldNames(Collection<String> names){
		if(fieldNames==null)
			;
		else 
			if(names!=null){
				fieldNames.removeAll(names);
				if(requiredFieldNames!=null)
					requiredFieldNames.removeAll(names);
			}
		return this;
	}
	public FormConfiguration deleteFieldNames(String...names){
		if(names!=null){
			deleteFieldNames(Arrays.asList(names));
		}
		return this;
	}
	
	public FormConfiguration addRequiredFieldNames(Boolean autoAddToFieldNames,Collection<String> names){
		if(requiredFieldNames==null)
			requiredFieldNames = new LinkedHashSet<>();
		if(names!=null){
			requiredFieldNames.addAll(names);
			if(Boolean.TRUE.equals(autoAddToFieldNames))
				addFieldNames(names);
		}
		return this;
	}
	public FormConfiguration addRequiredFieldNames(Boolean autoAddToFieldNames,String...names){
		if(requiredFieldNames==null)
			requiredFieldNames = new LinkedHashSet<>();
		if(names!=null){
			requiredFieldNames.addAll(Arrays.asList(names));
			if(Boolean.TRUE.equals(autoAddToFieldNames))
				addFieldNames(names);
		}
		return this;
	}
	
	public FormConfiguration addRequiredFieldNames(String...names){
		return addRequiredFieldNames(Boolean.TRUE, names);
	}
	
	public FormConfiguration addRequiredFieldNames(Boolean autoAddToFieldNames,FormConfiguration formConfiguration){
		return addRequiredFieldNames(autoAddToFieldNames, formConfiguration.getRequiredFieldNames());
	}
	
	public FormConfiguration addExcludedFieldNames(String...names){
		if(excludedFieldNames==null)
			excludedFieldNames = new LinkedHashSet<>();
		if(names!=null)
			excludedFieldNames.addAll(Arrays.asList(names));
		return this;
	}
	
	public FormConfiguration removeFieldNames(String...names){
		for(String name : names){
			if(fieldNames!=null)
				fieldNames.remove(name);
			
			if(requiredFieldNames!=null)
				requiredFieldNames.remove(name);
			
			if(excludedFieldNames!=null)
				excludedFieldNames.remove(name);
		}
		return this;
	}
	
	public Collection<ControlSetListener<?, ?, ?, ?, ?, ?>> getControlSetListener(){
		if(controlSetListeners == null)
			controlSetListeners = new ArrayList<>();
		return controlSetListeners;
	}
	
	public FormConfiguration addControlSetListener(ControlSetListener<?, ?, ?, ?, ?, ?> controlSetListener) {
		getControlSetListener().add(controlSetListener);
		return this;
	}
	public FormConfiguration addControlSetListeners(Collection<ControlSetListener<?, ?, ?, ?, ?, ?>> controlSetListeners) {
		for(ControlSetListener<?, ?, ?, ?, ?, ?> controlSetListener : controlSetListeners)
			addControlSetListener(controlSetListener);
		return this;
	}
	
	/**/
	
	public static FormConfiguration get(Class<?> clazz,Crud crud,String type,Boolean createIfNull){
		Key key = new Key(clazz, CommonBusinessAction.valueOf(crud.name()), StringUtils.isBlank(type) ? TYPE_INPUT_SET_DEFAULT : type);
		FormConfiguration formConfiguration = MAP.get(key);
		if(formConfiguration==null && Boolean.TRUE.equals(createIfNull))
			MAP.put(key, formConfiguration = new FormConfiguration());
		return formConfiguration;
	}
	
	public static FormConfiguration get(Class<?> clazz,Crud crud,Boolean createIfNull){
		return get(clazz,crud, null,createIfNull);
	}
	
	public static Boolean hasFieldName(FormConfiguration formConfiguration,String fieldName){ 
		return formConfiguration==null || formConfiguration.hasFieldName(fieldName);
	}
	public static Boolean hasRequiredFieldName(FormConfiguration formConfiguration,String fieldName){ 
		return formConfiguration==null || formConfiguration.hasRequiredFieldName(fieldName);
	}
	public static Boolean hasExcludedFieldName(FormConfiguration formConfiguration,String fieldName){ 
		return formConfiguration!=null && formConfiguration.hasExcludedFieldName(fieldName);
	}
	
	public static Boolean hasNoFieldNames(FormConfiguration formConfiguration){ 
		return formConfiguration==null || formConfiguration.hasNoFieldNames();
	}
	public static Boolean hasNoRequiredFieldNames(FormConfiguration formConfiguration){ 
		return formConfiguration==null || formConfiguration.hasNoRequiredFieldNames();
	}
	public static Boolean hasNoExcludedFieldNames(FormConfiguration formConfiguration){ 
		return formConfiguration==null || formConfiguration.hasNoExcludedFieldNames();
	}
	
	@SuppressWarnings("unchecked")
	public static void addControlSetListeners(FormConfiguration formConfiguration,@SuppressWarnings("rawtypes") Collection controlSetListeners){
		if(controlSetListeners!=null && formConfiguration!=null && formConfiguration.getControlSetListeners()!=null)
			for(ControlSetListener<?, ?, ?, ?, ?, ?> controlSetListener : formConfiguration.getControlSetListeners())
				controlSetListeners.add((ControlSetListener<?, ?, ?, ?, ?, ?>) controlSetListener);
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor @EqualsAndHashCode(of={"clazz","commonBusinessAction","type"})
	public static class Key implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Class<?> clazz;
		private CommonBusinessAction commonBusinessAction;
		private String type;
		
	}

	/**/
	
	public static final String TYPE_INPUT_SET_SMALLEST = "TYPE_INPUT_SET_SMALLEST";
	public static final String TYPE_INPUT_SET_BIGGEST = "TYPE_INPUT_SET_BIGGEST";
	public static final String TYPE_INPUT_SET_DEFAULT = "TYPE_INPUT_SET_DEFAULT";

	
	
}
