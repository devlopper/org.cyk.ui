package org.cyk.ui.web.api.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.utility.common.helper.CollectionHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputCollection<T> extends org.cyk.ui.api.data.collector.control.InputCollection<T,SelectItem> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	/**/
	
	public InputCollection(String name,Class<T> elementClass,Class<?> elementObjectClass,Class<?> sourceObjectClass) {
		super(elementClass,elementObjectClass,SelectItem.class,sourceObjectClass);
		getCollection().setName(name).setGetSourceObjectMethodName(GET_VALUE);
	}
	
	public InputCollection(String name,Class<T> elementClass,Class<?> elementObjectClass) {
		this(name,elementClass,elementObjectClass,null);
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.ui.api.data.collector.control.InputCollection.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	public static class CollectionAdapter<T> extends CollectionHelper.Instance.Listener.Adapter<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	private static final String GET_VALUE = "getValue";
	
}
