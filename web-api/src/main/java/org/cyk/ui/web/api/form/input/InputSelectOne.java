package org.cyk.ui.web.api.form.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ClassUtils;
import org.cyk.ui.api.annotation.FormField;
import org.cyk.ui.api.form.UIFormData;
import org.cyk.ui.api.form.input.UIInputSelectOne;
import org.cyk.ui.api.form.input.ISelectItem;
import org.cyk.ui.web.api.form.IWebFormContainer;

@Getter @Log
public class InputSelectOne<FORM> extends AbstractWebInputComponent<Object> implements WebUIInputSelectOne<Object,FORM>,UIInputSelectOne<Object,SelectItem>,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;
	
	//@Getter @Setter
	//private IForm<FORM, ?, ?, SelectItem> form;
	@Getter
	private Collection<SelectItem> items = new ArrayList<SelectItem>();
	private String noSelectionMarker = "---";
	private String noSelectionLabel = "NO SEL";//FunctionController.i18n("combobox.noselection");
	private Boolean renderNoSelectionLabel=Boolean.TRUE;
	private String processOnSelect="@this",updateOnSelect;
	private Boolean onChangeDisable=Boolean.TRUE,booleanValueType=Boolean.FALSE;
	
	
	public InputSelectOne(UIFormData<?, ?, ?, ?> conatinerForm,UIInputSelectOne<Object,ISelectItem> input) {
		super(conatinerForm,input);
		for(ISelectItem selectItem : input.getItems())
			getItems().add(new SelectItem(selectItem.getValue(), selectItem.getLabel()));
	}
	
	@Override
	public Boolean isBoolean() {
		return Boolean.class.equals( ClassUtils.primitiveToWrapper(getField().getType()));
	}
	
	@Override
	public Boolean isEnum() {
		return getField().getType().isEnum();
	}
		
	
	@SuppressWarnings("unchecked")
	@Override
	public Converter getConverter() {
		try {
			if(isBoolean())
				return (Converter) javax.faces.convert.BooleanConverter.class.newInstance();
			if(isEnum())
				return (Converter) javax.faces.convert.EnumConverter.class.newInstance();
			return  ((IWebFormContainer<FORM, ?, ?, SelectItem>)containerForm.getContainer()).getObjectConverter();
		} catch (Exception e) {
			log.log(Level.SEVERE,e.toString(),e);
			return null;
		}
	}
	
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
		return isBoolean() || FormField.SelectOneInputType.RADIO.equals(formField.selectOneInputType());
	}
	
	@Override
	public Boolean getAddable() {
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean isSelectItemEditable() {
		return !isBoolean() && !isEnum();
	}
	
}
