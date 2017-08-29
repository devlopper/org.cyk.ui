package org.cyk.ui.api.helper;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

public class SelectItemHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class OneBuilder<SELECTITEM> extends org.cyk.utility.common.helper.SelectItemHelper.Builder.One.Adapter.Default<SELECTITEM> implements Serializable{
		private static final long serialVersionUID = 1L;

		public OneBuilder(Class<SELECTITEM> outputClass) {
			super(outputClass);
		}
			
	}
}
