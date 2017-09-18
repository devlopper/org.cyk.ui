package org.cyk.ui.api.command;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CommandHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Command extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private CommandHelper.Command instance;
	
}
