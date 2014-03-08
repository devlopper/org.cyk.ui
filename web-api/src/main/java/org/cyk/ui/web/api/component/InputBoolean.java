package org.cyk.ui.web.api.component;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;

import org.cyk.ui.api.component.input.IInputBoolean;
import org.cyk.ui.api.form.IForm;

@Getter // TODO is it necessary to have this class ???
public class InputBoolean extends AbstractWebInputSelectOne<Boolean,Object> implements IWebInputBoolean,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;
	
	public InputBoolean(IForm<?, ?, ?, ?> containerForm,IInputBoolean input) {
		super(containerForm,input);
	}
	/*
	@Override
	public Object createFormModel() {
		return null;
		//throw new IllegalStateException("Cannot have a form");
	}*/
/*
	@Override
	public IForm<Object, ?, ?, SelectItem> createForm() {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	/*
	@Override
	public Object getForm() {
		throw new IllegalStateException("Cannot have a form");
	}*/
	
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
