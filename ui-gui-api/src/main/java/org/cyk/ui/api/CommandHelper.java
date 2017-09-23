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
		
	}

	
}
