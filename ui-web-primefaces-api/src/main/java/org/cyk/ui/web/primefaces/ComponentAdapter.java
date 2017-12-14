package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.resources.LazyDataModel;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.DataTable;

public class ComponentAdapter extends org.cyk.ui.web.primefaces.resources.ComponentAdapter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object build(final Component component) {
		Object object = super.build(component);
		if(component instanceof DataTable){
			if(Boolean.TRUE.equals(component.getPropertiesMap().getLazy())){
				component.getPropertiesMap().setValue(new LazyDataModel<AbstractIdentifiable>(component){
					private static final long serialVersionUID = 1L;
					
					/*@Override
					protected List<AbstractIdentifiable> __getInstances__(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
						Class<AbstractIdentifiable> aClass = (Class<AbstractIdentifiable>) component.getPropertiesMap().getActionOnClass();
						FilterHelper.Filter<AbstractIdentifiable> filter = (Filter<AbstractIdentifiable>) ClassHelper.getInstance().instanciateOne(FilterClassLocator.getInstance().locate(aClass));
						filter.set((String)filters.get("globalFilter"));
						DataReadConfiguration dataReadConfiguration = new DataReadConfiguration(new Long(first), new Long(pageSize));
						return (List<AbstractIdentifiable>) inject(BusinessInterfaceLocator.class).injectTyped(aClass).findByFilter(filter, dataReadConfiguration);
					}*/
					
					/*@Override
					protected Integer __count__(Collection<AbstractIdentifiable> instances, int first, int pageSize,String sortField, SortOrder sortOrder, Map<String, Object> filters) {
						Class<AbstractIdentifiable> aClass = (Class<AbstractIdentifiable>) component.getPropertiesMap().getActionOnClass();
						FilterHelper.Filter<AbstractIdentifiable> filter = (Filter<AbstractIdentifiable>) ClassHelper.getInstance().instanciateOne(FilterClassLocator.getInstance().locate(aClass));
						filter.set((String)filters.get("globalFilter"));
						DataReadConfiguration dataReadConfiguration = new DataReadConfiguration(new Long(first), new Long(pageSize));
						return inject(BusinessInterfaceLocator.class).injectTyped(aClass).countByFilter(filter, dataReadConfiguration).intValue();
					}*/
					
				});
			}
		}
		return object;
	}
	
}
