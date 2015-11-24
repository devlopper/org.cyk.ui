package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface BusinessEntityPrimefacesPageListener<ENTITY extends AbstractIdentifiable> extends PrimefacesPageListener {

	Class<ENTITY> getEntityTypeClass();
	
	/**/
	
	public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends PrimefacesPageListener.Adapter implements BusinessEntityPrimefacesPageListener<ENTITY_TYPE>,Serializable {
		private static final long serialVersionUID = -7944074776241690783L;

		@Getter protected Class<ENTITY_TYPE> entityTypeClass;
		
		public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
			super();
			this.entityTypeClass = entityTypeClass;
		}
	}

	
}
