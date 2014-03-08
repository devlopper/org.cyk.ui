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
import org.cyk.ui.api.form.IForm;

@Getter
public abstract class AbstractWebInputSelectOne<VALUE_TYPE,FORM> extends AbstractWebInputComponent<VALUE_TYPE> implements IWebInputSelectOne<VALUE_TYPE,FORM>,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;
	
	//@Getter @Setter
	//private IForm<FORM, ?, ?, SelectItem> form;
	@Setter
	private Collection<SelectItem> items = new ArrayList<SelectItem>();
	private String noSelectionMarker = "---";
	private String noSelectionLabel = "NO SEL";//FunctionController.i18n("combobox.noselection");
	private Boolean renderNoSelectionLabel=Boolean.TRUE;
	private String processOnSelect="@this",updateOnSelect;
	private Boolean onChangeDisable=Boolean.TRUE,booleanValueType=Boolean.FALSE;
	
	public AbstractWebInputSelectOne(IForm<?, ?, ?, ?> conatinerForm,IInputSelectOne<VALUE_TYPE,ISelectItem> input) {
		super(conatinerForm,input);
		if(input.getItems()!=null)
			for(ISelectItem selectItem : input.getItems())
				items.add(new SelectItem(selectItem.getValue(), selectItem.getLabel()));
		/*
		form = createForm();
		if(form!=null)
			form.setParentField(this);
			*/
	}
	/*
	@Override
	public void afterChildFormUpdated() {
		Object newItem = form.getModelsObject().iterator().next();
		if(newItem!=null){
			SelectItem item = form.getContainer().item(newItem);
			Boolean found = Boolean.FALSE;
			for(SelectItem i : items)
				if(i.getLabel().equals(item.getLabel())){
					found = Boolean.TRUE;
					break;
				}
			if(Boolean.FALSE.equals(found))
				items.add(item);
		}
	}
	*/
	
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
