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

	protected Collection<UICommandable> commandables = new ArrayList<UICommandable>(){
		private static final long serialVersionUID = -5378067672438543808L;
		public boolean add(UICommandable aCommandable){
			aCommandable.setIdentifier(RandomStringUtils.randomAlphabetic(4));
			return super.add(aCommandable);
		}
	};
	
	@Override
	public UICommandable commandable(String anIdentifier) {
		for(UICommandable commandable : commandables)
			if(commandable.getIdentifier().equals(anIdentifier))
				return commandable;
		throw new IllegalArgumentException("No such commandable <"+anIdentifier+"> exist");
	}
	
	public void addCommandable(UICommandable commandable) {
		commandables.add(commandable);
	}
}
