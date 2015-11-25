package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface ConsultPageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener<ENTITY> {

	/**/
	
	public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener.Adapter<ENTITY_TYPE> implements ConsultPageListener<ENTITY_TYPE>,Serializable {

		private static final long serialVersionUID = -7944074776241690783L;

		public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
			super(entityTypeClass);
		}

		/**/
		
		public static class Default<ENTITY extends AbstractIdentifiable> extends ConsultPageListener.Adapter<ENTITY> implements Serializable {

			private static final long serialVersionUID = -4255109770974601234L;

			public Default(Class<ENTITY> entityTypeClass) {
				super(entityTypeClass);
			}
				
		}
	}
	
}
