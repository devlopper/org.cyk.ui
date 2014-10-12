package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.EventCalendar;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.web.api.AbstractWebPage;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.menu.MenuModel;

public abstract class AbstractPrimefacesPage extends AbstractWebPage<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl> implements Serializable {

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
	protected <DATA> FormOneData<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> __createFormOneData__() {
		return new org.cyk.ui.web.primefaces.data.collector.form.FormOneData<>();
	}

	@Override
	protected <DATA> AbstractTable<DATA,?,?> __createTable__() {
		return new Table<DATA>();
	}
	
	@Override
	public EventCalendar eventCalendarInstance() {
		return new PrimefacesEventCalendar();
	}


	
}
