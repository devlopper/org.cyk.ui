package org.cyk.ui.api.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindTextResult;
import org.cyk.system.root.business.api.userinterface.style.CascadeStyleSheetBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.MessageManager;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.utility.common.AbstractBuilder;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractCommandableBuilder<COMMANDABLE extends AbstractCommandable> extends AbstractBuilder<COMMANDABLE> {
	private static final long serialVersionUID = 6858556884238933364L;

	private FindDoSomethingTextParameters labelParameters;
	private CommonBusinessAction commonBusinessAction;
	private String actionIdentifier,selectedTabId;
	private Class<? extends AbstractIdentifiable> identifiableClass;
	private Boolean one,useUrlOnClick;
	private AbstractIdentifiable master;
	private FindTextResult findTextResult;
	
	@Override
	public COMMANDABLE build() {
		if(instance.getCommand()==null)
			instance.setCommand(new DefaultCommand());
		
		if(instance.getCommand().getMessageManager()==null)
			instance.getCommand().setMessageManager(MessageManager.INSTANCE);
		
		if(instance.getViewId()==null && instance.getViewType()==null){
			setView(UIManager.getInstance().getViewIdentifier(identifiableClass, commonBusinessAction,one));
			if(instance.getViewId()==null)//No specific view has been defined so we'll use the dynamic one
				if(Boolean.TRUE.equals(one))
					setView(ViewType.DYNAMIC_CRUD_ONE);
				else
					setView(ViewType.DYNAMIC_CRUD_MANY);
				//setView(UIManager.getInstance().getViewDynamic(commonBusinessAction,one));
		}
		if(instance.getViewId() != null || instance.getViewType()!=null)
			instance.setCommandRequestType(CommandRequestType.UI_VIEW);
		
		if(StringUtils.isBlank(instance.getLabel()))
			if(StringUtils.isBlank(actionIdentifier))
				setLabelParameters(labelParameters = FindDoSomethingTextParameters.create(commonBusinessAction, identifiableClass));
			else{
				findTextResult = inject(LanguageBusiness.class).findActionIdentifierText(actionIdentifier, instance.getBusinessEntityInfos(), Boolean.TRUE);					
				setLabel(findTextResult.getValue());
			}
		//setLabelParameters(labelParameters = FindDoSomethingTextParameters.create(actionIdentifier, identifiableClass));
		
		if(StringUtils.isBlank(instance.getLabel()) && labelParameters!=null){
			findTextResult = inject(LanguageBusiness.class).findDoSomethingText(labelParameters);
			instance.setLabel(findTextResult.getValue());
		}
		
		if(master!=null)
			addParameters(master);
		
		if(StringUtils.isNotBlank(actionIdentifier))
			Parameter.add(instance.getParameters(),UniformResourceLocatorParameter.ACTION_IDENTIFIER, actionIdentifier);
		
		if(CommonBusinessAction.CREATE.equals(commonBusinessAction)){
			addCreateOneParameters(identifiableClass);
		}else if(CommonBusinessAction.SELECT.equals(commonBusinessAction)){
			Parameter.add(instance.getParameters(),UniformResourceLocatorParameter.CLASS, UIManager.getInstance().keyFromClass(instance.getBusinessEntityInfos()));
		}else if(CommonBusinessAction.LIST.equals(commonBusinessAction)){
			Parameter.add(instance.getParameters(),UniformResourceLocatorParameter.CLASS, UIManager.getInstance().keyFromClass(instance.getBusinessEntityInfos()));
		}
		
		if(StringUtils.isEmpty(selectedTabId))
			;
		else
			instance.addParameter(UniformResourceLocatorParameter.TAB_ID, selectedTabId);
		
		if(findTextResult!=null){
			instance.setIdentifier(findTextResult.getIdentifier());
			if(instance.cascadeStyleSheet!=null && StringUtils.isNotBlank(instance.label))
				inject(CascadeStyleSheetBusiness.class).addClasses(instance.cascadeStyleSheet, inject(CascadeStyleSheetBusiness.class)
						.generateUniqueClass(UIManager.COMMANDABLE_CLASS_PREFIX,findTextResult.getIdentifier()));
				//TODO many call of setLabel will add too more classes where previous are useless
		}
		
		if(Boolean.TRUE.equals(useUrlOnClick)){
			
		}
		
		return instance;
	}
	
	public AbstractCommandableBuilder<COMMANDABLE> setView(Object view){
		if(view instanceof ViewType)
			instance.setViewType((ViewType) view);
		else
			instance.setViewId(view);
		return this;
	}
	
	public AbstractCommandableBuilder<COMMANDABLE> setLabelId(String labelId){
		instance.setLabel(inject(LanguageBusiness.class).findText(labelId));
		return this;
	}
	
	public AbstractCommandableBuilder<COMMANDABLE> setLabel(String label){
		instance.setLabel(label);
		return this;
	}
	
	public AbstractCommandableBuilder<COMMANDABLE> addParameters(AbstractIdentifiable identifiable){
		Parameter.add(instance.parameters, identifiable);
		return this;
	}
	
	public AbstractCommandableBuilder<COMMANDABLE> addCreateOneParameters(Class<? extends AbstractIdentifiable> identifiableClass){
		Parameter.addCreateOne(instance.parameters, identifiableClass);
		return this;
	}
	
	/**/
	
	
	
	/**/
	
	public static interface Listener extends AbstractBuilder.Listener<String> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		@Getter @Setter @Accessors(chain=true)
		public static class Adapter extends AbstractBuilder.Listener.Adapter.Default<String> implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
			
			}
		}
	}
	
}
