package org.cyk.ui.api.command.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractMenu extends AbstractBean implements UIMenu,Serializable {

	private static final long serialVersionUID = 101308477938178448L;

	protected RenderType renderType = RenderType.PLAIN;
	
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
	
	@Override
	public UICommandable addCommandable(String labelId,IconType iconType) {
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelId, iconType);
		return addCommandable(commandable);
	}
	@Override
	public UICommandable addCommandable(String labelId, IconType iconType,ViewType viewType) {
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelId, iconType);
		commandable.setViewType(viewType);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		return addCommandable(commandable);
	}
	@Override
	public UICommandable addCommandable(String labelId, IconType iconType,Object viewId) {
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelId, iconType);
		commandable.setViewId(viewId);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		return addCommandable(commandable);
	}

	@Override
	public UICommandable addCommandable(UICommandable commandable) {
		/*if( (commandable.getViewId()==null || commandable.getViewType()==null) && commandable.getChildren().isEmpty() )
			;
		else*/
			commandables.add(commandable);
		return commandable;
	}
}
