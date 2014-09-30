package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.api.model.EventCalendar;
import org.cyk.ui.api.model.table.Table.UsedFor;
import org.cyk.ui.web.api.AbstractWebPage;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.menu.MenuModel;

public abstract class AbstractPrimefacesPage extends AbstractWebPage<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,PrimefacesTable<?>> implements Serializable {

	private static final long serialVersionUID = -1367372077209082614L;
	
	/*@Inject*/// @Getter protected ValidationPolicy validationPolicy;
	@Inject @Getter protected UIManager uiManager;
	@Inject @Getter protected PrimefacesMessageManager messageManager;
	
	@Getter protected MenuModel mainMenuModel,contentMenuModel,contextualMenuModel;
	
	@Override
	public void targetDependentInitialisation() {
		mainMenuModel = CommandBuilder.getInstance().menuModel(mainMenu, getClass(), "mainMenuModel");
		contextualMenuModel = CommandBuilder.getInstance().menuModel(contextualMenu, getClass(), "contextualMenuModel");
		contentMenuModel = CommandBuilder.getInstance().menuModel(contentMenu, getClass(), "contentMenu");	
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
	public <DATA> PrimefacesTable<DATA> tableInstance(Class<DATA> aDataClass,UsedFor usedFor,Crud crud) {
		return (PrimefacesTable<DATA>) super.tableInstance(aDataClass,usedFor,crud);
	}
	/*
	@Override
	public <DATA> HierarchycalData<DATA> hierarchyInstance() {
		return new PrimefacesHierarchycalData<>();
	}*/
	
	@Override
	public EventCalendar eventCalendarInstance() {
		return new PrimefacesEventCalendar();
	}

	public String text(String code) {
		return uiManager.text(code);
	}
	
}
