package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.cyk.utility.common.Comparator;
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
	
	protected List<T> __getInstances__(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters){
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
	protected List<T> __load__(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters){
		List<T> instances = __getInstances__(first, pageSize, sortField, sortOrder, filters);
		
		if(Boolean.TRUE.equals(isFilterable(filters)))
			instances = filter(instances, filters);
		
		if(Boolean.TRUE.equals(isSortable(sortField)))
			instances = sort(instances, sortField, sortOrder);
		
		if(Boolean.TRUE.equals(isPageable(first,pageSize)))
			instances = page(instances, first,pageSize);
		
		return instances;
	}
	
	protected Integer __count__(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters){
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
	@Override
	public List<DataTable.Row> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters) {
		List<T> instances = __load__(first, pageSize, sortField, sortOrder, filters);	
		setRowCount(__count__(first, pageSize, sortField, sortOrder, filters));
		List<DataTable.Row> rows = (List<DataTable.Row>) DataTable.Row.instanciateMany(instances,component,null);
		if("__orderNumber__".equals(sortField))
			sort(rows, sortOrder, sortField);
		return rows;
	}
	
	protected Boolean isFilterable(Map<String, Object> filters){
		return Boolean.FALSE;
	}
	
	protected List<T> filter(List<T> collection,Map<String, Object> filters){
		return collection;
	}
	
	protected Boolean isSortable(String fieldName){
		return Boolean.FALSE;
	}
	
	protected List<T> sort(List<T> collection,String sortField,SortOrder sortOrder){
		sort(collection, sortOrder,sortField);
		return collection;
	}
	
	protected Boolean isPageable(Integer first,Integer size){
		return Boolean.FALSE;
	}
	
	protected List<T> page(List<T> collection,Integer first,Integer size){
		return collection.subList(first, first+size);
	}

	/**/
	
	protected static <T> Comparator<T> instanciateComparator(SortOrder sortOrder,String...fieldNames){
		return new Comparator<T>(SortOrder.ASCENDING.equals(sortOrder) ? Comparator.Order.ASCENDING : Comparator.Order.DESCENDING,fieldNames);
	}
	
	protected static <T> void sort(List<T> list,SortOrder sortOrder,String...fieldNames){
		Collections.sort(list, instanciateComparator(sortOrder,fieldNames));
	}
}