package org.cyk.ui.web.api.data.collector.control;

import java.io.Serializable;
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
	
	Collection<WebInputListener> getWebInputListeners();
	void setWebInputListeners(Collection<WebInputListener> listeners);
	
	/**/
	
	public static interface WebInputListener{
		void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException;
	}
	
	public static class WebInputAdapter extends BeanAdapter implements WebInputListener,Serializable{

		private static final long serialVersionUID = -8747839786713747954L;

		@Override
		public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {}
		
	}
	
}
