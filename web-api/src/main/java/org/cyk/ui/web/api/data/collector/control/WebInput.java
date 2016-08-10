package org.cyk.ui.web.api.data.collector.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;

import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.web.api.AjaxListener;
import org.cyk.utility.common.cdi.BeanAdapter;


public interface WebInput<MODEL, ROW, LABEL, CONTROL> extends WebControl<MODEL, ROW, LABEL, CONTROL> {

	CascadeStyleSheet getReadOnlyValueCss();
	void setReadOnlyValueCss(CascadeStyleSheet aCascadeStyleSheet);
	
	Converter getConverter();
	
	void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException;
	
	void setAjaxListener(AjaxListener listener);
	AjaxListener getAjaxListener();
	
	String getOnChange();
	void setOnChange(String script);
	
	Collection<WebInput.Listener> getWebInputListeners();
	void setWebInputListeners(Collection<WebInput.Listener> listeners);
	
	/**/
	
	public static interface Listener{
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException;
		
		/**/
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable{

			private static final long serialVersionUID = -8747839786713747954L;

			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {}
			
			/**/
			
			public static class Default extends Adapter implements Serializable{
				private static final long serialVersionUID = -2890801679859553209L;
				
			}
		}
	}
	
	
	
}
