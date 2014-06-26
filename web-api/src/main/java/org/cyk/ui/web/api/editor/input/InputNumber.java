package org.cyk.ui.web.api.editor.input;

import java.io.Serializable;
import java.util.logging.Level;

import javax.faces.convert.Converter;

import lombok.Getter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ClassUtils;
import org.cyk.ui.api.editor.input.UIInputNumber;
import org.cyk.ui.web.api.editor.WebEditorInputs;

@Getter @Log
public class InputNumber extends AbstractWebInputComponent<Number> implements WebUIInputNumber,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	public InputNumber(WebEditorInputs<?, ?, ?, ?> containerForm,UIInputNumber input) {
		super(containerForm,input);
	}
	
	@Override
	public Converter getConverter() {
		Class<?> wrapper = ClassUtils.primitiveToWrapper(field.getType());
		try {
			return (Converter) Class.forName("javax.faces.convert."+wrapper.getSimpleName()+"Converter").newInstance();
		} catch (Exception e) {
			log.log(Level.SEVERE,e.toString(),e);
		}
		return null;
	}
	
}
