package org.cyk.ui.web.api.form;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;

import org.cyk.ui.api.UIMessageManager;
import org.cyk.ui.api.form.AbstractFormContainer;
import org.cyk.ui.api.form.input.UIInputSelectOne;
import org.cyk.ui.web.api.ObjectConverter;
import org.cyk.ui.web.api.form.input.WebUIInputSelect;

public abstract class AbstractWebForm<FORM,OUTPUTLABEL,INPUT> extends AbstractFormContainer<FORM,OUTPUTLABEL,INPUT,SelectItem> implements IWebFormContainer<FORM,OUTPUTLABEL,INPUT,SelectItem> , Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
	
	@Inject @Getter protected UIMessageManager messageManager;
	@Inject @Getter protected ObjectConverter objectConverter;
	
	@Override
	public SelectItem item(Object object) {
		return new SelectItem(object,object.toString());
	}
	
	@Override
	public void addItem(UIInputSelectOne<?,SelectItem> anInput, Object object) {
		((WebUIInputSelect<?, ?>)anInput).getItems().add(item(object));
	}
}
