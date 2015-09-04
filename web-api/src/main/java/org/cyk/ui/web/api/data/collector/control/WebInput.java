package org.cyk.ui.web.api.data.collector.control;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;

import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.web.api.AjaxListener;


public interface WebInput<MODEL, ROW, LABEL, CONTROL> extends WebControl<MODEL, ROW, LABEL, CONTROL> {

	CascadeStyleSheet getReadOnlyValueCss();
	void setReadOnlyValueCss(CascadeStyleSheet aCascadeStyleSheet);
	
	Converter getConverter();
	
	void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException;
	
	void setAjaxListener(AjaxListener listener);
	AjaxListener getAjaxListener();
	
	String getOnChange();
	void setOnChange(String script);
	
}
