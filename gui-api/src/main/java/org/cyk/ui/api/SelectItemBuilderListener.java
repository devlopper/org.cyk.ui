package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;

public interface SelectItemBuilderListener {

	String NULL_LABEL_ID = "input.choice.select.message";
	
	SelectItemBuilderListener DEFAULT = new SelectItemBuilderListener.Adapter.Default();
	
	String getLabel(Object object);
	Boolean getNullable();
	String getNullLabel();
	Collection<Object> getCollection(Class<?> aClass);
	void built(Class<?> aClass,Object item);
	
	/**/
	
	public static class Adapter implements SelectItemBuilderListener,Serializable {

		private static final long serialVersionUID = -715019422940050534L;

		@Override
		public String getLabel(Object object) {
			return null;
		}

		@Override
		public Boolean getNullable() {
			return null;
		}

		@Override
		public String getNullLabel() {
			return null;
		}

		@Override
		public Collection<Object> getCollection(Class<?> aClass) {
			return null;
		}
		
		@Override
		public void built(Class<?> aClass,Object item) {}
		
		/**/
		
		public static class Default extends Adapter implements Serializable {

			private static final long serialVersionUID = -715019422940050534L;

			@Override
			public String getLabel(Object object) {
				return RootBusinessLayer.getInstance().getFormatterBusiness().format(object);
			}

			@Override
			public Boolean getNullable() {
				return Boolean.TRUE;
			}

			@Override
			public String getNullLabel() {
				return UIManager.getInstance().text(NULL_LABEL_ID);
			}

			@SuppressWarnings("unchecked")
			@Override
			public Collection<Object> getCollection(Class<?> aClass) {
				Collection<Object> collection = new ArrayList<>();
				if(AbstractIdentifiable.class.isAssignableFrom(aClass))
					for(AbstractIdentifiable identifiable : UIManager.getInstance().getGenericBusiness().use((Class<? extends AbstractIdentifiable>) aClass).find().all())
						collection.add(identifiable);
				return collection;
			}
		}
	}

	
}
