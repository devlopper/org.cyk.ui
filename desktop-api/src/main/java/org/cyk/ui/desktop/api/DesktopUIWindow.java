package org.cyk.ui.desktop.api;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.model.table.Table;

public interface DesktopUIWindow<FORM,OUTPUTLABEL,INPUT,TABLE extends Table<?>> extends UIWindow<FORM,OUTPUTLABEL,INPUT,SelectItem,TABLE> {
	

}
