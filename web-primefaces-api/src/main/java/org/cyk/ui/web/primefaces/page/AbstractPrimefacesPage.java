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
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.config.OutputDetailsConfiguration;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.api.model.AbstractOutputDetails;
import org.cyk.ui.api.model.DetailsBlock;
import org.cyk.ui.api.model.DetailsBlockCollection;
import org.cyk.ui.api.model.ItemCollectionListener.ItemCollectionAdapter;
import org.cyk.ui.api.model.event.AbstractEventCalendar;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.api.model.table.AbstractTable.RenderType;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.api.AbstractWebPage;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.primefaces.CommandBuilder;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.EventCalendar;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.PrimefacesManager;
import org.cyk.ui.web.primefaces.PrimefacesMessageManager;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.Tree;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.utility.common.cdi.BeanAdapter;
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
	
	@Getter protected MenuModel mainMenuModel,contentMenuModel,contextualMenuModel,windowHierachyMenuModel,detailsMenuModel;
	private String mobilePageTransition="flip";
	@Getter protected Boolean mobilePageReverse=Boolean.TRUE,showTreeMenu=Boolean.FALSE;
	@Getter protected Tree treeMenu;
	
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
		
		if(detailsMenu!=null && detailsMenu.getCommandables().isEmpty())
			detailsMenu = null;
		else if(detailsMenu!=null){
			detailsMenu.setRenderType(UIMenu.RenderType.TAB);
			String selectedDetails = requestParameter(webManager.getRequestParameterTabId());
			if(StringUtils.isBlank(selectedDetails)){
				
			}else{
				detailsMenu.setRequestedCommandable(selectedDetails);
			}
			
		}
		contextualMenuModel = CommandBuilder.getInstance().menuModel(contextualMenu, getClass(), "contextualMenuModel");
		
		contentMenuModel = CommandBuilder.getInstance().menuModel(contentMenu, getClass(), "contentMenu");
		
		detailsMenuModel = CommandBuilder.getInstance().menuModel(detailsMenu, getClass(), "detailsMenu");
		
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
		if(Boolean.FALSE.equals(form.getRendered()))
			return;
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
	protected <T> Table<T> createDetailsTable(Class<T> aClass,final DetailsTableConfigurationListener<?,T> listener){
		@SuppressWarnings("unchecked")
		Table<T> table = (Table<T>) createTable(listener.getIdentifiableClass(), null, null);
		if(listener.getRendered()==null)
			if(StringUtils.isBlank(listener.getTabId()))
				table.setRendered(Boolean.TRUE);
			else
				setRenderedIfDetailsMenuCommandable(listener.getTabId(), table);
		
		//TODO we can go out from here???
		
		//table.getColumnListeners().add(new DefaultColumnAdapter());
		
		configureDetailsTable(aClass,table, listener);
		buildTable(table);
		
		table.setInplaceEdit(Boolean.FALSE);
		/*table.setShowEditColumn(ArrayUtils.contains(cruds, Crud.UPDATE));
		
		if(Boolean.TRUE.equals(table.getShowEditColumn())){
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
		
		table.setShowAddRemoveColumn(ArrayUtils.contains(cruds, Crud.DELETE));
		if(Boolean.TRUE.equals(table.getShowAddRemoveColumn())){
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
		*/
		return table;
	}
	/*
	protected <T> Table<T> createDetailsTable(final Class<T> aClass,Collection<T> collection,String titleId,Crud[] cruds,final String identifiableFieldName){
		return createDetailsTable(aClass, collection, null, titleId, cruds, identifiableFieldName);
	}
	
	protected <T> Table<T> createDetailsTable(Class<T> aClass,Collection<T> collection,ColumnAdapter listener,String titleId){
		return createDetailsTable(aClass, collection,listener, titleId,null,null);
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
	*/
	protected AbstractIdentifiable detailsTableRowIdentifiable(Object rowData){
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> void configureDetailsTable(Class<T> aClass,Table<T> table,final DetailsTableConfigurationListener<?,?> listener){
		if(Boolean.FALSE.equals(table.getRendered()))
			return;
		table.getColumnListeners().add(new DefaultColumnAdapter());
		if(listener.getColumnAdapter()!=null)
			table.getColumnListeners().add(listener.getColumnAdapter());
		
		table.setEditable(Boolean.FALSE);
		if(StringUtils.isBlank(listener.getTitleId()))
			table.setShowHeader(Boolean.FALSE);
		else
			table.setTitle(text(listener.getTitleId()));
		
		table.setShowToolBar(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.CREATE)));
		table.setShowEditColumn(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.UPDATE)) || Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.DELETE)));
		table.setShowOpenCommand(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.READ)));
		
		for(Object object : listener.getDatas())
			table.addRow((T) object);
		
		if(uiManager.isMobileDevice(userDeviceType))
			table.setRenderType(RenderType.LIST);
		
		if(Boolean.TRUE.equals(table.getShowOpenCommand())){
			table.getOpenRowCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
				private static final long serialVersionUID = 8640883295366346645L;
				@Override
				public void serve(UICommand command, Object parameter) {
					if( ((Row<?>)parameter).getData() instanceof  AbstractOutputDetails){
						navigationManager.redirectToDynamicConsultOne(((Row<? extends AbstractOutputDetails<?>>)parameter).getData().getMaster());
					}
				}
			});
		}
		
		((Table<Object>)table).getRowListeners().add(new RowAdapter<Object>(){
			@Override
			public void added(Row<Object> row) {
				super.added(row);
				row.setOpenable(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.READ)));
				row.setUpdatable(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.UPDATE)));
				row.setDeletable(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.DELETE)));
			}
		});
	}
	
	protected <TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> ItemCollection<TYPE,IDENTIFIABLE> createItemCollection(org.cyk.ui.web.primefaces.data.collector.form.FormOneData<?> form
			,String identifier,Class<TYPE> aClass,Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables){
		ItemCollection<TYPE,IDENTIFIABLE> collection = new ItemCollection<TYPE,IDENTIFIABLE>(identifier,aClass,identifiableClass);
		form.getItemCollections().add(collection);
		collection.getItemCollectionListeners().add(new ItemCollectionAdapter<TYPE,IDENTIFIABLE>(){
			private static final long serialVersionUID = 4920928936636548919L;
			@Override
			public void instanciated(AbstractItemCollection<TYPE,IDENTIFIABLE> itemCollection,TYPE item) {
				item.setForm(createFormOneData(item,Crud.CREATE));
			}
		});
		return collection;
	}

	public String mobilePageOutcome(String pageId){
		return navigationManager.mobilePageOutcome(pageId,mobilePageTransition,mobilePageReverse);
	}
	
	@Override
	protected String classSelector(WebInput<?, ?, ?, ?> input) {
		return PrimefacesManager.getInstance().classSelector(input);
	}
	
	/**/
	
	public static interface DetailsTableConfigurationListener<IDENTIFIABLE extends AbstractIdentifiable,ROW_DATA>{
		Crud[] getCruds();
		String getTitleId();
		Collection<IDENTIFIABLE> getIdentifiables();
		Collection<ROW_DATA> getDatas();
		ROW_DATA createData(IDENTIFIABLE identifiable);
		Class<ROW_DATA> getDataClass();
		Class<IDENTIFIABLE> getIdentifiableClass();
		ColumnAdapter getColumnAdapter();
		IDENTIFIABLE getIdentifiable(ROW_DATA data);
		Boolean getRendered();
		String getTabId();
	}
	
	@Getter @Setter
	public static class DetailsTableConfigurationAdapter<IDENTIFIABLE extends AbstractIdentifiable,ROW_DATA> extends BeanAdapter implements DetailsTableConfigurationListener<IDENTIFIABLE,ROW_DATA>{
		private static final long serialVersionUID = 6031762560954439308L;
		private Class<IDENTIFIABLE> identifiableClass;
		private Class<ROW_DATA> dataClass;
		private ColumnAdapter columnAdapter;
		private Boolean rendered=null;
		private String tabId;
		
		public DetailsTableConfigurationAdapter(Class<IDENTIFIABLE> identifiableClass, Class<ROW_DATA> dataClass) {
			super();
			this.identifiableClass = identifiableClass;
			this.dataClass = dataClass;
		}
		
		@Override
		public Crud[] getCruds() {
			return null;
		}
		@Override
		public String getTitleId() {
			return null;
		}
		@Override
		public Collection<IDENTIFIABLE> getIdentifiables() {
			return null;
		}
		@SuppressWarnings("unchecked")
		@Override
		public ROW_DATA createData(IDENTIFIABLE identifiable) {
			ROW_DATA data = newInstance(dataClass);
			if(AbstractOutputDetails.class.isAssignableFrom(dataClass)){
				((AbstractOutputDetails<IDENTIFIABLE>)data).setMaster(identifiable);
			}
			return data;
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
		
		@SuppressWarnings("unchecked")
		@Override
		public IDENTIFIABLE getIdentifiable(ROW_DATA data) {
			return ((AbstractOutputDetails<IDENTIFIABLE>)data).getMaster();
		}
		
	}
	
}
