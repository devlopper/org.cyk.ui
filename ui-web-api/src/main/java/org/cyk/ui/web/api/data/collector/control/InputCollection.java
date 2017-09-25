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
	
	public InputCollection(Class<T> elementClass,Class<?> sourceObjectClass) {
		super(elementClass,SelectItem.class,sourceObjectClass);
		getCollection().addListener(new CollectionAdapter<T>());
		getCollection().setGetGetSourceObjectMethodName(GET_VALUE);
	}
	
	public InputCollection(Class<T> elementClass) {
		this(elementClass,null);
	}
	
	@Override
	protected Object getSelectItemValue(SelectItem selectItem) {
		return selectItem.getValue();
	}
	
	public static class CollectionAdapter<T> extends CollectionHelper.Instance.Listener.Adapter<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	private static final String GET_VALUE = "getValue";
	
}
