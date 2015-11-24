package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface ReportPageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener<ENTITY> {

	String getUrl(String outcome,Object[] parameters);

	/**/
	
	public class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener.Adapter<ENTITY_TYPE> implements ReportPageListener<ENTITY_TYPE>,Serializable {

		private static final long serialVersionUID = -7944074776241690783L;

		public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
			super(entityTypeClass);
		}

		@Override
		public String getUrl(String outcome, Object[] parameters) {
			return null;
		}


	}
	
}
