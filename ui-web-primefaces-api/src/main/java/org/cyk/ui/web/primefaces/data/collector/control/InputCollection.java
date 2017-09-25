package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import org.cyk.ui.api.Constant;
import org.cyk.utility.common.helper.JQueryHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputCollection<T> extends org.cyk.ui.web.api.data.collector.control.InputCollection<T> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	/**/
	
	public InputCollection(Class<T> elementClass,Class<?> sourceObjectClass) {
		super(elementClass,sourceObjectClass);
		//in order to trigger update we need to use a unique css class to identify input
		getPropertiesMap().addString(Constant.STYLE_CLASS,org.cyk.utility.common.Constant.CHARACTER_SPACE.toString(), identifier);
		
		getAddCommand().setProperty(Constant.UPDATE, JQueryHelper.getInstance().getSelectByClass(identifier));
		getDeleteCommand().setProperty(Constant.UPDATE, JQueryHelper.getInstance().getSelectByClass(identifier));
		getIndexColumn().setWidth("10");
		getIndexColumn().addFooterCommand(getAddCommand());
	}
	
	public InputCollection(Class<T> elementClass) {
		this(elementClass,(Class<?>)null);
	}
	
	/**/
	
	public static class CollectionAdapter<T> extends org.cyk.ui.web.api.data.collector.control.InputCollection.CollectionAdapter<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
