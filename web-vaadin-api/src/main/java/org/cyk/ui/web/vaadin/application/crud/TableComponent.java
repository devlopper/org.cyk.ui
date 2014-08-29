package org.cyk.ui.web.vaadin.application.crud;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.table.Table.UsedFor;
import org.cyk.ui.web.vaadin.AbstractCustomComponent;
import org.cyk.ui.web.vaadin.editor.TableModel;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@Getter
public class TableComponent extends AbstractCustomComponent<Panel,VerticalLayout> implements Serializable {

	private static final long serialVersionUID = 4894464840887074438L;

	private static final String TREE_WIDTH = "170px";
	
	private TableModel tableModel;
	private Class<AbstractIdentifiable> clazz;
	
	public TableComponent(Class<AbstractIdentifiable> aClass) {
		super();
		this.clazz = aClass;
		fill();
	}

	@Override
	protected void fill() {
		if(clazz==null)
			return;
		tableModel = createTableModel(clazz, UsedFor.ENTITY_INPUT, Crud.CREATE);
		GridLayout layout;
		if(tableModel.getTree()==null){
			layout = new GridLayout(1, 2);
			layout.addComponent( tableModel.getMenuBar(),0,0 );
			layout.addComponent( tableModel.getTable(),0,1 );
		}else{
			layout = new GridLayout(2, 2);
			
			//layout.setColumnExpandRatio(0, 0);
			layout.setColumnExpandRatio(1, 1);
			tableModel.getTree().setWidth(TREE_WIDTH);
			layout.addComponent(tableModel.getTree(), 0, 0, 0, 1);
			layout.addComponent(tableModel.getMenuBar(), 1, 0);
			layout.addComponent(tableModel.getTable(), 1, 1);	
		}
		
		layout.setSizeFull();
		//layout.setRowExpandRatio(0, 0);
		layout.setRowExpandRatio(1, 1);
		addComponent(layout);
		/*
		*/
	}

}
