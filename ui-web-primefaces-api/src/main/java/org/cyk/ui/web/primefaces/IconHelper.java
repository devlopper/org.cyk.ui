package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

public class IconHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Mapping extends org.cyk.ui.web.api.IconHelper.Mapping implements Serializable {
		private static final long serialVersionUID = 1L;
	
		public static class FontAwesome extends org.cyk.ui.web.api.IconHelper.Mapping.FontAwesome implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}
	}
}