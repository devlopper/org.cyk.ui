package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.web.api.AbstractWebPage;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.model.menu.MenuModel;

public abstract class AbstractPrimefacesPage extends AbstractWebPage<DynaFormModel,DynaFormLabel,DynaFormControl,PrimefacesTable<?>> implements Serializable {

	private static final long serialVersionUID = -1367372077209082614L;
	
	@Inject @Getter protected UIManager uiManager;
	@Inject @Getter protected PrimefacesMessageManager messageManager;
	
	
	@Getter protected MenuModel mainMenuModel,contentMenuModel;
	
	@Override
	public void targetDependentInitialisation() {
		mainMenuModel = CommandBuilder.getInstance().menuModel(mainMenu, getClass(), "mainMenuModel");
		//contentMenuModel = commandBuilder.menuModel(contentMenu, getClass(), "contentMenu");	
	}

	@Override
	public Editor<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> editorInstance() {
		return new PrimefacesEditor();
	}
	
	@Override
	public <DATA> PrimefacesTable<DATA> tableInstance() {
		return new PrimefacesTable<>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <DATA> PrimefacesTable<DATA> tableInstance(Class<DATA> aDataClass) {
		return (PrimefacesTable<DATA>) super.tableInstance(aDataClass);
	}

	public String text(String code) {
		return uiManager.text(code);
	}
	
	
	
}
