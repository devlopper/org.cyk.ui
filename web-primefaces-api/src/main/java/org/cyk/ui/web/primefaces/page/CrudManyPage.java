package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.model.table.TableAdapter;

@Named
@ViewScoped
@Getter
@Setter
public class CrudManyPage extends AbstractDynamicBusinessEntityPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private Table<AbstractIdentifiable> table;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		table = (Table<AbstractIdentifiable>) createTable(businessEntityInfos.getClazz());
		table.getTableListeners().add(new TableAdapter<Row<AbstractIdentifiable>, Column, AbstractIdentifiable, String, Cell, String>(){
			@Override
			public Boolean ignore(Field field) {
				Input input = field.getAnnotation(Input.class);
				return input == null;
			}
		});
		
		table.addColumnFromDataClass();
		
		table.setEditable(true);
		table.setMaster(identifiable);
		table.fetchData();
		
		/*
		table.getSaveRowCommand().getCommand().setAfterFailureMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -4698491663673906259L;
			@Override
			protected Object __execute__(Object parameter) {
				messageDialogOkButtonOnClick="clickEditButtonRow('"+table.getUpdateStyleClass()+"','"+(table.getLastEditedRowIndex()+1)+"');";
				return null;
			}
		});*/
		
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE.equals(table.getShowHierarchy());
	}

}
