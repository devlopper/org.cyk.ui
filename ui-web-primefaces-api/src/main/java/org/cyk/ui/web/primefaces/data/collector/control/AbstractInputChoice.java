package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.api.ObjectConverter;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.data.collector.control.WebInputChoice;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter
public abstract class AbstractInputChoice<VALUE_TYPE> extends AbstractInput<VALUE_TYPE> implements WebInputChoice<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputChoice<VALUE_TYPE,DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = -1270441695945429412L;

	protected List<SelectItem> list = new ArrayList<SelectItem>();
	protected Boolean filtered;
	protected FilterMode filterMode = FilterMode.CONTAINS;
	protected String filterModeAsString;
	protected Boolean isAutomaticallyRemoveSelected;
	
	@Override
	public Converter getConverter() {
		return ObjectConverter.getInstance();
	}
	
	public String getFilterModeAsString(){
		if(filterModeAsString==null)
			switch(filterMode){
			case STARTS_WITH: filterModeAsString = "startsWith"; break;
			case CONTAINS: filterModeAsString = "contains"; break;
			case ENDS_WITH: filterModeAsString = "endsWith"; break;
			}
		return filterModeAsString;
	}
	
	@Override
	public void addChoice(Object object) {
		list.add(WebManager.getInstance().getSelectItem(object));
	}
	
	@Override
	public void removeChoice(Object object) {
		for(int index = 0 ; index < list.size() ; index++){
			if(list.get(index).getValue()!=null && list.get(index).getValue().equals(object) ){
				list.remove(index);
			}
		}	
	}
	
	@Override
	public void removeSelected() {
		removeChoice(getValue());
	}
	
	@Override
	public void removeSelectedAutomatically() {
		if(Boolean.TRUE.equals(isAutomaticallyRemoveSelected))
			removeSelected();
	}
	
	@Override
	public void addChoiceIfAutomaticallyRemoveSelected(Object object) {
		if(Boolean.TRUE.equals(isAutomaticallyRemoveSelected))
			addChoice(object);
	}
}
