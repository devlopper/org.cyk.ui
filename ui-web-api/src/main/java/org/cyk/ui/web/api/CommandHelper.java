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
		
		public static final String COMMAND_PROPERTY_NAME_ACTION = "COMMAND_PROPERTY_NAME_ACTION";
		public static final String COMMAND_PROPERTY_NAME_AJAX = "COMMAND_PROPERTY_NAME_AJAX";
		public static final String COMMAND_PROPERTY_NAME_ICON = "COMMAND_PROPERTY_NAME_ICON";
		public static final String COMMAND_PROPERTY_NAME_PROCESS = "COMMAND_PROPERTY_NAME_PROCESS";
		public static final String COMMAND_PROPERTY_NAME_RENDERED = "COMMAND_PROPERTY_NAME_RENDERED";
		public static final String COMMAND_PROPERTY_NAME_DISABLED = "COMMAND_PROPERTY_NAME_DISABLED";
		public static final String COMMAND_PROPERTY_NAME_TYPE = "COMMAND_PROPERTY_NAME_TYPE";
		public static final String COMMAND_PROPERTY_NAME_TITLE = "COMMAND_PROPERTY_NAME_TITLE";
		public static final String COMMAND_PROPERTY_NAME_UPDATE = "COMMAND_PROPERTY_NAME_UPDATE";
		public static final String COMMAND_PROPERTY_NAME_VALUE = "COMMAND_PROPERTY_NAME_VALUE";
		public static final String COMMAND_PROPERTY_NAME_ONCOMPLETE = "COMMAND_PROPERTY_NAME_ONCOMPLETE";
		public static final String COMMAND_PROPERTY_NAME_ONCLICK = "COMMAND_PROPERTY_NAME_ONCLICK";
		public static final String COMMAND_PROPERTY_NAME_IMMEDIATE = "COMMAND_PROPERTY_NAME_IMMEDIATE";
		public static final String COMMAND_PROPERTY_NAME_WIDGET_VAR = "COMMAND_PROPERTY_NAME_WIDGET_VAR";
		public static final String COMMAND_PROPERTY_NAME_STYLE_CLASS = "COMMAND_PROPERTY_NAME_STYLE_CLASS";
		public static final String COMMAND_PROPERTY_NAME_STYLE = "COMMAND_PROPERTY_NAME_STYLE";
		
	}

	
}
