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
import org.cyk.ui.api.model.AbstractOutputDetails;
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
		contentTitle = text(businessEntityInfos.getUiLabelId());
		
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
	
	protected UICommandable addDetailsMenuCommandable(String labelId,IconType iconType){
		return addDetailsMenuCommandable(labelId,labelId, iconType, businessEntityInfos.getUiConsultViewId());
	}
	
	@Override
	protected void configureDetailsForm(FormOneData<?> form,DetailsFormOneDataConfigurationListener<?, ?> listener) {
		super.configureDetailsForm(form, listener);
		addDetailsMenuCommandable(listener);
	}
	
	@Override
	protected <T> void configureDetailsTable(Class<T> aClass, Table<T> table,DetailsTableConfigurationListener<?, ?> listener) {
		super.configureDetailsTable(aClass, table, listener);
		addDetailsMenuCommandable(listener);
		if(Boolean.TRUE.equals(ArrayUtils.contains(listener.getCruds(), Crud.CREATE))){
			table.getAddRowCommandable().addParameter(identifiable);
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
					addDetailsMenuCommandable(listener.getTitleId(), null);
			}
		}
	}
	
	@Override
	protected <T> Table<T> createDetailsTable(Class<T> aClass,DetailsTableConfigurationListener<?, T> listener) {
		
		return super.createDetailsTable(aClass, listener);
	}
	
}
