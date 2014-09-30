package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.ui.api.MenuManager.Type;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.ui.api.editor.EditorInputsListener;
import org.cyk.ui.api.editor.EditorListener;
import org.cyk.ui.api.model.EventCalendar;
import org.cyk.ui.api.model.table.Table;
import org.cyk.ui.api.model.table.Table.UsedFor;
import org.cyk.ui.api.model.table.TableCell;
import org.cyk.ui.api.model.table.TableColumn;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWindow<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM,TABLE extends Table<?>> extends AbstractBean 
	implements UIWindow<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM,TABLE>,EditorListener<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM>,EditorInputsListener<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM>,
	CommandListener,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	@Inject @Getter protected ValidationPolicy validationPolicy;
	@Getter @Setter protected AbstractMethod<Object, EditorInputs<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM>> editorInputsEventListenerMethod;
	@Inject @Getter protected GenericBusiness genericBusiness;
	@Inject @Getter protected DataTreeTypeBusiness dataTreeTypeBusiness;
	@Inject @Getter protected EventBusiness eventBusiness;
	@Inject @Getter protected LanguageBusiness languageBusiness;
	
	@Getter protected Locale locale = Locale.FRENCH;
	@Getter @Setter protected UIMenu mainMenu,contextualMenu,contentMenu;
	//@Getter protected Boolean showContentMenu = Boolean.FALSE,showContextualMenu=Boolean.TRUE;
	protected Collection<Editor<?,?,?,?>> editors = new ArrayList<>();
	protected Collection<TABLE> tables = new ArrayList<>();
	protected Collection<EventCalendar> eventCalendars = new ArrayList<>();
	//protected Collection<HierarchycalData<?>> hierarchicalDatas = new ArrayList<>();
	@Inject protected MenuManager menuManager;
	@Getter protected String title,contentTitle="Content";
	
	@Override
	protected void initialisation() {
		super.initialisation();
		mainMenu = menuManager.build(Type.APPLICATION);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		//if(editors.size()==1)
		//	contentMenu = editors.iterator().next().getMenu();
		
		
		
		targetDependentInitialisation();
		for(Editor<?,?,?,?> editor : editors)
			editor.targetDependentInitialisation();
		
		for(TABLE table : tables)
			table.targetDependentInitialisation();
		/*
		for(HierarchycalData<?> hierarchicalData : hierarchicalDatas)
			hierarchicalData.targetDependentInitialisation();
		*/
		for(EventCalendar eventCalendar : eventCalendars)
			eventCalendar.targetDependentInitialisation();
	}
	
	public Boolean getShowContextualMenu(){
		return contextualMenu!=null;
	}
	
	public Boolean getShowContentMenu(){
		return contentMenu!=null;
	}
	
	public Boolean getShowMainMenu(){
		return Boolean.TRUE;
	}
	
	@Override
	public Editor<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editorInstance(Object anObjectModel,Crud crud) {
		Editor<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editor = editorInstance();
		editor.getListeners().add(this);
		editor.setWindow(this);
		configureBeforeConstruct(editor);
		((AbstractBean)editor).postConstruct();
		editor.getSubmitCommand().getCommand().getListeners().add(this);
		editor.setCrud(crud);
		editor.build(anObjectModel);
		configureAfterConstruct(editor);
		editors.add(editor);
		return editor;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <DATA> TABLE tableInstance(Class<DATA> aDataClass,UsedFor usedFor,Crud crud) {
		@SuppressWarnings("rawtypes")
		Table table = tableInstance();
		table.setUsedFor(usedFor);
		table.setCrud(crud);
		table.setWindow(this);
		configureBeforeConstruct(table);
		((AbstractBean)table).postConstruct();
		table.build(aDataClass, TableRow.class, TableColumn.class, TableCell.class);
		configureAfterConstruct(table);
		//table.getAddRowCommand().getCommand().setMessageManager(getMessageManager());
		//table.getSaveRowCommand().setMessageManager(getMessageManager());
		//table.getDeleteRowCommand().getCommand().setMessageManager(getMessageManager());
		//table.getCancelRowCommand().setMessageManager(getMessageManager());
		
		tables.add((TABLE) table);
		return (TABLE) table;
	}
	
	/*
	@Override
	public <DATA> HierarchycalData<DATA> hierarchyInstance(Class<DATA> aNodeClass) {
		HierarchycalData<DATA> hierarchicalData = hierarchyInstance();
		hierarchicalData.setWindow(this);
		hierarchicalData.setNodeDataClass(aNodeClass);
		((AbstractBean)hierarchicalData).postConstruct();
		hierarchicalData.getAddNodeCommand().getCommand().setMessageManager(getMessageManager());
		hierarchicalDatas.add(hierarchicalData);
		return hierarchicalData;
	}*/
	
	@Override
	public EventCalendar eventCalendarInstance(Class<?> aClass) {
		EventCalendar eventCalendar = eventCalendarInstance();
		eventCalendar.setWindow(this);
		configureBeforeConstruct(eventCalendar);
		((AbstractBean)eventCalendar).postConstruct();
		configureAfterConstruct(eventCalendar);
		//eventCalendar.getAddEventCommand().getCommand().setMessageManager(getMessageManager());
		eventCalendars.add(eventCalendar);
		return eventCalendar;
	}
	
	public void onMessageDialogBoxClosed(){
		System.out.println("AbstractWindow.onMessageDialogBoxClosed()");
	}
	
	private void configureBeforeConstruct(UIWindowPart aWindowPart){
		aWindowPart.setValidationPolicy(getValidationPolicy());
	}
	
	private void configureAfterConstruct(UIWindowPart aWindowPart){
		Collection<Field> fields = commonUtils.getAllFields(aWindowPart.getClass());
		for(Field field : fields)
			if(UICommandable.class.isAssignableFrom(field.getType())){
				UICommandable commandable = ((UICommandable)commonUtils.readField(aWindowPart, field, Boolean.FALSE));
				if(commandable!=null)
					configureCommand( commandable.getCommand() );
			}else if(UICommand.class.isAssignableFrom(field.getType())){
				configureCommand( (UICommand)commonUtils.readField(aWindowPart, field, Boolean.FALSE) );
			}
	}
	
	private void configureCommand(UICommand aCommand){
		if(aCommand==null)
			return;
		aCommand.setMessageManager(getMessageManager());
	}
	
	protected UICommandable createCommandable(String labelId, IconType iconType, AbstractMethod<Object, Object> executeMethod, EventListener anExecutionPhase, ProcessGroup aProcessGroup){
		UICommandable commandable = UIManager.getInstance().createCommandable(labelId, iconType, executeMethod, anExecutionPhase, aProcessGroup);
		commandable.getCommand().setMessageManager(getMessageManager());
		return commandable;
	}
	
	/* Listeners */
	
	@Override
	public void editorInputsCreated(Editor<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editor, EditorInputs<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editorInputs) {
		editorInputs.getListeners().add(this);
	}
	
	@Override
	public void componentsDiscovered(EditorInputs<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editorInputs,Collection<UIInputOutputComponent<?>> components) {
		
	}
	  
	// Command
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		System.out.println("AbstractWindow.transfer()");
	}
	
	@Override
	public Boolean validate(UICommand command, Object parameter) {
		System.out.println("AbstractWindow.validate()");
		return null;
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		System.out.println("AbstractWindow.execute()");
	}
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		System.out.println("AbstractWindow.success()");
		return null;
	}
	
	@Override
	public Object fail(UICommand command, Object parameter, Throwable throwable) {
		System.out.println("AbstractWindow.fail()");
		return null;
	}
	
}
