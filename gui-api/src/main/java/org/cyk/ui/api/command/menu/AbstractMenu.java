package org.cyk.ui.api.command.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
	protected UICommandable requestedCommandable;
	
	protected Collection<UICommandable> commandables = new ArrayList<UICommandable>(){
		private static final long serialVersionUID = -5378067672438543808L;
		public boolean add(UICommandable aCommandable){
			if(StringUtils.isBlank(aCommandable.getIdentifier())){
				aCommandable.setIdentifier(RandomStringUtils.randomAlphabetic(4));
				aCommandable.setIndex(size());
			}
			return super.add(aCommandable);
		}
	};
	
	public Integer getIndex(String anIdentifier) {
		Integer index = 0;
		for(UICommandable commandable : commandables)
			if(commandable.getIdentifier().equals(anIdentifier))
				break;
			else
				index++;
		return index;
	}
	
	@Override
	public UICommandable commandable(String anIdentifier) {
		for(UICommandable commandable : commandables)
			if(commandable.getIdentifier().equals(anIdentifier))
				return commandable;
		throw new IllegalArgumentException("No such commandable <"+anIdentifier+"> exist");
	}
	
	@Override
	public UICommandable remove(String anIdentifier) {
		List<UICommandable> list = (List<UICommandable>) commandables;
		UICommandable commandable = __remove__(anIdentifier, list);
		if(commandable==null){
			for(int i=0;i<list.size() && commandable==null;i++)
				commandable = __remove__(anIdentifier, (List<UICommandable>) list.get(i).getChildren());
		}	
		return commandable;
	}
	
	public static UICommandable __remove__(String anIdentifier,List<UICommandable> commandables) {
		if(commandables==null)
			return null;
		for(int i=0;i<commandables.size();i++){
			if(commandables.get(i).getIdentifier().equals(anIdentifier))
				return commandables.remove(i);
		}
		return null;
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
		if(commandable==null)
			return null;
		/*if( (commandable.getViewId()==null || commandable.getViewType()==null) && commandable.getChildren().isEmpty() )
			;
		else*/
			commandables.add(commandable);
		return commandable;
	}
	
	@Override
	public void setRequestedCommandable(String identifier) {
		UICommandable commandable = StringUtils.isBlank(identifier)?null:commandable(identifier);
		if(commandable==null)
			commandable = commandables.iterator().next();
		setRequestedCommandable(commandable);	
	}
	@Override
	public void setRequestedCommandable(UICommandable commandable) {
		requestedCommandable = commandable;
	}
}
