package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.ui.api.MenuManager.Type;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.api.model.EventCalendar;
import org.cyk.ui.api.model.table.Table;
import org.cyk.ui.api.model.table.TableCell;
import org.cyk.ui.api.model.table.TableColumn;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWindow<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM,TABLE extends Table<?>> extends AbstractBean implements UIWindow<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM,TABLE>,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	@Inject @Getter protected GenericBusiness genericBusiness;
	@Inject @Getter protected DataTreeTypeBusiness dataTreeTypeBusiness;
	@Inject @Getter protected EventBusiness eventBusiness;
	
	@Getter @Setter protected UIMenu mainMenu,contextualMenu,contentMenu;
	//@Getter protected Boolean showContentMenu = Boolean.FALSE,showContextualMenu=Boolean.TRUE;
	protected Collection<Editor<?,?,?,?>> editors = new ArrayList<>();
	protected Collection<TABLE> tables = new ArrayList<>();
	protected Collection<EventCalendar> eventCalendars = new ArrayList<>();
	//protected Collection<HierarchycalData<?>> hierarchicalDatas = new ArrayList<>();
	@Inject protected MenuManager menuManager;
	@Getter protected String title = "CYK Systems",contentTitle="Content";
	
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
		editor.setWindow(this);
		((AbstractBean)editor).postConstruct();
		editor.setCrud(crud);
		editor.build(anObjectModel);
		editors.add(editor);
		return editor;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <DATA> TABLE tableInstance(Class<DATA> aDataClass) {
		@SuppressWarnings("rawtypes")
		Table table = tableInstance();
		table.setWindow(this);
		((AbstractBean)table).postConstruct();
		table.build(aDataClass, TableRow.class, TableColumn.class, TableCell.class);
		table.getAddRowCommand().getCommand().setMessageManager(getMessageManager());
		table.getSaveRowCommand().setMessageManager(getMessageManager());
		table.getDeleteRowCommand().getCommand().setMessageManager(getMessageManager());
		table.getCancelRowCommand().setMessageManager(getMessageManager());
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
		((AbstractBean)eventCalendar).postConstruct();
		eventCalendar.getAddEventCommand().getCommand().setMessageManager(getMessageManager());
		eventCalendars.add(eventCalendar);
		return eventCalendar;
	}
	
	public void onMessageDialogBoxClosed(){
		System.out.println("AbstractWindow.onMessageDialogBoxClosed()");
	}
		
}
