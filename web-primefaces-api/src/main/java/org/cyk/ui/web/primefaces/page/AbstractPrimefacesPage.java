package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.config.OutputDetailsConfiguration;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.AbstractOutputDetails;
import org.cyk.ui.api.model.DetailsBlock;
import org.cyk.ui.api.model.DetailsBlockCollection;
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
	
	@Getter protected DetailsBlockCollection<MenuModel> detailsBlocks = new DetailsBlockCollection<>();
	
	@Getter protected MenuModel mainMenuModel,contentMenuModel,contextualMenuModel;
	private String mobilePageTransition="flip";
	private Boolean mobilePageReverse=Boolean.TRUE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		for(PrimefacesPageListener listener : getListeners())
			listener.initialisationStarted(this); 
		
		
		for(PrimefacesPageListener listener : getListeners())
			listener.initialisationEnded(this); 
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(PrimefacesPageListener listener : getListeners())
			listener.afterInitialisationStarted(this);
		
		// your Code gere
		
		for(PrimefacesPageListener listener : getListeners())
			listener.afterInitialisationEnded(this); 
	}
	
	private Collection<PrimefacesPageListener> getListeners(){
		return primefacesManager.getPageListeners();
	}
	
	@Override
	public void targetDependentInitialisation() {
		for(PrimefacesPageListener listener : getListeners())
			listener.targetDependentInitialisationStarted(this); 
		
		mainMenuModel = CommandBuilder.getInstance().menuModel(mainMenu, getClass(), "mainMenuModel");
		
		if(contextualMenu!=null && contextualMenu.getCommandables().isEmpty())
			contextualMenu = null;
		
		if(contextualMenu!=null)
			for(PrimefacesPageListener listener : getListeners())
				listener.processContextualMenu(this,contextualMenu); 
		
		if(contextualMenu!=null && contextualMenu.getCommandables().isEmpty())
			contextualMenu = null;
		
		contextualMenuModel = CommandBuilder.getInstance().menuModel(contextualMenu, getClass(), "contextualMenuModel");
		
		contentMenuModel = CommandBuilder.getInstance().menuModel(contentMenu, getClass(), "contentMenu");
		
		for(PrimefacesPageListener listener : getListeners())
			listener.targetDependentInitialisationEnded(this); 
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
	
	protected DetailsBlock<MenuModel> createDetailsBlock(AbstractIdentifiable master,AbstractOutputDetails<?> details,String editOutcome,UICommandable...links){
		DetailsBlock<MenuModel> detailsBlock = new DetailsBlock<MenuModel>(null,createFormOneData(details, Crud.READ));
		configureDetailsForm((org.cyk.ui.web.primefaces.data.collector.form.FormOneData<?>) detailsBlock.getFormOneData());
		Collection<UICommandable> commandables = new ArrayList<>();
		@SuppressWarnings("unchecked")
		OutputDetailsConfiguration configuration = uiManager.findOutputDetailsConfiguration((Class<? extends AbstractOutputDetails<?>>) details.getClass());
		if(detailsBlock.getTitle()==null)
			if(configuration!=null)
				detailsBlock.setTitle(configuration.getUiLabel());
		if(links!=null){
			for(UICommandable commandable : links)
				commandables.add(commandable);
		}
		
		if(StringUtils.isBlank(editOutcome))
			if(configuration!=null)
				editOutcome = configuration.getUiEditViewId();
			
		if(StringUtils.isNotBlank(editOutcome)){
			UICommandable commandable = createViewCommandRequest("command.edit",IconType.ACTION_EDIT, editOutcome);
			/*
			commandable.addParameter(uiManager.getClassParameter(), uiManager.keyFromClass(master.getClass()));
			commandable.addParameter(uiManager.getCrudParameter(), uiManager.getCrudUpdateParameter());
			commandable.addParameter(uiManager.getIdentifiableParameter(), master.getIdentifier());
			*/
			commandable.addCrudParameters(uiManager.getCrudUpdateParameter(), master, details);
			commandables.add(commandable);
		}
		
		detailsBlock.setMenu(new DefaultMenu());
		for(UICommandable commandable : commandables){
			commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
			detailsBlock.getMenu().getCommandables().add(commandable);
		}
		detailsBlock.setMenuModel(CommandBuilder.getInstance().menuModel(detailsBlock.getMenu(), getClass(),""));
		
		detailsBlocks.add(detailsBlock);
		return detailsBlock;
	}
	
	protected DetailsBlock<MenuModel> createDetailsBlock(AbstractIdentifiable master,AbstractOutputDetails<?> details,String editOutcome){
		return createDetailsBlock(master, details, editOutcome,new Commandable[]{});
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
