package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class CommandHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true)
	public static class Command extends org.cyk.ui.api.CommandHelper.Command implements Serializable {
		private static final long serialVersionUID = 1L;
		
		/**/
		
	}

	@Getter @Setter @Accessors(chain=true)
	public static class Commands extends org.cyk.ui.api.CommandHelper.Commands implements Serializable {
		private static final long serialVersionUID = 1L;
		
		
	}
	
}
