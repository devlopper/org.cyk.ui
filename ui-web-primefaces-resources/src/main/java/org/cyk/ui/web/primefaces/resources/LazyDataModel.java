package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.cyk.utility.common.helper.ThrowableHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.primefaces.model.SortOrder;

public class LazyDataModel<T> extends org.primefaces.model.LazyDataModel<DataTable.Row> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Component component;
	
	public LazyDataModel(Component component) {
		this.component = component;
	}
	
	protected List<T> __load__(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters){
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
	protected Integer __count__(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters){
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
	@Override
	public List<DataTable.Row> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters) {
		List<T> instances = (List<T>) __load__(first, pageSize, sortField, sortOrder, filters);
		setRowCount(__count__(first, pageSize, sortField, sortOrder, filters));
		return (List<DataTable.Row>) DataTable.Row.instanciateMany(instances,component,null);
	}

}