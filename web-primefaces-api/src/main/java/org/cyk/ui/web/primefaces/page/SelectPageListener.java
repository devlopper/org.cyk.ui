package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.Setter;

public interface SelectPageListener<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener<ENTITY> {

	/**/
	
	ENTITY findByIdentifier(IDENTIFIER_TYPE identifier);
	
	Type getType();
	void setType(Type type);
	
	@Getter @Setter
	public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener.Adapter<ENTITY_TYPE> implements SelectPageListener<ENTITY_TYPE,IDENTIFIER_TYPE>,Serializable {

		private static final long serialVersionUID = -7944074776241690783L;

		protected Type type = Type.IDENTIFIABLE;
		
		public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
			super(entityTypeClass);
		}

		@Override
		public ENTITY_TYPE findByIdentifier(IDENTIFIER_TYPE identifier) {
			return null;
		}
		
		/**/
		
		public static class Default<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends SelectPageListener.Adapter<ENTITY,IDENTIFIER_TYPE> implements Serializable {

			private static final long serialVersionUID = -4255109770974601234L;

			public Default(Class<ENTITY> entityTypeClass) {
				super(entityTypeClass);
			}
				
		}
	}
	
	public static enum Type{IDENTIFIER,IDENTIFIABLE}

	
	
}
