package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.event.AbstractEventCalendar;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.api.model.table.AbstractTable.RenderType;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.api.AbstractWebPage;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.primefaces.CommandBuilder;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.EventCalendar;
import org.cyk.ui.web.primefaces.PrimefacesManager;
import org.cyk.ui.web.primefaces.PrimefacesMessageManager;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.utility.common.model.table.TableListener;
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
	@Inject protected PrimefacesManager primefacesManager;
	
	@Getter protected MenuModel mainMenuModel,contentMenuModel,contextualMenuModel;
	private String mobilePageTransition="flip";
	private Boolean mobilePageReverse=Boolean.TRUE;
	
	@Override
	public void targetDependentInitialisation() {
		mainMenuModel = CommandBuilder.getInstance().menuModel(mainMenu, getClass(), "mainMenuModel");
		
		if(contextualMenu!=null && contextualMenu.getCommandables().isEmpty())
			contextualMenu = null;
		contextualMenuModel = CommandBuilder.getInstance().menuModel(contextualMenu, getClass(), "contextualMenuModel");
		
		contentMenuModel = CommandBuilder.getInstance().menuModel(contentMenu, getClass(), "contentMenu");	
	}
	
	protected void buildTable(AbstractTable<?, ?, ?> atable){
		super.buildTable(atable);
		tableFormatJavaScript((Table<?>) atable,Boolean.TRUE);
	}
	
	protected String tableFormatJavaScript(Table<?> table,Boolean onDocumentLoad) {
		String script = table.getFormatJavaScript();
		if(Boolean.TRUE.equals(table.getShowHeader()))
			onDocumentLoadJavaScript = javaScriptHelper.add(onDocumentLoadJavaScript, script);
		return script;
	}
	
	protected void tables(){}
	
	@Override
	protected <DATA> FormOneData<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> __createFormOneData__() {
		FormOneData<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> form = new org.cyk.ui.web.primefaces.data.collector.form.FormOneData<>();
		return form;
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
	
	/**
	 * Call after page init
	 */
	protected <T> Table<T> createDetailsTable(final Class<T> aClass,Collection<T> collection,ColumnAdapter listener,String titleId,Boolean editable,Boolean deletable,final String identifiableFieldName){
		@SuppressWarnings("unchecked")
		Table<T> table = (Table<T>) createTable(aClass, null, null);
		table.getColumnListeners().add(new DefaultColumnAdapter());
		if(listener!=null)
			table.getColumnListeners().add(listener);
		configureDetailsTable(table, titleId);
		buildTable(table);
		if(collection!=null)
			table.addRows(collection);
		
		table.setInplaceEdit(Boolean.FALSE);
		table.setShowEditColumn(editable);
		if(Boolean.TRUE.equals(editable)){
			table.getCrudOneRowCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
				private static final long serialVersionUID = 5577628912554608271L;
				@SuppressWarnings("unchecked")
				@Override
				public void serve(UICommand command, Object parameter) {
					AbstractIdentifiable identifiable = null;
					if(StringUtils.isNotBlank(identifiableFieldName)){
						Object data = ((Row<T>)parameter).getData();
						identifiable = (AbstractIdentifiable) commonUtils.readField(data, FieldUtils.getField(aClass,identifiableFieldName , Boolean.TRUE), Boolean.FALSE);
					}else
						identifiable = detailsTableRowIdentifiable(((Row<T>)parameter).getData());
					
					if(identifiable!=null)
						redirectToEditOne(identifiable);
				}
			});
		}
		
		table.setShowAddRemoveColumn(deletable);
		if(Boolean.TRUE.equals(deletable)){
			table.getRemoveRowCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
				private static final long serialVersionUID = 5577628912554608271L;
				@SuppressWarnings("unchecked")
				@Override
				public void serve(UICommand command, Object parameter) {
					AbstractIdentifiable identifiable = null;
					if(StringUtils.isNotBlank(identifiableFieldName)){
						Object data = ((Row<T>)parameter).getData();
						identifiable = (AbstractIdentifiable) commonUtils.readField(data, FieldUtils.getField(aClass,identifiableFieldName , Boolean.TRUE), Boolean.FALSE);
					}else
						identifiable = detailsTableRowIdentifiable(((Row<T>)parameter).getData());
					
					if(identifiable!=null)
						redirectToDeleteOne(identifiable);
				}
			});
		}
		((Commandable)table.getAddRowCommandable()).getButton().setRendered(Boolean.FALSE);
		
		return table;
	}
	
	protected <T> Table<T> createDetailsTable(final Class<T> aClass,Collection<T> collection,String titleId,Boolean editable,Boolean deletable,final String identifiableFieldName){
		return createDetailsTable(aClass, collection, null, titleId, editable, deletable, identifiableFieldName);
	}
	
	protected <T> Table<T> createDetailsTable(Class<T> aClass,Collection<T> collection,ColumnAdapter listener,String titleId){
		return createDetailsTable(aClass, collection,listener, titleId, Boolean.FALSE,Boolean.FALSE,null);
	}
	
	protected <T> Table<T> createDetailsTable(Class<T> aClass,Collection<T> collection,String titleId){
		return createDetailsTable(aClass, collection,null, titleId);
	}
	
	protected <T> Table<T> createDetailsTable(Class<T> aClass,Collection<T> collection,TableListener<Row<T>, Column, T, String, Cell, String> listener){
		return createDetailsTable(aClass, collection,listener);
	}
	
	protected <T> Table<T> createDetailsTable(Class<T> aClass,Collection<T> collection){
		return createDetailsTable(aClass, collection, "");
	}
	
	protected AbstractIdentifiable detailsTableRowIdentifiable(Object rowData){
		return null;
	}
	
	protected void configureDetailsTable(Table<?> table,String titleId){
		table.getColumnListeners().add(new DefaultColumnAdapter());
		table.setEditable(Boolean.FALSE);
		if(StringUtils.isBlank(titleId))
			table.setShowHeader(Boolean.FALSE);
		else
			table.setTitle(text(titleId));
		table.setShowToolBar(Boolean.FALSE);
		if(uiManager.isMobileDevice(userDeviceType))
			table.setRenderType(RenderType.LIST);
	}

	public String mobilePageOutcome(String pageId){
		return navigationManager.mobilePageOutcome(pageId,mobilePageTransition,mobilePageReverse);
	}
	
	@Override
	protected String classSelector(WebInput<?, ?, ?, ?> input) {
		return PrimefacesManager.getInstance().classSelector(input);
	}
	
}
