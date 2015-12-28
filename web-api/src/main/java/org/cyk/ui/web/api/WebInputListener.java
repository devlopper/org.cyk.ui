package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.cyk.utility.common.cdi.BeanAdapter;

public interface WebInputListener{
	
	void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException;
	
	/**/
	
	public static class Adapter extends BeanAdapter implements WebInputListener,Serializable{

		private static final long serialVersionUID = -8747839786713747954L;

		@Override
		public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {}
		
		/**/
		
		public static class Default extends Adapter implements Serializable{
			private static final long serialVersionUID = -2890801679859553209L;
			
		}
	}
}