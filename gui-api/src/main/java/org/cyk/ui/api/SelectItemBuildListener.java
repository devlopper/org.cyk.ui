package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;

public interface SelectItemBuildListener {

	SelectItemBuildListener DEFAULT = new SelectItemBuildListener.Adapter.Default();
	
	String label(Object object);
	Boolean nullable();
	String nullLabel();
	Collection<Object> collection(Class<?> aClass);
	void built(Class<?> aClass,Object item);
	
	/**/
	
	public static class Adapter implements SelectItemBuildListener,Serializable {

		private static final long serialVersionUID = -715019422940050534L;

		@Override
		public String label(Object object) {
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
		public Collection<Object> collection(Class<?> aClass) {
			return null;
		}
		
		@Override
		public void built(Class<?> aClass,Object item) {}
		
		/**/
		
		public static class Default extends Adapter implements Serializable {

			private static final long serialVersionUID = -715019422940050534L;

			@Override
			public String label(Object object) {
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
			public Collection<Object> collection(Class<?> aClass) {
				Collection<Object> collection = new ArrayList<>();
				if(AbstractIdentifiable.class.isAssignableFrom(aClass))
					for(AbstractIdentifiable identifiable : UIManager.getInstance().getGenericBusiness().use((Class<? extends AbstractIdentifiable>) aClass).find().all())
						collection.add(identifiable);
				return collection;
			}
		}


	}

	
}
