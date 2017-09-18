package org.cyk.ui.api;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class CommandHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true)
	public static class Command extends org.cyk.utility.common.helper.CommandHelper.Command.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;

		public static final String COMMAND_PROPERTY_NAME_ICON = "COMMAND_PROPERTY_NAME_ICON";
		public static final String COMMAND_PROPERTY_NAME_LABEL = "COMMAND_PROPERTY_NAME_LABEL";
		
	}

	
}
