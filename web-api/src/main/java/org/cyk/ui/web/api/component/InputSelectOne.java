package org.cyk.ui.web.api.component;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.component.input.IInputSelectOne;

@Getter
public class InputSelectOne extends AbstractWebInputSelectOne<Object> implements IWebInputSelectOne<Object>,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;
	
	public InputSelectOne(IInputSelectOne<Object> input) {
		super(input);
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
