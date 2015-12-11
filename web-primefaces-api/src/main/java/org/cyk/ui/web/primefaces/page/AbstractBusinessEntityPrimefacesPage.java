package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.ui.api.model.DetailsBlock;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.primefaces.model.menu.MenuModel;

@Getter
@Setter
public abstract class AbstractBusinessEntityPrimefacesPage<ENTITY extends AbstractIdentifiable> extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected BusinessEntityInfos businessEntityInfos;
	protected IdentifiableConfiguration identifiableConfiguration ;
	protected ENTITY identifiable;
	protected String formModelClassId;
	protected Class<?> formModelClass;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		for(BusinessEntityFormPageListener<?> listener : getListeners())
			listener.initialisationStarted(this); 
		businessEntityInfos = fetchBusinessEntityInfos();
		identifiableConfiguration = uiManager.findConfiguration((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz());
		identifiable = identifiableFromRequestParameter((Class<ENTITY>)businessEntityInfos.getClazz());
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
	
	protected BusinessEntityInfos fetchBusinessEntityInfos(){
		return uiManager.classFromKey(requestParameter(webManager.getRequestParameterClass()));
	}
	
	protected String __formModelClassId__(){
		return requestParameter(webManager.getRequestParameterFormModel());
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
		//if(businessEntityInfos==null)
		//	return new ArrayList<>();
		return primefacesManager.getBusinessEntityFormPageListeners(businessEntityInfos==null?null:businessEntityInfos.getClazz());
	}
	
	protected UICommandable addDetailsMenuCommandable(String identifier,String labelId,IconType iconType){
		return addDetailsMenuCommandable(identifier,labelId, iconType, businessEntityInfos.getUserInterface().getConsultViewId());
	}
	protected UICommandable addDetailsMenuCommandables(String labelId,IconType iconType){
		return addDetailsMenuCommandable(labelId,labelId, iconType);
	}
	
	@Override
	protected <I extends AbstractIdentifiable> void configureDetailsForm(FormOneData<?> form,DetailsConfigurationListener.Form<I, ?> listener) {
		super.configureDetailsForm(form, listener);
		addDetailsMenuCommandable(listener);
	}
	
	@Override
	protected <T> void configureDetailsTable(Class<T> aClass, Table<T> table,DetailsConfigurationListener.Table<?, ?> listener) {
		super.configureDetailsTable(aClass, table, listener);
		addDetailsMenuCommandable(listener);
		if(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.CREATE))){
			if(Boolean.TRUE.equals(listener.getIsIdentifiableMaster()))
				table.getAddRowCommandable().addParameter(identifiable);
			if(listener.getMasters()!=null)
				for(AbstractIdentifiable master : listener.getMasters())
			table.getAddRowCommandable().addParameter(master);
		}
	}
	
	protected void addDetailsMenuCommandable(DetailsConfigurationListener<?, ?> listener){
		if(listener!=null){
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
	
	@Override
	protected <T> Table<T> createDetailsTable(Class<T> aClass,DetailsConfigurationListener.Table<?, T> listener) {
		
		return super.createDetailsTable(aClass, listener);
	}
	
}
