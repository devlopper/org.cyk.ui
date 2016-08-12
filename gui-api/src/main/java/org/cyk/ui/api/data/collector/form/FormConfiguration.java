package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.Crud;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter 
public class FormConfiguration extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 218887156564236022L;
	
	private static final Map<Key,FormConfiguration> MAP = new HashMap<>();
	
	/* Key */
	private Key key;
	
	/* Value */
	private Set<String> fieldNames,requiredFieldNames,excludedFieldNames;
	
	public FormConfiguration addFieldNames(String...names){
		if(fieldNames==null)
			fieldNames = new LinkedHashSet<>();
		if(names!=null){
			Collection<String> collection = Arrays.asList(names);
			fieldNames.addAll(collection);
			if(excludedFieldNames!=null)
				excludedFieldNames.removeAll(collection);
		}
		return this;
	}
	
	public FormConfiguration addRequiredFieldNames(String...names){
		if(requiredFieldNames==null)
			requiredFieldNames = new LinkedHashSet<>();
		if(names!=null){
			requiredFieldNames.addAll(Arrays.asList(names));
			addFieldNames(names);
		}
		return this;
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
	
	/**/
	
	public static FormConfiguration get(Class<?> clazz,Crud crud,String type,Boolean createIfNull){
		Key key = new Key(clazz, CommonBusinessAction.valueOf(crud.name()), StringUtils.isBlank(type) ? TYPE_INPUT_SET_DEFAULT : type);
		FormConfiguration formConfiguration = MAP.get(key);
		if(formConfiguration==null)
			MAP.put(key, formConfiguration = new FormConfiguration());
		return formConfiguration;
	}
	
	public static FormConfiguration get(Class<?> clazz,Crud crud,Boolean createIfNull){
		return get(clazz,crud, null,createIfNull);
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
