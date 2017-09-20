package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.GridHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputCollection<T> extends GridHelper.Grid<T> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	//protected CascadeStyleSheet cascadeStyleSheet = new CascadeStyleSheet();
	
	/**/
	
	public InputCollection(Class<T> elementClass) {
		super(elementClass);
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.utility.common.helper.CollectionHelper.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	/**/
	
	public static interface Listener<T> {
		
		public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;

		}
		
	}
}
