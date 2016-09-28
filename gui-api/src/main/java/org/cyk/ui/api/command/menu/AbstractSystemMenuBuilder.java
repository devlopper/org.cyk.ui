package org.cyk.ui.api.command.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.file.report.ReportTemplateBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.AbstractCommandable;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.model.AbstractHierarchyNode;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

@Getter @Setter
public abstract class AbstractSystemMenuBuilder<COMMANDABLE extends AbstractCommandable,TREE_NODE,TREE_NODE_MODEL extends AbstractHierarchyNode,USER_SESSION extends AbstractUserSession<TREE_NODE,TREE_NODE_MODEL>> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6995162040038809581L;

	protected Collection<AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION>> listeners = new ArrayList<>();
	
	/**/
	
	public abstract SystemMenu build(USER_SESSION userSession);
	
	/**/
	
	protected <TYPE> AbstractTree<TREE_NODE,TREE_NODE_MODEL> getNavigator(Class<TREE_NODE> nodeClass,Class<TREE_NODE_MODEL> nodeModelClass,Class<TYPE> dataClass,USER_SESSION userSession){
		AbstractTree<TREE_NODE,TREE_NODE_MODEL> navigator = createNavigatorTree(userSession);
		Collection<TYPE> datas = getNavigatorTreeNodeDatas(dataClass,userSession);
		/*if(datas!=null){
			for(Object data : datas){
				NODE_MODEL nodeModel = createHierarchyNodeClassInstance(nodeClass, nodeModelClass, userSession, data);
				nodeModel.setExpanded(Boolean.FALSE);
				nodes.add(nodeModel);
			}
		}*/
		navigator.build(dataClass, datas, null);
		return navigator;
	}
	
	protected AbstractTree<TREE_NODE,TREE_NODE_MODEL> createNavigatorTree(USER_SESSION userSession){
		return null;
	}
	protected <TYPE> Collection<TYPE> getNavigatorTreeNodeDatas(Class<TYPE> dataClass,USER_SESSION userSession){
		return null;
	}
	
	protected void addBusinessMenu(USER_SESSION userSession,SystemMenu systemMenu,COMMANDABLE commandable){
		addCommandable(userSession,systemMenu.getBusinesses(),commandable); 
	}
	
	protected void onBusinessMenuPopulateEnded(final USER_SESSION userSession,final COMMANDABLE module){
		listenerUtils.execute(listeners, new ListenerUtils.VoidMethod<AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION>>() {
			@Override
			public void execute(AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE, TREE_NODE_MODEL,USER_SESSION> listener) {
				listener.onBusinessMenuPopulateEnded(userSession, module);
			}
		});
	}
	
	protected void addChild(USER_SESSION userSession,COMMANDABLE parent,COMMANDABLE child){
		addCommandable(userSession,parent.getChildren(),child); 
	}
	
	private void addCommandable(USER_SESSION userSession,Collection<UICommandable> commandables,COMMANDABLE commandable){
		if(commandable==null)
			;
		else
			if(Boolean.TRUE.equals(isCommandableVisible(userSession,commandable))){
				commandables.add(commandable);
			}
	}
	
	protected Boolean isCommandableVisible(final USER_SESSION userSession,final COMMANDABLE commandable){
		Collection<String> invisibleCommandableIdentifiers = getInvisibleCommandableIdentifiers(userSession);
		Boolean visible = Boolean.TRUE; 
		if(invisibleCommandableIdentifiers!=null && StringUtils.isNotBlank(commandable.getIdentifier()) && invisibleCommandableIdentifiers.contains(commandable.getIdentifier()))
			visible = Boolean.FALSE;
		if(visible)
			visible = listenerUtils.getBoolean(listeners, new ListenerUtils.BooleanMethod<AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION>>() {
			@Override
			public Boolean execute(AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION> listener) {
				return listener.isCommandableVisible(userSession,commandable);
			}
			@Override
			public Boolean getNullValue() {
				return Boolean.TRUE;
			}
		});
		return visible;
	}
	
	protected Collection<String> getInvisibleCommandableIdentifiers(final USER_SESSION userSession){
		return listenerUtils.getCollection(listeners, new ListenerUtils.CollectionMethod<AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION>,String>() {
			@Override
			public Collection<String> execute(AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION> listener) {
				return listener.getInvisibleCommandableIdentifiers(userSession);
			}
		});
	}
	
	protected void initialiseNavigatorTree(final USER_SESSION userSession){
		listenerUtils.execute(listeners, new ListenerUtils.VoidMethod<AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION>>(){
			@Override
			public void execute(AbstractSystemMenuBuilderListener<COMMANDABLE, TREE_NODE, TREE_NODE_MODEL, USER_SESSION> listener) {
				listener.initialiseNavigatorTree(userSession);
			}
		});
	}
	
	/**/
	
	protected AbstractCommandable.Builder<COMMANDABLE> getCommandableBuilder(){
		return AbstractCommandable.Builder.instanciateOne();
	}
	
	protected COMMANDABLE createModuleCommandable(String labelId,Icon icon){
		@SuppressWarnings("unchecked")
		COMMANDABLE module = (COMMANDABLE) AbstractCommandable.Builder.create(labelId, icon);
		return module;
	}
	protected COMMANDABLE createModuleCommandable(Class<? extends AbstractIdentifiable> businessClass,Icon icon){
		BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(businessClass);
		return createModuleCommandable(businessEntityInfos.getUserInterface().getLabelId(), icon);
	}
	@SuppressWarnings("unchecked")
	protected COMMANDABLE createListCommandable(Class<? extends AbstractIdentifiable> businessClass,Icon icon){
		return (COMMANDABLE) AbstractCommandable.Builder.createList(businessClass, icon);
	}
	@SuppressWarnings("unchecked")
	protected COMMANDABLE createCreateCommandable(Class<? extends AbstractIdentifiable> businessClass,Icon icon){
		return (COMMANDABLE) AbstractCommandable.Builder.createCreate(UIManager.getInstance().businessEntityInfos(businessClass), icon);
	}
	@SuppressWarnings("unchecked")
	protected COMMANDABLE createCreateManyCommandable(Class<? extends AbstractIdentifiable> businessClass,Icon icon){
		return (COMMANDABLE) AbstractCommandable.Builder.createCreateMany(UIManager.getInstance().businessEntityInfos(businessClass), icon);
	}
	@SuppressWarnings("unchecked")
	protected COMMANDABLE createSelectManyCommandable(Class<? extends AbstractIdentifiable> businessClass,String actionIdentifier,Icon icon){
		return (COMMANDABLE) AbstractCommandable.Builder.createSelectMany(businessClass,actionIdentifier, icon);
	}
	@SuppressWarnings("unchecked")
	protected COMMANDABLE createSelectOneCommandable(Class<? extends AbstractIdentifiable> businessClass,String actionIdentifier,Icon icon){
		return (COMMANDABLE) AbstractCommandable.Builder.createSelectOne(businessClass,actionIdentifier, icon);
	}
	
	@SuppressWarnings("unchecked")
	protected COMMANDABLE createPrintOneCommandable(Class<? extends AbstractIdentifiable> businessClass,String reportTemplateCode,Icon icon){
		ReportTemplate reportTemplate = inject(ReportTemplateBusiness.class).find(reportTemplateCode);
		/*FindDoSomethingTextParameters parameters = new FindDoSomethingTextParameters();
		
		parameters.getSubjectClassLabelTextParameters().setClazz(File.class);
		parameters.getSubjectClassLabelTextParameters().getResult().setValue(reportTemplate.getName());
		parameters.getSubjectClassLabelTextParameters().setGenderType(reportTemplate.getGlobalIdentifier().getMale() == null ? GenderType.UNSET : 
			Boolean.TRUE.equals(reportTemplate.getGlobalIdentifier().getMale()) ? GenderType.MALE : GenderType.FEMALE);
		parameters.setActionIdentifier(CommonBusinessAction.PRINT);
		parameters.setOne(Boolean.TRUE);
		parameters.setGlobal(Boolean.FALSE);
		parameters.setVerb(Boolean.TRUE);
		*/
		
		return (COMMANDABLE) createSelectOneCommandable(businessClass, inject(RootBusinessLayer.class).getActionPrint(), icon)
				.setLabel(inject(LanguageBusiness.class).findDoPrintReportText(reportTemplate).getValue())
				.addParameter(UniformResourceLocatorParameter.REPORT_IDENTIFIER, reportTemplate.getIdentifier());
		//return (COMMANDABLE) AbstractCommandable.Builder.createSelectOne(businessClass,actionIdentifier, icon);
	}
	/**/
	
	protected String getText(String code){
		return inject(LanguageBusiness.class).findText(code);
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> Collection<UICommandable> getReportCommandables(Class<IDENTIFIABLE> identifiableClass,Collection<? extends AbstractIdentifiableBusinessServiceImpl.Listener<IDENTIFIABLE>> listeners){
		Collection<UICommandable> commandables = new ArrayList<>();
		Set<String> reportTemplateCodes = new LinkedHashSet<>();
		for(AbstractIdentifiableBusinessServiceImpl.Listener<?> listener : listeners)
			if(listener.getCascadeToReportTemplateCodes()!=null)
				for(String reportTemplateCode : listener.getCascadeToReportTemplateCodes())
					reportTemplateCodes.add(reportTemplateCode);
		
		for(String reportTemplateCode : reportTemplateCodes)
			commandables.add(createPrintOneCommandable(identifiableClass, reportTemplateCode, null));
		
		return commandables;
	}
	public <IDENTIFIABLE extends AbstractIdentifiable> void addReportCommandables(Class<IDENTIFIABLE> identifiableClass,UICommandable commandable,Collection<? extends AbstractIdentifiableBusinessServiceImpl.Listener<IDENTIFIABLE>> listeners){
		for(UICommandable reportCommandable : getReportCommandables(identifiableClass,listeners))
			commandable.addChild(reportCommandable);
	}
	
	public static interface AbstractSystemMenuBuilderListener<COMMANDABLE extends AbstractCommandable,TREE_NODE,TREE_NODE_MODEL extends AbstractHierarchyNode,USER_SESSION extends AbstractUserSession<TREE_NODE,TREE_NODE_MODEL>> {
		
		Set<String> getInvisibleCommandableIdentifiers(USER_SESSION userSession);
		Boolean isCommandableVisible(USER_SESSION userSession,COMMANDABLE commandable);
		void onBusinessMenuPopulateEnded(USER_SESSION userSession,COMMANDABLE module);
		void initialiseNavigatorTree(USER_SESSION userSession);
		
		/**/
		
		public static class Adapter<COMMANDABLE extends AbstractCommandable,TREE_NODE,TREE_NODE_MODEL extends AbstractHierarchyNode,USER_SESSION extends AbstractUserSession<TREE_NODE,TREE_NODE_MODEL>> extends BeanAdapter implements AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE, TREE_NODE_MODEL,USER_SESSION>{
			private static final long serialVersionUID = 3034803382486669232L;

			@Override
			public Boolean isCommandableVisible(USER_SESSION userSession,COMMANDABLE commandable) {
				return null;
			}

			@Override
			public Set<String> getInvisibleCommandableIdentifiers(USER_SESSION userSession) {
				return null;
			}

			@Override
			public void onBusinessMenuPopulateEnded(USER_SESSION userSession,COMMANDABLE module) {}
			
			@Override
			public void initialiseNavigatorTree(USER_SESSION userSession) {}
		}
	}
}
