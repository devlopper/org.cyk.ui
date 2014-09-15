package org.cyk.ui.web.api.converter;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.UIManager;

@Named @Singleton 
public class UriConverter implements Converter,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3354843808744206682L;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) {
		if(StringUtils.isEmpty(string))
			return null;
		try {
			return new URI(string);
		} catch (URISyntaxException e) {
			throw new ConverterException(new FacesMessage(UIManager.getInstance().text("converter.field.error")));
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		return object==null?null:object.toString();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(new URL("fg"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
