package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.web.api.AjaxListener;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class AbstractInput<VALUE_TYPE> extends AbstractControl implements Input<VALUE_TYPE,DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>,
	WebInput<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, Serializable {

	private static final long serialVersionUID = 5671513590779656492L;

	protected Object object;
	protected String label,readOnlyValue,description,requiredMessage;
	protected CascadeStyleSheet readOnlyValueCss = new CascadeStyleSheet(); 
	protected Field field;
	protected VALUE_TYPE value;
	protected Boolean required,readOnly,disabled,keepShowingInputOnReadOnly;
	protected MessageLocation messageLocation = MessageLocation.TOP;
	protected AjaxListener ajaxListener;
	protected String onChange;
	protected Collection<WebInput.Listener> webInputListeners = new ArrayList<>();
	
	{
		readOnlyValueCss.addClass(getUniqueCssClass());
	}
	
	@Override
	public void applyValueToField() throws IllegalAccessException{
		FieldUtils.writeField(field, object, getValueToApply(), Boolean.TRUE);
	}
	
	@Override
	public VALUE_TYPE getValueToApply(){
		return value;
	}

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		for(WebInput.Listener listener : webInputListeners)
			listener.validate(facesContext, uiComponent, value);
	}
	
	@Override
	public Converter getConverter() {
		return null;
	}
	
	public void setReadOnly(Boolean value){
		this.readOnly = value;
		if(Boolean.TRUE.equals(required) && Boolean.TRUE.equals(readOnly))
			setRequired(Boolean.FALSE);
	}
	
	public void setLabel(String label){
		this.label = label;
		if(this.requiredMessage==null)
			this.requiredMessage = UIManager.getInstance().getLanguageBusiness().findText("input.value.required", new Object[]{label});
	}
	
}
