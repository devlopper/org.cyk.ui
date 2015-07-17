package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.ui.api.UIMessageManager;
import org.cyk.ui.api.UIProvider;

public abstract class AbstractCommandable implements UICommandable , Serializable {

	private static final long serialVersionUID = 3245517653342272298L;

	@Setter protected UIMessageManager messageManager;
	@Getter @Setter protected UICommand command;
	@Getter @Setter protected BusinessEntityInfos businessEntityInfos;
	@Getter @Setter protected String identifier,label,tooltip;
	@Getter @Setter protected IconType iconType;
	@Getter @Setter protected Boolean showLabel=Boolean.TRUE,rendered=Boolean.TRUE,requested=Boolean.FALSE;
	@Getter @Setter protected Object viewId;
	@Getter @Setter protected ViewType viewType;
	@Getter @Setter protected Class<?> dynamicClass; 
	@Getter @Setter protected EventListener eventListener;
	@Getter @Setter protected RenderType renderType;
	@Getter @Setter protected ProcessGroup processGroup;
	@Getter @Setter protected CommandRequestType commandRequestType;
	@Getter @Setter protected Collection<UICommandable> children = new ArrayList<UICommandable>(){
		private static final long serialVersionUID = -5378067672438543808L;
		public boolean add(UICommandable aCommandable){
			aCommandable.setIdentifier(RandomStringUtils.randomAlphabetic(4));
			return super.add(aCommandable);
		}
	};
	
	@Getter @Setter protected Collection<Parameter> parameters = new ArrayList<>();
	
	@Override
	public Boolean getIsNavigationCommand() {
		return CommandRequestType.UI_VIEW.equals(commandRequestType);
	}
	
	@Override
	public UICommandable addChild(String labelId, IconType iconType,String viewId,Collection<Parameter> parameters) {
		UICommandable child = UIProvider.getInstance().createCommandable(labelId, iconType);
		child.setViewId(viewId);
		child.setCommandRequestType(CommandRequestType.UI_VIEW);
		if(parameters != null)
			child.getParameters().addAll(parameters);
		addChild(child);
		return child;
	}
	
	@Override
	public UICommandable addChild(String labelId, IconType iconType,ViewType viewType,Collection<Parameter> parameters) {
		UICommandable child = UIProvider.getInstance().createCommandable(labelId, iconType);
		child.setViewType(viewType);
		child.setCommandRequestType(CommandRequestType.UI_VIEW);
		if(parameters != null)
			child.getParameters().addAll(parameters);
		addChild(child);
		return child;
	}
	
	@Override
	public void addChild(UICommandable aCommandable) {
		children.add(aCommandable);
	}
	
	@Override
	public Parameter getParameter(String name) {
		for(Parameter parameter : parameters)
			if(parameter.getName().equals(name))
				return parameter;
		return null;
	}
	
	@Override
	public UICommandable addParameter(String name, Object value) {
		parameters.add(new Parameter(name, value));
		return this;
	}
	
	@Override
	public UICommandable setParameter(String name, Object value) {
		Parameter parameter = getParameter(name);
		if(parameter==null){
			parameter = new Parameter(name, value);
			addParameter(name, value);
		}else{
			parameter.setValue(value);
		}
		return this;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}
