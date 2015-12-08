package org.cyk.ui.api;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;

public interface SelectItemBuildListener<TYPE> {

	String label(TYPE object);
	Boolean nullable();
	String nullLabel();
	Collection<TYPE> collection(Class<TYPE> aClass);
	
	/**/
	
	public static class Adapter<TYPE> implements SelectItemBuildListener<TYPE>,Serializable {

		private static final long serialVersionUID = -715019422940050534L;

		@Override
		public String label(TYPE object) {
			return null;
		}

		@Override
		public Boolean nullable() {
			return null;
		}

		@Override
		public String nullLabel() {
			return null;
		}

		@Override
		public Collection<TYPE> collection(Class<TYPE> aClass) {
			return null;
		}
		
		/**/
		
		public static class Default<TYPE> extends Adapter<TYPE> implements Serializable {

			private static final long serialVersionUID = -715019422940050534L;

			@Override
			public String label(TYPE object) {
				return RootBusinessLayer.getInstance().getFormatterBusiness().format(object);
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


	}

	
}
