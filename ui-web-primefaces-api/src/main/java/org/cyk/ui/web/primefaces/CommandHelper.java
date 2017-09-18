package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class CommandHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true)
	public static class Command extends org.cyk.ui.web.api.CommandHelper.Command implements Serializable {
		private static final long serialVersionUID = 1L;

	
	}

	
}
