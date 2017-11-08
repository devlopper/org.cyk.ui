package org.cyk.ui.web.api.resources.converter;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.NumberHelper;

public class NumberConverter extends AbstractBean implements Converter,Serializable {
	private static final long serialVersionUID = -1615078449226502960L;
	
	private Class<? extends Number> clazz;
	
	public NumberConverter(Class<? extends Number> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String number) {
		return NumberHelper.getInstance().get(clazz,number);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object number) {
		return NumberHelper.getInstance().stringify((Number)number);
	}
}