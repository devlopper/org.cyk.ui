package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.DetailsBlock;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.utility.common.Constant;
import org.primefaces.model.menu.MenuModel;

@Getter
@Setter
public abstract class AbstractBusinessEntityPrimefacesPage<ENTITY extends AbstractIdentifiable> extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected BusinessEntityInfos businessEntityInfos;
	protected IdentifiableConfiguration identifiableConfiguration ;
	protected ENTITY identifiable;//TODO is it the right place ???
	protected String formModelClassId;
	protected Class<?> formModelClass;
	protected FormConfiguration formConfiguration;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		for(BusinessEntityFormPageListener<?> listener : getListeners())
			listener.initialisationStarted(this); 
		businessEntityInfos = fetchBusinessEntityInfos();
		identifiableConfiguration = uiManager.findConfiguration((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz(),Boolean.TRUE);
		identifiable = identifiableFromRequestParameter((Class<ENTITY>)businessEntityInfos.getClazz());
		if(identifiable!=null){
			identifiable.getProcessing().setParty(userSession.getUser());
			identifiable.getProcessing().setIdentifier(actionIdentifier);
			processOnIdentifiableFound(identifiable);
		}
		formModelClassId = __formModelClassId__();
		formModelClass = __formModelClass__();
		for(BusinessEntityFormPageListener<?> listener : getListeners()){
			Class<?> fmc = listener.getFormModelClass();
			if(fmc!=null)
				formModelClass = fmc;
		}
		
		contentTitle = text(businessEntityInfos.getUserInterface().getLabelId());
		
		for(BusinessEntityFormPageListener<?> listener : getListeners())
			listener.initialisationEnded(this); 
		
		logDebug("Web page created I={} , M={}", businessEntityInfos==null?"":businessEntityInfos.getClazz().getSimpleName(),
				formModelClass==null?"":formModelClass.getSimpleName());
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(BusinessEntityFormPageListener<?> listener : getListeners())
			listener.afterInitialisationStarted(this); 
		
		
		
		for(BusinessEntityFormPageListener<?> listener : getListeners())
			listener.afterInitialisationEnded(this); 
	}
	
	@Override
	protected String buildContentTitle() {
		StringBuilder stringBuilder = new StringBuilder(languageBusiness.findDoSomethingText(getContentTitleDoSomethingTextParameters()).getValue());
		if(identifiable==null){
			
		}else{
			String identiableText = getContentTitleIdentifiableText();
			if(StringUtils.isNotBlank(identiableText))
				stringBuilder.append(Constant.CHARACTER_SPACE.toString()+Constant.CHARACTER_VERTICAL_BAR+Constant.CHARACTER_SPACE+identiableText);	
		}
		return stringBuilder.toString();
	}
	
	protected String getContentTitleIdentifiableText(){
		return formatUsingBusiness(identifiable);
		//return formatPathUsingBusiness(AbstractIdentifiable.class, identifiable);
	}
	
	@SuppressWarnings("unchecked")
	protected FindDoSomethingTextParameters getContentTitleDoSomethingTextParameters() {
		final FindDoSomethingTextParameters parameters = new FindDoSomethingTextParameters();
		parameters.getSubjectClassLabelTextParameters().setClazz((Class<? extends AbstractIdentifiable>) /*identifiable.getClass()*/businessEntityInfos.getClazz());
		parameters.setOne(Boolean.TRUE);
		parameters.setGlobal(Boolean.FALSE);
		parameters.setVerb(Boolean.FALSE);
		
		return parameters;
	}
	
	protected BusinessEntityInfos fetchBusinessEntityInfos(){
		return uiManager.classFromKey(requestParameter(UniformResourceLocatorParameter.CLASS));
	}
	
	protected void processOnIdentifiableFound(ENTITY identifiable){}
	
	protected Boolean isDetailsMenuCommandable(Class<?> aClass){
		return isDetailsMenuCommandable(aClass, identifiable);
	}
	
	protected String __formModelClassId__(){
		return requestParameter(UniformResourceLocatorParameter.FORM_MODEL);
	}
	
	protected Class<?> __formModelClass__(){
		if(StringUtils.isNotBlank(formModelClassId))
			formModelClass = UIManager.FORM_MODEL_MAP.get(formModelClassId);
		return formModelClass;
	}
	
	protected DetailsBlock<MenuModel> createDetailsBlock(AbstractOutputDetails<?> details, String editOutcome,UICommandable... links) {
		return super.createDetailsBlock(identifiable, details, editOutcome, links);
	}
	protected DetailsBlock<MenuModel> createDetailsBlock(AbstractOutputDetails<?> details,UICommandable... links) {
		return createDetailsBlock(details, null,links);
	}
	
	private Collection<BusinessEntityFormPageListener<?>> getListeners(){
		return BusinessEntityFormPageListener.Adapter.getBusinessEntityFormPageListeners(businessEntityInfos==null?null:businessEntityInfos.getClazz());
	}
	
	protected UICommandable addDetailsMenuCommandable(String identifier,String labelId,Icon icon){
		return addDetailsMenuCommandable(identifier,labelId, icon, businessEntityInfos.getUserInterface().getConsultViewId());
	}
	protected UICommandable addDetailsMenuCommandables(String labelId,Icon icon){
		return addDetailsMenuCommandable(labelId,labelId, icon);
	}
	
	@Override
	protected <I extends AbstractIdentifiable> void configureDetailsForm(FormOneData<?> form,DetailsConfigurationListener.Form<I, ?> listener) {
		super.configureDetailsForm(form, listener);
		addDetailsMenuCommandable(listener);
	}
	
	@Override
	protected <T> void configureDetailsTable(Class<T> aClass, Table<T> table,DetailsConfigurationListener.Table<?, ?> listener) {
		super.configureDetailsTable(aClass, table, listener);
		if(Boolean.TRUE.equals(WindowInstanceManager.INSTANCE.isShowDetails(listener.getDataClass(), identifiable, this))){
			addDetailsMenuCommandable(listener);
			if(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.CREATE))){
				if(Boolean.TRUE.equals(listener.getIsIdentifiableMaster()))
					table.getAddRowCommandable().addParameter(identifiable);
				if(listener.getMasters()!=null)
					for(AbstractIdentifiable master : listener.getMasters())
						table.getAddRowCommandable().addParameter(master);
			}
		}
	}
	
	protected void addDetailsMenuCommandable(DetailsConfigurationListener<?, ?> listener){
		if(Boolean.TRUE.equals(WindowInstanceManager.INSTANCE.isShowDetails(listener.getDataClass(), identifiable, this))){
			//Boolean rendered = listener.isRendered(this); //&& isDetailsMenuCommandable(listener.getIdentifiableClass());
			if(listener!=null /*&& (rendered == null || Boolean.TRUE.equals(rendered))*/){
				if(StringUtils.isNotBlank(listener.getTabId()) && Boolean.TRUE.equals(listener.getAutoAddTabCommandable())){
					Boolean found = Boolean.FALSE;
					if(detailsMenu!=null)
						for(UICommandable commandable : detailsMenu.getCommandables()){
							if(commandable.getIdentifier().equals(listener.getTabId())){
								found = Boolean.TRUE;
								break;
							}
					}
					if(Boolean.FALSE.equals(found))
						addDetailsMenuCommandable(listener.getTabId(),listener.getTitleId(), null);
				}
			}
		}
	}
	
	@Override
	public <T> Table<T> createDetailsTable(Class<T> aClass,DetailsConfigurationListener.Table<?, T> listener) {
		
		return super.createDetailsTable(aClass, listener);
	}
	
	protected FormConfiguration getFormConfiguration(Crud crud, String type){
		return FormConfiguration.get(businessEntityInfos.getClazz(), crud, type, Boolean.FALSE);
	}
	
	protected FormConfiguration getFormConfiguration(Crud crud){
		return getFormConfiguration(crud, selectedTabId);
	}
	
	/**/
	
	public interface BusinessEntityPrimefacesPageListener<ENTITY extends AbstractIdentifiable> extends AbstractPrimefacesPage.PrimefacesPageListener {

		Collection<BusinessEntityPrimefacesPageListener<?>> COLLECTION = new ArrayList<>();
		
		Class<ENTITY> getEntityTypeClass();
		
		void processContentTitleDoSomethingTextParameters(FindDoSomethingTextParameters findDoSomethingTextParameters,String actionIdentifier);
		
		/**/
		
		public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends AbstractPrimefacesPage.PrimefacesPageListener.Adapter implements BusinessEntityPrimefacesPageListener<ENTITY_TYPE>,Serializable {
			private static final long serialVersionUID = -7944074776241690783L;

			@Getter protected Class<ENTITY_TYPE> entityTypeClass;
			
			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super();
				this.entityTypeClass = entityTypeClass;
			}
			
			@Override
			public void processContentTitleDoSomethingTextParameters(FindDoSomethingTextParameters findDoSomethingTextParameters,String actionIdentifier) {}
			
			public static Collection<BusinessEntityPrimefacesPageListener<?>> getBusinessEntityPrimefacesPageListeners(Class<? extends Identifiable<?>> aClass){
				Collection<BusinessEntityPrimefacesPageListener<?>> results = new ArrayList<>();
				if(aClass!=null)
					for(BusinessEntityPrimefacesPageListener<?> listener : BusinessEntityPrimefacesPageListener.COLLECTION)
						if(listener.getEntityTypeClass().isAssignableFrom(aClass))
							results.add(listener);
				return results;
			}
		}

		
	}

	
}
