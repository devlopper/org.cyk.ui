package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.RandomHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class CommandHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true)
	public static class Command extends org.cyk.ui.web.api.CommandHelper.Command implements Serializable {
		private static final long serialVersionUID = 1L;

		private String blockUiWidgetVar = RandomHelper.getInstance().getAlphabetic(5)+System.currentTimeMillis();
		
		public Command() {
			MarkupLanguageHelper.setAjax(this, Boolean.TRUE);
			MarkupLanguageHelper.setGlobal(this, Boolean.TRUE);
		}
	
	}

	@Getter @Setter @Accessors(chain=true)
	public static class Commands extends org.cyk.ui.web.api.CommandHelper.Commands implements Serializable {
		private static final long serialVersionUID = 1L;
		
		
	}
}
