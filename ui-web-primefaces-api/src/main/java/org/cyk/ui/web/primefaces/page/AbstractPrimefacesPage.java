package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.api.model.DetailsBlock;
import org.cyk.ui.api.model.DetailsBlockCollection;
import org.cyk.ui.api.model.event.AbstractEventCalendar;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.api.model.table.AbstractTable.RenderType;
import org.cyk.ui.api.model.table.CellAdapter;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.api.AbstractWebPage;
import org.cyk.ui.web.api.AjaxBuilder;
import org.cyk.ui.web.primefaces.CommandBuilder;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.EventCalendar;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.ItemCollectionAdapter;
import org.cyk.ui.web.primefaces.PrimefacesManager;
import org.cyk.ui.web.primefaces.PrimefacesMessageManager;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.Tree;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.cdi.BeanListener;
import org.cyk.utility.common.cdi.DefaultBeanAdapter;
import org.cyk.utility.common.helper.StringHelper;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.menu.MenuModel;

@Getter @Setter
public abstract class AbstractPrimefacesPage extends AbstractWebPage<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,Commandable> implements Serializable {

	private static final long serialVersionUID = -1367372077209082614L;
	
	/*@Inject*/// @Getter protected ValidationPolicy validationPolicy;
	@Inject transient protected UIManager uiManager;
	@Inject transient protected PrimefacesMessageManager messageManager;
	@Inject protected UserSession userSession;
	@Inject transient protected PrimefacesManager primefacesManager;
	
	protected DetailsBlockCollection<MenuModel> detailsBlocks = new DetailsBlockCollection<>();
	
	protected MenuModel mainMenuModel,contentMenuModel,contextualMenuModel,windowHierachyMenuModel,detailsMenuModel;
	private String mobilePageTransition="flip";
	protected Boolean mobilePageReverse=Boolean.TRUE,showTreeMenu=Boolean.FALSE;
	protected Tree treeMenu;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		for(PrimefacesPageListener listener : PrimefacesPageListener.COLLECTION)
			listener.initialisationStarted(this); 
		
		
		for(PrimefacesPageListener listener : PrimefacesPageListener.COLLECTION)
			listener.initialisationEnded(this); 
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(PrimefacesPageListener listener : PrimefacesPageListener.COLLECTION)
			listener.afterInitialisationStarted(this);
		
		// your Code gere
		
		for(PrimefacesPageListener listener : PrimefacesPageListener.COLLECTION)
			listener.afterInitialisationEnded(this); 
		
		//debug(tables.iterator().next());
		//System.out.println("Tables : "+tables.iterator().next());
		
