package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.AbstractEventCalendar;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.web.api.AbstractWebPage;
import org.cyk.ui.web.primefaces.CommandBuilder;
import org.cyk.ui.web.primefaces.EventCalendar;
import org.cyk.ui.web.primefaces.PrimefacesMessageManager;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.UserSession;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.menu.MenuModel;

public abstract class AbstractPrimefacesPage extends AbstractWebPage<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl> implements Serializable {

	private static final long serialVersionUID = -1367372077209082614L;
	
	/*@Inject*/// @Getter protected ValidationPolicy validationPolicy;
	@Inject @Getter transient protected UIManager uiManager;
	@Inject @Getter transient protected PrimefacesMessageManager messageManager;
	@Inject @Getter protected UserSession userSession;
	
	@Getter protected MenuModel mainMenuModel,contentMenuModel,contextualMenuModel;
	
	@Override
	public void targetDependentInitialisation() {
		mainMenuModel = CommandBuilder.getInstance().menuModel(mainMenu, getClass(), "mainMenuModel");
		
		contextualMenuModel = CommandBuilder.getInstance().menuModel(contextualMenu, getClass(), "contextualMenuModel");
		
		contentMenuModel = CommandBuilder.getInstance().menuModel(contentMenu, getClass(), "contentMenu");	
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(AbstractTable<?, ?, ?> atable : tables){
			Table<?> table = (Table<?>) atable;
			if(!Boolean.TRUE.equals(table.getShowHeader()))
				hideTableHeader("."+table.getUpdateStyleClass() /*".dataTableStyleClass"*/);
			if(!Boolean.TRUE.equals(table.getShowFooter()))
				hideTableFooter("."+table.getUpdateStyleClass()/*".dataTableStyleClass"*/);
		}
			
	}
	
	@Override
	protected <DATA> FormOneData<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> __createFormOneData__() {
		return new org.cyk.ui.web.primefaces.data.collector.form.FormOneData<>();
	}

	@Override
	protected AbstractTable<Object,?,?> __createTable__() {
		return new Table<Object>();
	}
	
	@Override
	public AbstractEventCalendar eventCalendarInstance() {
		return new EventCalendar();
	}
	
	protected Boolean commandsEqual(UICommandable commandable,UICommand command){
		return commandable.getCommand() == command;
	}
	
	/**/
	
	protected void configureDetailsForm(org.cyk.ui.web.primefaces.data.collector.form.FormOneData<?> form){
		form.setShowCommands(Boolean.FALSE);
	}
	
	protected void configureDetailsTable(Table<?> table,String titleId){
		table.addColumnFromDataClass();
		table.setTitle(text(titleId));
		table.setShowToolBar(Boolean.FALSE);
	}


	
}
