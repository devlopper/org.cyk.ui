package org.cyk.ui.web.api.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.ClassUtils;
import org.cyk.ui.api.annotation.FormField;
import org.cyk.ui.api.component.input.IInputSelectOne;
import org.cyk.ui.api.component.input.ISelectItem;

@Getter
public abstract class AbstractWebInputSelectOne<VALUE_TYPE> extends AbstractWebInputComponent<VALUE_TYPE> implements IWebInputSelectOne<VALUE_TYPE>,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;
	
	@Setter
	private Collection<SelectItem> items = new ArrayList<SelectItem>();
	private String noSelectionMarker = "---";
	private String noSelectionLabel = "NO SEL";//FunctionController.i18n("combobox.noselection");
	private Boolean renderNoSelectionLabel=Boolean.TRUE;
	private String processOnSelect="@this",updateOnSelect;
	private Boolean onChangeDisable=Boolean.TRUE,booleanValueType=Boolean.FALSE;
	
	public AbstractWebInputSelectOne(IInputSelectOne<VALUE_TYPE> input) {
		super(input);
		
		if(input.getItems()!=null)
			for(ISelectItem selectItem : input.getItems())
				items.add(new SelectItem(selectItem.getValue(), selectItem.getLabel()));
		
	}
	
	/*
	@Override
	public Converter getConverter() {
		try {
			return (Converter) javax.faces.convert.BooleanConverter.class.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/
	
	public String getNoSelectionOption(){
		return noSelectionMarker+" "+noSelectionLabel+" "+noSelectionMarker;
	}
	
	public void valueChangeListener(ValueChangeEvent event){
		//System.out.println("onChangeListener "+event.getOldValue()+" _ "+event.getNewValue());
	}
	
	@Override
	public String getFamily() {
		if(isRadio())
			return "InputSelectOneRadio";
		return super.getFamily();
	}
	
	public boolean isCombobox(){
		return FormField.SelectOneInputType.COMBOBOX.equals(formField.selectOneInputType());
	}
	
	public boolean isRadio(){
		return Boolean.class.equals(ClassUtils.primitiveToWrapper(field.getType())) || 
				FormField.SelectOneInputType.RADIO.equals(formField.selectOneInputType());
	}
	
	/*
	@Override
	protected Object __valueToSet__(TYPE value) {
		return booleanValueType?Boolean.valueOf((String)value):value;
	}*/
	
}
