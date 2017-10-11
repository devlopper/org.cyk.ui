package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputCollection<T> extends org.cyk.ui.web.api.data.collector.control.InputCollection<T> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	private String mobileNodes,model,ajaxListener;
	
	/**/
	
	public InputCollection(String name,Class<T> elementClass,Class<?> elementObjectClass,Class<?> sourceObjectClass) {
		super(name,elementClass,elementObjectClass,sourceObjectClass);
	}
	
	public InputCollection(String name,Class<T> elementClass,Class<?> elementObjectClass) {
		this(name,elementClass,elementObjectClass,(Class<?>)null);
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.ui.web.api.data.collector.control.InputCollection.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	/**/
	
	public static class CollectionAdapter<T> extends org.cyk.ui.web.api.data.collector.control.InputCollection.CollectionAdapter<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
