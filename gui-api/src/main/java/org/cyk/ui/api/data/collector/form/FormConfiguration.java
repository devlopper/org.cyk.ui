package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter @EqualsAndHashCode(of="type",callSuper=false)
public class FormConfiguration extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 218887156564236022L;

	private Type type;
	private Set<String> fieldNames,requiredFieldNames;
	
	public FormConfiguration(Type type) {
		super();
		this.type = type;
	}
	
	public FormConfiguration addFieldNames(String...names){
		if(fieldNames==null)
			fieldNames = new LinkedHashSet<>();
		if(names!=null)
			fieldNames.addAll(Arrays.asList(names));
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

	/**/
	
	public static enum Type{
		INPUT_SET_SMALLEST,
		INPUT_SET_BIGGEST,
		
		DEFAULT,
		;
	}
	
}
