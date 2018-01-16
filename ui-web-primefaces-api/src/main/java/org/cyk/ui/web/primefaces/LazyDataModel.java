package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.Country;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Component;
import org.primefaces.model.SortOrder;

public class LazyDataModel<T> extends org.cyk.ui.web.primefaces.resources.LazyDataModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public LazyDataModel(Component component) {
		super(component);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List<T> __getInstances__(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
		List<T> instances =  super.__getInstances__(first, pageSize, sortField, sortOrder, filters);
		if(ClassHelper.getInstance().isHierarchy((Class<?>)component.getPropertiesMap().getActionOnClass())){
			CommonUtils.getInstance().inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)component.getPropertiesMap().getActionOnClass())
			.setParents((Collection<AbstractIdentifiable>)instances,1);
		}else if(Country.class.equals(component.getPropertiesMap().getActionOnClass())){
			CommonUtils.getInstance().inject(CountryBusiness.class).setContinent((Collection<Country>)instances);
		}
		return instances;
	}
	
}
