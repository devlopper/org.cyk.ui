package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.AbstractWindow;
import org.cyk.ui.api.model.table.Table;
import org.omnifaces.util.Faces;

public abstract class AbstractWebPage<EDITOR,OUTPUTLABEL,INPUT,TABLE extends Table<?>> extends AbstractWindow<EDITOR,OUTPUTLABEL,INPUT,SelectItem,TABLE> implements WebUIPage<EDITOR,OUTPUTLABEL,INPUT,TABLE>,Serializable {

	private static final long serialVersionUID = -7284361545083572063L;
	
	@Inject protected WebManager webManager;
	@Getter @Setter protected String footer,messageDialogOkButtonOnClick;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		footer="CYK Systems - All rights Reserved.";
	}
	
	protected String requestParameter(String name){
		return Faces.getRequestParameter(name);
	}
	
}
