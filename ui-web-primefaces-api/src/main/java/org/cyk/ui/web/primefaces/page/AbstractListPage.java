package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.data.collector.control.OutputCollection;
import org.cyk.utility.common.helper.ClassHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractListPage<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 2366143822395124500L;

	protected OutputCollection<IDENTIFIABLE> collection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		@SuppressWarnings("unchecked")
		Class<IDENTIFIABLE> identifiableClass = (Class<IDENTIFIABLE>) ClassHelper.getInstance().getParameterAt(getClass(), 0, AbstractIdentifiable.class);
		collection = new OutputCollection<IDENTIFIABLE>(identifiableClass);
	}
	
}
