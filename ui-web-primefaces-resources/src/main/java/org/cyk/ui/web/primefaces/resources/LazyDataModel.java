package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.cyk.utility.common.Comparator;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.primefaces.model.SortOrder;

public class LazyDataModel<T> extends org.primefaces.model.LazyDataModel<DataTable.Row> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Component component;
	
	public LazyDataModel(Component component) {
		this.component = component;
	}
	
	protected FilterHelper.Filter<T> getFilter(Class<T> aClass,Integer first, Integer pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters){
		@SuppressWarnings("unchecked")
		FilterHelper.Filter<T> filter = (Filter<T>) ClassHelper.getInstance().instanciateOne(FilterHelper.Filter.getClassLocator().locate(aClass));
		filter.set((String)filters.get("globalFilter"));
		return filter;
	}
	
	protected DataReadConfiguration getDataReadConfiguration(Class<T> aClass,Integer first, Integer pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters){
		DataReadConfiguration dataReadConfiguration = new DataReadConfiguration(new Long(first), new Long(pageSize));
		return dataReadConfiguration;
	}
	
	protected List<T> __getInstances__(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters){
		@SuppressWarnings("unchecked")
		Class<T> aClass = (Class<T>) component.getPropertiesMap().getActionOnClass();
		return  (List<T>) InstanceHelper.getInstance().get(aClass, getFilter(aClass, first, pageSize, sortField, sortOrder, filters)
				, getDataReadConfiguration(aClass, first, pageSize, sortField, sortOrder, filters));
	}
	
	protected Integer __count__(Collection<T> instances,int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters){
		@SuppressWarnings("unchecked")
		Class<T> aClass = (Class<T>) component.getPropertiesMap().getActionOnClass();
		return InstanceHelper.getInstance().count(aClass, getFilter(aClass, first, pageSize, sortField, sortOrder, filters)
				, getDataReadConfiguration(aClass, first, pageSize, sortField, sortOrder, filters)).intValue();
		//return instances.size();
	}
	
	@Override
	public List<DataTable.Row> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters) {
		List<T> instances = __getInstances__(first, pageSize, sortField, sortOrder, filters);
		
		if(Boolean.TRUE.equals(isFilterable(filters)))
			instances = filter(instances, filters);
		
		if(Boolean.TRUE.equals(isSortable(sortField)))
			instances = sort(instances, sortField, sortOrder);
		
		setRowCount(__count__(instances,first, pageSize, sortField, sortOrder, filters));
		
		if(Boolean.TRUE.equals(isPageable(first,pageSize)))
			instances = page(instances, first,pageSize);
		
		List<DataTable.Row> rows = CollectionHelper.getInstance().isEmpty(instances) ? new ArrayList<DataTable.Row>() : (List<DataTable.Row>) DataTable.Row.instanciateMany(instances,component,null).getElements();
		
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
		Integer collectionSize = CollectionHelper.getInstance().getSize(collection);
		Integer last = first + size;
		if(last > collectionSize)
			last = collectionSize;
		return collection.subList(first, last);
	}

	/**/
	
	protected static <T> Comparator<T> instanciateComparator(SortOrder sortOrder,String...fieldNames){
		return new Comparator<T>(SortOrder.ASCENDING.equals(sortOrder) ? Comparator.Order.ASCENDING : Comparator.Order.DESCENDING,fieldNames);
	}
	
	protected static <T> void sort(List<T> list,SortOrder sortOrder,String...fieldNames){
		Collections.sort(list, instanciateComparator(sortOrder,fieldNames));
	}
}