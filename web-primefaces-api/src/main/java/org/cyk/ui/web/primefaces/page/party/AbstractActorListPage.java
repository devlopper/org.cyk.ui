package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter
public abstract class AbstractActorListPage<ACTOR extends AbstractIdentifiable> extends AbstractPersonListPage<ACTOR> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	/**/
	
	/*public static abstract class AbstractActorListPageAdapter<ACTOR extends AbstractIdentifiable> extends AbstractPersonListPageAdapter<ACTOR> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractActorListPageAdapter(Class<ACTOR> entityTypeClass) {
			super(entityTypeClass);
		}
		
		
		public static class AbstractDefault<ACTOR extends AbstractIdentifiable> extends AbstractActorListPageAdapter<ACTOR> implements Serializable{

			private static final long serialVersionUID = -2256223077759190879L;

			public AbstractDefault(Class<ACTOR> entityTypeClass) {
				super(entityTypeClass);
			}
			
			
			public static class Default<ACTOR extends AbstractIdentifiable> extends AbstractDefault<ACTOR> implements Serializable{

				private static final long serialVersionUID = -2256223077759190879L;

				public Default(Class<ACTOR> entityTypeClass) {
					super(entityTypeClass);
				}
				
			}
		}
		
	}*/
}
