package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.web.primefaces.resources.LazyDataModel;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.window.ListWindow;

@Named @ViewScoped @Getter @Setter
public class IdentifiableListPage extends org.cyk.ui.web.api.resources.page.IdentifiableListPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends org.cyk.utility.common.userinterface.collection.DataTable> getDataTableClass() {
		Class<? extends org.cyk.utility.common.userinterface.collection.DataTable> clazz = super.getDataTableClass();
		if(org.cyk.utility.common.userinterface.collection.DataTable.class.equals(clazz))
			clazz = DataTable.class;
		return clazz;
	}

	@Override
	protected org.cyk.utility.common.userinterface.collection.DataTable buildDataTable() {
		org.cyk.utility.common.userinterface.collection.DataTable dataTable = super.buildDataTable();
		dataTable.getPropertiesMap().setValue(new LazyDataModel<Object>(dataTable){
			private static final long serialVersionUID = 1L;
			
			@Override
			protected Boolean isFilterable(Map<String, Object> filters) {
				return Boolean.TRUE;
			}
			
			@Override
			protected List<Object> filter(List<Object> collection, Map<String, Object> filters) {
				return null;//Person.filter(collection, filters);
			}
			
			@Override
			protected Boolean isPageable(Integer first, Integer size) {
				return Boolean.TRUE;
			}
		});
		return dataTable;
	}
	
	@Override
	protected Class<? extends org.cyk.utility.common.userinterface.hierarchy.Hierarchy> getHierarchyClass() {
		Class<? extends org.cyk.utility.common.userinterface.hierarchy.Hierarchy> clazz = super.getHierarchyClass();
		if(org.cyk.utility.common.userinterface.hierarchy.Hierarchy.class.equals(clazz))
			clazz = Hierarchy.class;
		return clazz;
	}
	
	public static class DataTable extends ListWindow.DataTable implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void __prepare__() {
			super.__prepare__();
			addColumnsByFieldNames("globalIdentifier.code","globalIdentifier.name");
			if(ClassHelper.getInstance().isHierarchy((Class<?>) getPropertiesMap().getActionOnClass()))
				addColumnsByFieldNames("parent");
			if(ClassHelper.getInstance().isTyped((Class<?>) getPropertiesMap().getActionOnClass()))
				addColumnsByFieldNames("type");
			
		}
		
	}
	
	public static class Hierarchy extends ListWindow.Hierarchy implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void __prepare__() {
			super.__prepare__();
			addColumnsByFieldNames("globalIdentifier.code","globalIdentifier.name");
			addColumnsByFieldNames("parent");
			if(ClassHelper.getInstance().isTyped(getActionOnClass()))
				addColumnsByFieldNames("type");
		}
		
	}
}
