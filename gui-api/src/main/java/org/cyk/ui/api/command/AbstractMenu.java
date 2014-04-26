package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractMenu extends AbstractBean implements UIMenu,Serializable {

	private static final long serialVersionUID = 101308477938178448L;

	protected Collection<UICommand> commands = new ArrayList<UICommand>(){
		private static final long serialVersionUID = -5378067672438543808L;
		public boolean add(UICommand aCommand){
			aCommand.setIdentifier(RandomStringUtils.randomAlphabetic(4));
			return super.add(aCommand);
		}
	};
	
	@Override
	public UICommand command(String anIdentifier) {
		for(UICommand command : commands)
			if(command.getIdentifier().equals(anIdentifier))
				return command;
		throw new IllegalArgumentException("No such command <"+anIdentifier+"> exist");
	}
}
