package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

public class GridHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Grid<T> extends org.cyk.ui.api.GridHelper.Grid<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public Grid(Class<T> elementClass) {
			super(elementClass);
		}
		
		public static class Column extends org.cyk.ui.api.GridHelper.Grid.Column implements Serializable {
			private static final long serialVersionUID = 1L;
			
			
			
		}
		
	}
}