		for(AbstractTable<?, ?, ?> table : tables)
			//onDocumentLoadJavaScript = 
			tableFormatJavaScript((org.cyk.ui.web.primefaces.Table<?>) table, Boolean.TRUE);
	}
	
	@Override
	public void targetDependentInitialisation() {
		for(PrimefacesPageListener listener : PrimefacesPageListener.COLLECTION)
			listener.targetDependentInitialisationStarted(this); 
		
		mainMenuModel = CommandBuilder.getInstance().menuModel(mainMenu, getClass(), "mainMenuModel");
		
		if(contextualMenu!=null && contextualMenu.getCommandables().isEmpty())
			contextualMenu = null;
		
		if(contextualMenu!=null)
			for(PrimefacesPageListener listener : PrimefacesPageListener.COLLECTION)
				listener.processContextualMenu(this,contextualMenu); 
		
		if(contextualMenu!=null && contextualMenu.getCommandables().isEmpty())
			contextualMenu = null;
		
		if(detailsMenu!=null && detailsMenu.getCommandables().isEmpty())
			detailsMenu = null;
		else if(detailsMenu!=null){
			detailsMenu.setRenderType(UIMenu.RenderType.TAB);
			detailsMenu.setRequestedCommandable(requestParameter(UniformResourceLocatorParameter.TAB_ID));
		}
		contextualMenuModel = CommandBuilder.getInstance().menuModel(contextualMenu, getClass(), "contextualMenuModel");
		
		contentMenuModel = CommandBuilder.getInstance().menuModel(contentMenu, getClass(), "contentMenu");
		
		detailsMenuModel = CommandBuilder.getInstance().menuModel(detailsMenu, getClass(), "detailsMenu");
		
		/*for(FormOneData<?, ?, ?, ?, ?, ?> form : formOneDatas){
			System.out.println("FFF");
			if(Boolean.TRUE.equals(form.getSubmitCommandable().getCommand().getShowExecutionProgress())){
				final String progressBarWidgetVar = ((org.cyk.ui.web.primefaces.data.collector.form.FormOneData<?>)form).getProgressBarWidgetVar();
				System.out
						.println("AbstractPrimefacesPage.targetDependentInitialisation() : "+progressBarWidgetVar+" : "+form.getSubmitCommandable().getOnClick());
				//Starts on click
				JavaScriptHelper.getInstance().add(form.getSubmitCommandable().getOnClick(), PrimefacesManager.getInstance().getStartScript(progressBarWidgetVar));
				//Cancel on execution ends
				form.getSubmitCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
					private static final long serialVersionUID = -4119943624542439662L;
					@Override
					public Object succeed(UICommand command, Object parameter) {
						Ajax.oncomplete(PrimefacesManager.getInstance().getCancelScript(progressBarWidgetVar));
						return super.succeed(command, parameter);
					}
					@Override
					public Object fail(UICommand command, Object parameter,Throwable throwable) {
						Ajax.oncomplete(PrimefacesManager.getInstance().getCancelScript(progressBarWidgetVar));
						return super.fail(command, parameter, throwable);
					}
				});
			}
		}*/
		
		for(PrimefacesPageListener listener : PrimefacesPageListener.COLLECTION)
			listener.targetDependentInitialisationEnded(this); 
	}
	
	protected void buildTable(AbstractTable<?, ?, ?> atable){
		super.buildTable(atable);
		//tableFormatJavaScript((Table<?>) atable,Boolean.TRUE);
	}
	
	protected String tableFormatJavaScript(Table<?> table,Boolean onDocumentLoad) {
		String script = table.getFormatJavaScript();
		if(Boolean.TRUE.equals(onDocumentLoad)){
			onDocumentLoadJavaScript = javaScriptHelper.add(onDocumentLoadJavaScript, script);
		}
		return script;
	}
	
	protected void tables(){}
	
	@Override
	protected <DATA> FormOneData<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> __createFormOneData__(String submitCommandableLabelId) {
		final FormOneData<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> form = 
				new org.cyk.ui.web.primefaces.data.collector.form.FormOneData<>(submitCommandableLabelId);
		
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
	
	@Override
	public AjaxBuilder createAjaxBuilder(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName){
		return super.createAjaxBuilder(form, fieldName).classSelectorSymbol(Constant.CHARACTER_AT.toString());
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected <I extends AbstractIdentifiable> void configureDetailsForm(org.cyk.ui.web.primefaces.data.collector.form.FormOneData<?> form,DetailsConfigurationListener.Form<I,?> listener){
		if(Boolean.FALSE.equals(form.getRendered()))
			return;
		form.setShowCommands(Boolean.FALSE);
		if(listener!=null){
			Boolean rendered = listener.isRendered(this);
			//if(rendered==null)
				if(StringUtils.isBlank(listener.getTabId()))
					form.setRendered(Boolean.TRUE);
				else
					setRenderedIfDetailsMenuCommandable(listener.getTabId(), form,listener.getEnabledInDefaultTab());
			form.addControlSetListener(getDetailsConfiguration(listener.getDataClass()).getFormControlSetAdapter(listener.getIdentifiableClass()));
			
			if(Boolean.FALSE.equals(rendered))
				form.setRendered(Boolean.FALSE);
		}
		//if(form.getData()==null || ((AbstractOutputDetails<?>)form.getData()).getMaster()==null)
		//	return;
		
		form.setMenu(new DefaultMenu());
		form.getMenu().setRenderType(UIMenu.RenderType.BAR);
		AbstractIdentifiable formIdentifiable = listener.getFormIdentifiable() == null ? ((AbstractOutputDetails<?>)form.getData()).getMaster() : listener.getFormIdentifiable(); 
		UICommandable commandable = form.getMenu().addCommandable(Builder.createCrud(Crud.UPDATE, formIdentifiable ,"command.update"
				, Icon.ACTION_UPDATE,listener.getFormViewIdentifier()));
		
		commandable.addParameter(UniformResourceLocatorParameter.FORM_IDENTIFIER, listener.getFormConfigurationIdentifier());
		commandable.addParameter(UniformResourceLocatorParameter.TAB_ID, listener.getFormConfigurationIdentifier());
		/*
		commandable = form.getMenu().addCommandable(Builder.createCrud(Crud.DELETE, formIdentifiable ,"command.delete"
				, Icon.ACTION_DELETE,listener.getFormViewIdentifier()));
		commandable.setParameter(UniformResourceLocatorParameter.FORM_IDENTIFIER, listener.getFormConfigurationIdentifier());
		*/
		form.setMenuModel(getClass(), "details.menuModel");
	}
	
	protected <T,I extends AbstractIdentifiable> org.cyk.ui.web.primefaces.data.collector.form.FormOneData<T> createDetailsForm(Class<T> aClass,I identifiable
			,final DetailsConfigurationListener.Form<I,T> listener){
		org.cyk.ui.web.primefaces.data.collector.form.FormOneData<T> details = null;
		details = (org.cyk.ui.web.primefaces.data.collector.form.FormOneData<T>) createFormOneData(listener.createData(identifiable), Crud.READ);
		//details.setRendered(listener.isRendered(this));
		if(Boolean.TRUE.equals(details.getRendered())){
			configureDetailsForm(details,listener);
		}
		return details;
	}
	
	protected void configureDetailsForm(org.cyk.ui.web.primefaces.data.collector.form.FormOneData<?> form){
		configureDetailsForm(form,null);
	}
	
	protected DetailsBlock<MenuModel> createDetailsBlock(AbstractIdentifiable master,AbstractOutputDetails<?> details,String editOutcome,UICommandable...links){
		DetailsBlock<MenuModel> detailsBlock = new DetailsBlock<MenuModel>(null,createFormOneData(details, Crud.READ));
		configureDetailsForm((org.cyk.ui.web.primefaces.data.collector.form.FormOneData<?>) detailsBlock.getFormOneData(),null);
		Collection<UICommandable> commandables = new ArrayList<>();
		/*@SuppressWarnings("unchecked")
		OutputDetailsConfiguration configuration = uiManager.findOutputDetailsConfiguration((Class<? extends AbstractOutputDetails<?>>) details.getClass());
		if(detailsBlock.getTitle()==null)
			if(configuration!=null)
				detailsBlock.setTitle(configuration.getUiLabel());
		*/
		if(links!=null){
			for(UICommandable commandable : links)
				commandables.add(commandable);
		}
		/*
		if(StringUtils.isBlank(editOutcome))
			if(configuration!=null)
				editOutcome = configuration.getUiEditViewId();
		*/	
		if(StringUtils.isNotBlank(editOutcome)){
			UICommandable commandable = instanciateCommandableBuilder().setLabelFromId("command.edit").setIcon(Icon.ACTION_EDIT).setView(editOutcome)
					.addCrudParameters(Crud.UPDATE, master/*,details*/).create();
			/*
			commandable.addParameter(uiManager.getClassParameter(), uiManager.keyFromClass(master.getClass()));
			commandable.addParameter(uiManager.getCrudParameter(), uiManager.getCrudUpdateParameter());
			commandable.addParameter(uiManager.getIdentifiableParameter(), master.getIdentifier());
			*/
			//commandable.addCrudParameters(uiManager.getCrudUpdateParameter(), master, details);
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
	
	public <T> Table<T> createDetailsTable(Class<T> aClass,final DetailsConfigurationListener.Table<?,T> listener){
		@SuppressWarnings("unchecked")
		Table<T> table = (Table<T>) createTable(listener.getDataClass(), null, null);
		//table.setShowHeader(Boolean.FALSE);
		//System.out.println("Show header : "+table.getShowHeader());
		//tableFormatJavaScript(table, Boolean.TRUE);
		table.setRendered(listener.isRendered(this));
		if(table.getRendered()==null)
			if(StringUtils.isBlank(listener.getTabId()))
				table.setRendered(Boolean.TRUE);
			else{
				table.setRendered(setRenderedIfDetailsMenuCommandable(listener.getTabId(), table,listener.getEnabledInDefaultTab()));
			}
		//TODO we can go out from here???
		
		//table.getColumnListeners().add(new DefaultColumnAdapter());
		//table.setRendered(listener.getRendered());
		table.getColumnListeners().add(new Table.ColumnAdapter());//internal should be first
		table.getColumnListeners().add(PrimefacesManager.getDetailsConfiguration(aClass).getTableColumnAdapter(aClass,this));
		
		if(listener.getRowAdapter()!=null)
			table.getRowListeners().add(listener.getRowAdapter());
		
		if(listener.getColumnAdapter()!=null)
			table.getColumnListeners().add(listener.getColumnAdapter());
		
		if(listener.getCellAdapter()!=null)
			table.getCellListeners().add(listener.getCellAdapter());
		
		configureDetailsTable(aClass,table, listener);
		//table.getColumnListeners().add(new Table.ColumnAdapter());
		//buildTable(table);
		table.setInplaceEdit(Boolean.FALSE);
		if(Boolean.TRUE.equals(listener.getAutomaticallySetTitle()))
			table.setTitle(inject(LanguageBusiness.class).findClassLabelText(listener.getIdentifiableClass()));
		else
			table.setTitle(null);//TODO can be in listener as boolean method or computed base on others details
		return table;
	}
	
	protected AbstractIdentifiable detailsTableRowIdentifiable(Object rowData){
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> void configureDetailsTable(Class<T> aClass,final Table<T> table,final DetailsConfigurationListener.Table<?,?> listener){
		if(Boolean.FALSE.equals(table.getRendered()))
			return;
		//table.getColumnListeners().add(new DefaultColumnAdapter());
		if(listener.getColumnAdapter()!=null)
			table.getColumnListeners().add(listener.getColumnAdapter());
		
		//if(listener.getRowAdapter()!=null)
		//	table.getRowListeners().add(listener.getRowAdapter());
		
		table.getRowListeners().add(new Table.RowAdapter<T>(getUserSession()));
		
		table.setLazyLoad(Boolean.FALSE);
		table.setEditable(Boolean.FALSE);
		if(StringUtils.isBlank(listener.getTitleId()))
			table.setShowHeader(Boolean.FALSE);
		else
			table.setTitle(text(listener.getTitleId()));
		
		table.setShowToolBar(Boolean.TRUE);
		table.setShowOpenCommand(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.READ)));
		table.setShowEditColumn(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.UPDATE)));
		table.setShowAddRemoveColumn(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.DELETE)));
		
		//table.setShowFooter(Boolean.FALSE);
		//tableFormatJavaScript(table, Boolean.TRUE);
		
		table.setIdentifiableClass(listener.getIdentifiableClass());
		
		for(Object object : listener.getDatas())
			table.getInitialData().add((T) object);
		
		if(uiManager.isMobileDevice(userDeviceType))
			table.setRenderType(RenderType.LIST);
		
		/*List<Crud> crudCollection = new ArrayList<>();
		if(inject(GlobalIdentifierBusiness.class).isCreatable(identifiableClass))
			crudCollection.add(Crud.CREATE);
		if(inject(GlobalIdentifierBusiness.class).isReadable(arg0))
			crudCollection.add(Crud.CREATE);
		*/
		
		//if(Boolean.TRUE.equals(table.getShowXXXCommand())){
		if(!StringHelper.getInstance().isBlank(listener.getEditPageOutcome())){
			table.getUpdateCommandable().setRendered(Boolean.TRUE);
			table.getUpdateCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
				private static final long serialVersionUID = 8640883295366346645L;
				@Override
				public void serve(UICommand command, Object parameter) {
					//debug(parameter);
					navigationManager.redirectToDynamicCrudOne(listener.getFormIdentifiable(),Crud.UPDATE
								,table.getUpdateRowCommandable().getParameters(),listener.getEditPageOutcome());
				}
			});
		}
		//}
		
		//if(Boolean.TRUE.equals(table.getShowOpenCommand())){
			table.getOpenRowCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
				private static final long serialVersionUID = 8640883295366346645L;
				@Override
				public void serve(UICommand command, Object parameter) {
					if( ((Row<?>)parameter).getData() instanceof  AbstractOutputDetails){
						navigationManager.redirectToDynamicConsultOne(((Row<? extends AbstractOutputDetails<?>>)parameter).getData().getMaster(),
								table.getOpenRowCommandable().getParameters());
					}
				}
			});
		//}
		//if(Boolean.TRUE.equals(table.getShowEditColumn())){
			table.getUpdateRowCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
				private static final long serialVersionUID = 8640883295366346645L;
				@Override
				public void serve(UICommand command, Object parameter) {
					if( ((Row<?>)parameter).getData() instanceof  AbstractOutputDetails){
						navigationManager.redirectToDynamicCrudOne(((Row<? extends AbstractOutputDetails<?>>)parameter).getData().getMaster(),Crud.UPDATE
								,table.getUpdateRowCommandable().getParameters());
					}
				}
			});
		//}
		
		//if(Boolean.TRUE.equals(table.getShowAddRemoveColumn())){
			table.getRemoveRowCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
				private static final long serialVersionUID = 8640883295366346645L;
				@Override
				public void serve(UICommand command, Object parameter) {
					if( ((Row<?>)parameter).getData() instanceof  AbstractOutputDetails){
						navigationManager.redirectToDynamicCrudOne(((Row<? extends AbstractOutputDetails<?>>)parameter).getData().getMaster(),Crud.DELETE
								,table.getRemoveRowCommandable().getParameters());
					}
				}
			});
		//}
		
		/*((Table<Object>)table).getRowListeners().add(new RowAdapter<Object>(){
			@Override
			public void added(Row<Object> row) {
				super.added(row);
				row.setOpenable(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.READ)));
				row.setUpdatable(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.UPDATE)));
				row.setDeletable(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.DELETE)));
			}
		});*/
	}
	
	protected <TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> ItemCollection<TYPE,IDENTIFIABLE,COLLECTION> instanciateItemCollection
		(String identifier,Class<TYPE> aClass,Class<IDENTIFIABLE> identifiableClass,COLLECTION valueCollection){
		ItemCollection<TYPE,IDENTIFIABLE,COLLECTION> collection = new ItemCollection<TYPE,IDENTIFIABLE,COLLECTION>(identifier,aClass,identifiableClass,valueCollection,Crud.CREATE);
		return collection;
	}
	
	@SuppressWarnings("unchecked")
	protected <TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> ItemCollection<TYPE,IDENTIFIABLE,COLLECTION> createItemCollection(org.cyk.ui.web.primefaces.data.collector.form.FormOneData<?> form
			,String identifier,Class<TYPE> aClass,Class<IDENTIFIABLE> identifiableClass,COLLECTION collectionIdentifiable,Collection<IDENTIFIABLE> identifiables,AbstractItemCollection.Listener<TYPE, IDENTIFIABLE,COLLECTION,SelectItem> listener){
		ItemCollection<TYPE,IDENTIFIABLE,COLLECTION> collection = instanciateItemCollection(identifier, aClass, identifiableClass,collectionIdentifiable);
		form.getItemCollections().add(collection);
		
		collection.getItemCollectionListeners().add(new ItemCollectionAdapter<TYPE,IDENTIFIABLE,COLLECTION>(collectionIdentifiable,Crud.CREATE
				,(FormOneData<AbstractIdentifiable, ?, ?, ?, ?, ?>) form,identifiableClass){
			private static final long serialVersionUID = 4920928936636548919L;
			@Override
			public void instanciated(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SelectItem> itemCollection,TYPE item) {
				item.setForm(createFormOneData(item,Crud.CREATE));
			}
		});
		
		if(listener!=null){
			collection.setCrud(listener.getCrud());
			collection.getItemCollectionListeners().add(listener);
			collection.setEditable(Crud.isCreateOrUpdate(listener.getCrud()));
			collection.getAddCommandable().setRendered(Boolean.TRUE.equals(collection.getEditable()) && Boolean.TRUE.equals(listener.isShowAddButton()));
			collection.getDeleteCommandable().setRendered(Boolean.TRUE.equals(collection.getEditable()) &&  collection.getAddCommandable().getRendered());
			if(listener.getInputChoice()!=null)
				collection.setInputChoice((InputChoice<?, ?, ?, ?, ?, SelectItem>) listener.getInputChoice());
			collection.setIsInputChoiceUnique(listener.getIsInputChoiceUnique());
		}
		
		if(Crud.isCreateOrUpdate(collection.getCrud()))
			if(collection.getInputChoice()!=null)
				collection.getInputChoice().setIsAutomaticallyRemoveSelected(Boolean.TRUE);
		
		if(collection.getInputChoice()!=null){
			collection.setItemLabel(collection.getInputChoice().getLabel());
		}
		
		collection.setShowFooter(collection.getAddCommandable().getRendered());
		onDocumentLoadJavaScript = javaScriptHelper.add(onDocumentLoadJavaScript, collection.getFormatJavaScript());
		
		if(identifiables!=null)
			for(IDENTIFIABLE identifiable : identifiables)
				collection.add(identifiable,listener.getMasterSelected(collection, identifiable));
		return collection;
	}
	

	public String mobilePageOutcome(String pageId){
		return navigationManager.mobilePageOutcome(pageId,mobilePageTransition,mobilePageReverse);
	}
	
	protected DetailsConfiguration getDetailsConfiguration(Class<?> detailsClass){
		return PrimefacesManager.getDetailsConfiguration(detailsClass);
	}
		
	/**/
	
	public static interface DetailsConfigurationListener<IDENTIFIABLE extends AbstractIdentifiable,DATA>{
		Crud[] getCruds();
		String getTitleId();
		Boolean getAutomaticallySetTitle();
		DATA createData(IDENTIFIABLE identifiable);
		Class<DATA> getDataClass();
		Class<IDENTIFIABLE> getIdentifiableClass();
		IDENTIFIABLE getIdentifiable(DATA data);
		Boolean isRendered(AbstractPrimefacesPage page);
		String getTabId();
		String getFormConfigurationIdentifier();
		String getFormViewIdentifier();
		Boolean getAutoAddTabCommandable();
		Boolean getEnabledInDefaultTab();
		Boolean getIsIdentifiableMaster();
		AbstractIdentifiable getFormIdentifiable();
		Collection<? extends AbstractIdentifiable> getMasters();
		
		String getEditPageOutcome();
		DetailsConfigurationListener<IDENTIFIABLE,DATA> setEditPageOutcome(String editPageOutcome);
		
		/*  */
		
		@Getter @Setter
		public static class AbstractDetailsConfigurationAdapter<IDENTIFIABLE extends AbstractIdentifiable,DATA> extends BeanAdapter implements DetailsConfigurationListener<IDENTIFIABLE,DATA>{
			protected static final long serialVersionUID = 6031762560954439308L;
			protected Class<IDENTIFIABLE> identifiableClass;
			protected Class<DATA> dataClass;
			protected String tabId,titleId,formConfigurationIdentifier,formViewIdentifier;
			protected Boolean autoAddTabCommandable = Boolean.TRUE,enabledInDefaultTab=Boolean.FALSE,automaticallySetTitle=Boolean.FALSE;
			protected Crud[] cruds = new Crud[]{Crud.CREATE,Crud.READ,Crud.UPDATE,Crud.DELETE};//TODO should be removed ??? because value is taken from database
			protected Boolean isIdentifiableMaster=Boolean.TRUE;
			protected Collection<? extends AbstractIdentifiable> masters;
			protected AbstractIdentifiable formIdentifiable;
			protected String editPageOutcome;
			
			public AbstractDetailsConfigurationAdapter(Class<IDENTIFIABLE> identifiableClass, Class<DATA> dataClass) {
				super();
				this.identifiableClass = identifiableClass;
				this.dataClass = dataClass;
				titleId = IdentifierProvider.Adapter.getTabOf(identifiableClass);
				
				tabId = getTitleId();
			}
			
			public DetailsConfigurationListener<IDENTIFIABLE,DATA> setEditPageOutcome(String editPageOutcome){
				this.editPageOutcome = editPageOutcome;
				return this;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Boolean isRendered(AbstractPrimefacesPage page) {
				AbstractIdentifiable identifiable = null;
				if(page instanceof AbstractConsultPage<?>)
					identifiable = ((AbstractConsultPage<AbstractIdentifiable>)page).getIdentifiable();
				
				if(StringUtils.isBlank(((AbstractPrimefacesPage)page).getSelectedTabId()))
					return getEnabledInDefaultTab();
				return ((AbstractPrimefacesPage)page).getSelectedTabId().equals(getTabId()) &&
						WindowInstanceManager.INSTANCE.isShowDetails(dataClass, identifiable,page);
			}
			
			/*
			@Override
			public String getTabId() {
				if(tabId==null)
					tabId = getTitleId();
				return tabId;
			}*/

			@Override
			public DATA createData(IDENTIFIABLE identifiable) {
				DATA data = null;
				if(AbstractOutputDetails.class.isAssignableFrom(dataClass)){
					try {
						data = //ClassHelper.getInstance().instanciate(getDataClass(), new Object[]{getIdentifiableClass(),identifiable});
						commonUtils.getConstructor(getDataClass(), new Class<?>[]{getIdentifiableClass()}).newInstance(identifiable);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//data = newInstance(getDataClass(), new Class<?>[]{getIdentifiableClass()}, new Object[]{identifiable});
				}
				else
					data = newInstance(dataClass);
				return data;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public IDENTIFIABLE getIdentifiable(DATA data) {
				return ((AbstractOutputDetails<IDENTIFIABLE>)data).getMaster();
			}
			
		}
		
		/**/
		
		public static interface Table<IDENTIFIABLE extends AbstractIdentifiable,ROW_DATA> extends DetailsConfigurationListener<IDENTIFIABLE,ROW_DATA>{
			Collection<IDENTIFIABLE> getIdentifiables();
			Collection<ROW_DATA> getDatas();
			ColumnAdapter getColumnAdapter();
			RowAdapter<ROW_DATA> getRowAdapter();
			CellAdapter<ROW_DATA> getCellAdapter();
			/**/
			@Getter @Setter
			public static class Adapter<IDENTIFIABLE extends AbstractIdentifiable,ROW_DATA> extends AbstractDetailsConfigurationAdapter<IDENTIFIABLE,ROW_DATA> implements Table<IDENTIFIABLE,ROW_DATA>{
				private static final long serialVersionUID = 6031762560954439308L;
				private ColumnAdapter columnAdapter;
				private RowAdapter<ROW_DATA> rowAdapter;
				private CellAdapter<ROW_DATA> cellAdapter;
				
				public Adapter(Class<IDENTIFIABLE> identifiableClass, Class<ROW_DATA> dataClass) {
					super(identifiableClass,dataClass);
					this.identifiableClass = identifiableClass;
					this.dataClass = dataClass;
				}
				
				@Override
				public Collection<IDENTIFIABLE> getIdentifiables() {
					return null;
				}
				
				@Override
				public Collection<ROW_DATA> getDatas() {
					Collection<ROW_DATA> datas = new ArrayList<>();
					Collection<IDENTIFIABLE> identifiables = getIdentifiables();
					if(identifiables!=null)
						for(IDENTIFIABLE identifiable : identifiables)
							datas.add(createData(identifiable));
					return datas;
				}
				
			}
			
		}
		
		public static interface Form<IDENTIFIABLE extends AbstractIdentifiable,DATA> extends DetailsConfigurationListener<IDENTIFIABLE,DATA>{
		
			/**/
			@Getter @Setter
			public static class Adapter<IDENTIFIABLE extends AbstractIdentifiable,DATA> extends AbstractDetailsConfigurationAdapter<IDENTIFIABLE,DATA> implements Form<IDENTIFIABLE,DATA>{
				private static final long serialVersionUID = 6031762560954439308L;
				
				public Adapter(Class<IDENTIFIABLE> identifiableClass, Class<DATA> dataClass) {
					super(identifiableClass,dataClass);
				}
				
			}	
		}
		
	}

	/**/
	
	public static interface PrimefacesPageListener extends BeanListener {

		Collection<PrimefacesPageListener> COLLECTION = new ArrayList<>();
		
		void targetDependentInitialisationStarted(AbstractPrimefacesPage abstractPrimefacesPage);
		void targetDependentInitialisationEnded(AbstractPrimefacesPage abstractPrimefacesPage);
		void processContextualMenu(AbstractPrimefacesPage abstractPrimefacesPage,UIMenu contextualMenu);
		
		/**/
		
		public static class Adapter extends DefaultBeanAdapter implements PrimefacesPageListener,Serializable {
			private static final long serialVersionUID = -7944074776241690783L;
			@Override public void targetDependentInitialisationStarted(AbstractPrimefacesPage abstractPrimefacesPage) {}
			@Override public void targetDependentInitialisationEnded(AbstractPrimefacesPage abstractPrimefacesPage) {}
			@Override public void processContextualMenu(AbstractPrimefacesPage abstractPrimefacesPage, UIMenu contextualMenu) {}
		}

	}
	
	public static class PageInstanceManager extends AbstractWebPage.PageInstanceManager implements Serializable {

		private static final long serialVersionUID = 2796674164692183550L;

	}
}
