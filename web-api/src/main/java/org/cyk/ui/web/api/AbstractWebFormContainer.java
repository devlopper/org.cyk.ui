package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;

import org.cyk.ui.api.IMessageManager;
import org.cyk.ui.api.form.AbstractFormContainer;

public abstract class AbstractWebFormContainer<FORM,OUTPUTLABEL,INPUT> extends AbstractFormContainer<FORM,OUTPUTLABEL,INPUT,SelectItem> implements IWebFormContainer<FORM,OUTPUTLABEL,INPUT,SelectItem> , Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
	
	@Inject @Getter protected IMessageManager messageManager;
	

}
