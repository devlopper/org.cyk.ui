package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import org.cyk.ui.web.primefaces.CommandHelper.Command;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputCollection<T> extends org.cyk.ui.web.api.data.collector.control.InputCollection<T> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	/**/
	
	public InputCollection(Class<T> elementClass) {
		super(elementClass);
		//in order to trigger update we need to use a unique css class to identify input
		getAddCommand().setProperty(Command.COMMAND_PROPERTY_NAME_STYLE_CLASS, getAddCommand().getProperty(Command.COMMAND_PROPERTY_NAME_STYLE_CLASS)+" "+identifier);
		getAddCommand().setProperty(Command.COMMAND_PROPERTY_NAME_UPDATE, "@(."+identifier+")");
		getDeleteCommand().setProperty(Command.COMMAND_PROPERTY_NAME_UPDATE, "@(."+identifier+")");
		getIndexColumn().setWidth("10");
		getIndexColumn().addFooterCommand(getAddCommand());
	}
	
	/**/
	
	
}
