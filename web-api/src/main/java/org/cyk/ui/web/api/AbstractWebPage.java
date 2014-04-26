package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.AbstractWindow;

public abstract class AbstractWebPage<FORM,OUTPUTLABEL,INPUT> extends AbstractWindow<FORM,OUTPUTLABEL,INPUT,SelectItem> implements WebUIPage<FORM,OUTPUTLABEL,INPUT>,Serializable {

	private static final long serialVersionUID = -7284361545083572063L;
	
	
	
}
