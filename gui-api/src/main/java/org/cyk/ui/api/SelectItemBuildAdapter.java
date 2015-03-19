package org.cyk.ui.api;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;

public class SelectItemBuildAdapter<TYPE> implements SelectItemBuildListener<TYPE>,Serializable {

	private static final long serialVersionUID = -715019422940050534L;

	@Override
	public String label(TYPE object) {
		return object.toString();
	}

	@Override
	public Boolean nullable() {
		return Boolean.TRUE;
	}

	@Override
	public String nullLabel() {
		return UIManager.getInstance().text("input.choice.select.message");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<TYPE> collection(Class<TYPE> aClass) {
		if(AbstractIdentifiable.class.isAssignableFrom(aClass))
			return (Collection<TYPE>) UIManager.getInstance().getGenericBusiness().use((Class<? extends AbstractIdentifiable>) aClass).find().all();
		return null;
	}
	
	

}
