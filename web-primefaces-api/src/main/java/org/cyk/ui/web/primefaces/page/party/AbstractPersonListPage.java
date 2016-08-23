package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPersonListPage<PERSON extends AbstractIdentifiable> extends AbstractPartyListPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
	/*public static abstract class AbstractPersonListPageAdapter<PERSON extends AbstractIdentifiable> extends AbstractPartyListPageAdapter<PERSON> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractPersonListPageAdapter(Class<PERSON> entityTypeClass) {
			super(entityTypeClass);
			FormConfiguration configuration = getFormConfiguration(Crud.READ, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addFieldNames(AbstractPersonDetails.FIELD_LASTNAMES);	
		}
		
		
		public static abstract class AbstractDefault<PERSON extends AbstractIdentifiable> extends AbstractPersonListPageAdapter<PERSON> implements Serializable{

			private static final long serialVersionUID = -2256223077759190879L;

			public AbstractDefault(Class<PERSON> entityTypeClass) {
				super(entityTypeClass);
			}
			
			
			public static class Default<PERSON extends AbstractIdentifiable> extends AbstractDefault<PERSON> implements Serializable{

				private static final long serialVersionUID = -2256223077759190879L;

				public Default(Class<PERSON> entityTypeClass) {
					super(entityTypeClass);
				}
				
			}
		}
		
		
		
	}*/
}
