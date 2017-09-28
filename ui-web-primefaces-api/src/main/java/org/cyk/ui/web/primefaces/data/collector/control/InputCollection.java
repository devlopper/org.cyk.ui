package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import org.cyk.ui.api.Constant;
import org.cyk.utility.common.helper.JQueryHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputCollection<T> extends org.cyk.ui.web.api.data.collector.control.InputCollection<T> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	private String mobileNodes,model,ajaxListener;
	
	/**/
	
	public InputCollection(String name,Class<T> elementClass,Class<?> elementObjectClass,Class<?> sourceObjectClass) {
		super(name,elementClass,elementObjectClass,sourceObjectClass);
		//in order to trigger update we need to use a unique css class to identify input
		getPropertiesMap().addString(Constant.STYLE_CLASS,org.cyk.utility.common.Constant.CHARACTER_SPACE.toString(), identifier);
		
		getAddCommand().setProperty(Constant.UPDATE, JQueryHelper.getInstance().getSelectByClass(identifier));
		getDeleteCommand().setProperty(Constant.UPDATE, JQueryHelper.getInstance().getSelectByClass(identifier));
		getIndexColumn().setWidth("25");
		getIndexColumn().addFooterCommand(getAddCommand());
		getCommandsColumn().setWidth("30");
	}
	
	public InputCollection(String name,Class<T> elementClass,Class<?> elementObjectClass) {
		this(name,elementClass,elementObjectClass,(Class<?>)null);
	}
	
	
	
	/**/
	
	public static class CollectionAdapter<T> extends org.cyk.ui.web.api.data.collector.control.InputCollection.CollectionAdapter<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
