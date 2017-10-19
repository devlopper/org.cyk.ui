package org.cyk.ui.api;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

public class GridHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Grid<T,SELECT_ITEM> extends org.cyk.utility.common.helper.GridHelper.Grid<T,SELECT_ITEM> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public Grid(Class<T> elementClass) {
			super(elementClass);
		}
		
		public static class Column<SELECT_ITEM> extends org.cyk.utility.common.helper.GridHelper.Grid.Column<SELECT_ITEM> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			
		}
		
	}
}
