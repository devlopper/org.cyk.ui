package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.DataTable;

public class ComponentAdapter extends org.cyk.ui.web.primefaces.resources.ComponentAdapter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object build(final Component component) {
		Object object = super.build(component);
		if(component instanceof DataTable){
			if(Boolean.TRUE.equals(component.getPropertiesMap().getLazy())){
				component.getPropertiesMap().setValue(new LazyDataModel<AbstractIdentifiable>(component));
			}
		}
		return object;
	}
	
}
