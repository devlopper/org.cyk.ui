package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.AbstractWindow;
import org.cyk.ui.api.model.table.Table;

public abstract class AbstractWebPage<EDITOR,OUTPUTLABEL,INPUT,TABLE extends Table<?>> extends AbstractWindow<EDITOR,OUTPUTLABEL,INPUT,SelectItem,TABLE> implements WebUIPage<EDITOR,OUTPUTLABEL,INPUT,TABLE>,Serializable {

	private static final long serialVersionUID = -7284361545083572063L;
	
	
	
}
