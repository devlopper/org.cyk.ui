package org.cyk.ui.api.command;

import java.util.Collection;

public interface UICommandable {
	
	UICommand getCommand();
	
	void setCommand(UICommand aCommand);
	
	String getIdentifier();
	
	void setIdentifier(String anIdentifier);
	
	String getLabel();
	
	void setLabel(String label);
	
	String getIcon();
	
	void setIcon(String icon);

	String getTooltip();
	
	void setTooltip(String label);
	
	RenderType getRenderType();
	
	void setRenderType(RenderType aRenderType);
	
	CommandRequestType getCommandRequestType();
	
	void setCommandRequestType(CommandRequestType aCommandRequestType);
	
	EventListener getEventListener();
	
	void setEventListener(EventListener anEventListener);
	
	ProcessGroup getProcessGroup();
	
	void setProcessGroup(ProcessGroup aProcessGroup);
	
	Boolean getIsNavigationCommand();
	
	Collection<UICommandable> getChildren();
	
	void setChildren(Collection<UICommandable> commandables);
	
	Class<?> getDynamicClass();
	
	void setDynamicClass(Class<?> aClass);
	
	DynamicView getDynamicView();
	
	void setDynamicView(DynamicView aDynamicView);
	
	/**/
	
	public enum RenderType{BUTTON,LINK}
	
	public enum CommandRequestType{UI_VIEW,BUSINESS_PROCESSING}
	
	public enum EventListener{NONE,CLICK}
	
	public enum ProcessGroup{THIS,FORM,PARTIAL}
	
	public enum DynamicView{EDITOR,TABLE}

}
