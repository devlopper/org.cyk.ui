package org.cyk.ui.api;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.MarkupLanguageHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class CommandHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true)
	public static class Command extends org.cyk.utility.common.helper.CommandHelper.Command.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;

		public Command() {
			((MarkupLanguageHelper.Attributes)(Object)getPropertiesMap()).setRendered(Boolean.TRUE.toString());
		}
		
		@Override
		public Command setProperty(String name, Object value) {
			return (Command) super.setProperty(name, value);
		}
		
		@Override
		public Command setName(String name) {
			return (Command) super.setName(name);
		}
		
		public Command setIcon(Object icon) {
			return setProperty(Constant.ICON, icon);
		}
		
		/**/
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Commands extends org.cyk.utility.common.helper.CommandHelper.Commands implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public Commands() {
			((MarkupLanguageHelper.Attributes)(Object)getPropertiesMap()).setRendered(Boolean.TRUE.toString());
		}
		
	}

	
}
