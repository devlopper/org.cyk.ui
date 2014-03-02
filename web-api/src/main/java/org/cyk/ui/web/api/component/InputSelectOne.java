package org.cyk.ui.web.api.component;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;

import org.cyk.ui.api.component.input.IInputSelectOne;
import org.cyk.ui.api.form.IForm;

@Getter
public class InputSelectOne<FORM> extends AbstractWebInputSelectOne<Object,FORM> implements IWebInputSelectOne<Object,FORM>,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;
	
	public InputSelectOne(IForm<?, ?, ?, ?> containerForm,IInputSelectOne<Object> input) {
		super(containerForm,input);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IForm<FORM,?,?,SelectItem> createForm() {
		if(field.getType().isEnum())
			return null;
	
		IForm<FORM, ?, ?, SelectItem> form =  (IForm<FORM, ?, ?, SelectItem>) containerForm.createChild(this);
		
		//form.setParent(containerForm);
		form.model(containerForm.getCommonUtils().readField(object, field,true));
		form.build();
		
		return form;
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
	

	
}
