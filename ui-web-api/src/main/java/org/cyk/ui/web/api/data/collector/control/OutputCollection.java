 package org.cyk.ui.web.api.data.collector.control;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputCollection<T> extends org.cyk.ui.api.data.collector.control.OutputCollection<T> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<T> elementClass,Class<?> elementObjectClass,Class<IDENTIFIABLE> identifiableClass,String[] elementObjectClassFieldNames,Collection<IDENTIFIABLE> identifiables) {
		super(elementClass,elementObjectClass,identifiableClass,elementObjectClassFieldNames,identifiables);
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<T> elementClass,Class<?> elementObjectClass,Class<IDENTIFIABLE> identifiableClass,String[] elementObjectClassFieldNames) {
		this(elementClass,elementObjectClass,identifiableClass,elementObjectClassFieldNames,null);
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.ui.api.data.collector.control.OutputCollection.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
	
		public static class Enumeration<T> extends org.cyk.ui.api.data.collector.control.OutputCollection.Element<T> implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}
		
	}
	
	/**/
	
	public static interface Listener<T> {
		
		public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;

		}
		
	}
}
