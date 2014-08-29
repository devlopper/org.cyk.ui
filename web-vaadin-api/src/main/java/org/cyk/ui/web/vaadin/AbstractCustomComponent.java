package org.cyk.ui.web.vaadin;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.table.Table.UsedFor;
import org.cyk.ui.api.model.table.TableCell;
import org.cyk.ui.api.model.table.TableColumn;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.ui.web.vaadin.editor.Editor;
import org.cyk.ui.web.vaadin.editor.EditorInputs;
import org.cyk.ui.web.vaadin.editor.TableModel;
import org.cyk.utility.common.cdi.AbstractBean;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.SingleComponentContainer;

public abstract class AbstractCustomComponent<CONTAINER extends SingleComponentContainer,LAYOUT extends Layout> extends CustomComponent implements Serializable {

	private static final long serialVersionUID = -5464828835159257761L;
	
	protected CONTAINER container;
	protected LAYOUT layout;
	
	public AbstractCustomComponent() {
		container = container();
		container.setContent(layout = layout());
		container.setSizeFull();
		
		layout.setSizeFull();
		
		fill();
		setSizeFull();
		setCompositionRoot(compositionRoot());
	}
	
	@SuppressWarnings("unchecked")
	protected CONTAINER container(){
		try {
			return (((Class<CONTAINER>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0])).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected LAYOUT layout(){
		try {
			return (((Class<LAYOUT>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1])).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected Component compositionRoot(){
		return container;
	}
	
	protected abstract void fill();
	
	protected void addComponent(Component component){
		if(component!=null)
			layout.addComponent(component);
		
	}
	
	protected void setContent(Component component){
		layout.removeAllComponents();
		addComponent(component);
	}
	
	/* Short cuts */
	
	protected Editor createEditor(Object object,Crud crud){
		Editor editor = new Editor();
		editor.setCrud(crud);
		editor.postConstruct();
		editor.targetDependentInitialisation();
		
		EditorInputs editorInputs = (EditorInputs) editor.build(object);
		editorInputs.targetDependentInitialisation();
		return editor;
	}
	
	protected Editor createEditor(Object object){
		Editor editor = createEditor(object, Crud.CREATE);
		return editor;
	}
	
	@SuppressWarnings("unchecked")
	protected TableModel createTableModel(Class<AbstractIdentifiable> aClass,UsedFor usedFor,Crud crud){
		TableModel table = new TableModel();
		table.setUsedFor(usedFor);
		table.setCrud(crud);
		
		/*
		if(AbstractDataTreeNode.class.isAssignableFrom(aClass)){
			table.setShowHierarchy(Boolean.TRUE);
			table.setHierarchyData(hierarchyData);
		}*/
		//table.setWindow(this);
		//configureBeforeConstruct(table);
		((AbstractBean)table).postConstruct();
		table.build(aClass, (Class<? extends TableRow<AbstractIdentifiable>>) TableRow.class, TableColumn.class, TableCell.class);
		table.fetchData();
		//configureAfterConstruct(table);
		
		//if(UsedFor.ENTITY_INPUT.equals(usedFor))
		//	table.addRow(UIManager.getInstance().getGenericBusiness().use(aClass).find().all());
		
		table.targetDependentInitialisation();
		return table;
	}
	
	/**/
	
	
}
