package org.cyk.ui.web.primefaces.globalidentification;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractJoinGlobalIdentifierEditPage<IDENTIFIABLE extends AbstractJoinGlobalIdentifier> extends AbstractCrudOnePage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected AbstractIdentifiable joinedIdentifiable;
	
	@SuppressWarnings("unchecked")
	@Override   
	protected void initialisation() { 
		super.initialisation();
		String globalIdentifier = requestParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER);
		BusinessEntityInfos globalIdentifierOwnerBusinessEntityInfos = uiManager.classFromKey(requestParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS));
		if(globalIdentifierOwnerBusinessEntityInfos!=null)
			joinedIdentifiable = (AbstractIdentifiable) BusinessInterfaceLocator.getInstance()
				.injectTyped((Class<AbstractIdentifiable>)globalIdentifierOwnerBusinessEntityInfos.getClazz()) 
			//BusinessLocator.getInstance().locate((Class<? extends AbstractIdentifiable>) globalIdentifierOwnerBusinessEntityInfos.getClazz())
				.findByGlobalIdentifierValue(globalIdentifier);
		if(Crud.isCreateOrUpdate(crud))
			((AbstractForm<IDENTIFIABLE>)form.getData()).setJoinedIdentifiable(joinedIdentifiable);
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			private static final long serialVersionUID = 1L;

			@Override
			public Boolean build(Object data,Field field) {
				if(AbstractForm.FIELD_GLOBAL_IDENTIFIER.equals(field.getName())){
					return joinedIdentifiable == null;
				}
				return super.build(data,field);
			}
		});
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		if(joinedIdentifiable!=null)
			setChoicesAndGetAutoSelected(AbstractForm.FIELD_GLOBAL_IDENTIFIER, Arrays.asList( joinedIdentifiable.getGlobalIdentifier() ));
	}
	
	@Override
	protected String buildContentTitle() {
		if(joinedIdentifiable==null)
			return super.buildContentTitle();
		return super.buildContentTitle()+Constant.CHARACTER_SLASH+formatUsingBusiness(joinedIdentifiable);
	}
	
	@Override
	protected void create() {
		if(identifiable.getIdentifiableGlobalIdentifier()==null && joinedIdentifiable!=null)
			identifiable.setIdentifiableGlobalIdentifier(joinedIdentifiable.getGlobalIdentifier());
		super.create();
	}
	
	@Getter @Setter
	public static class AbstractForm<IDENTIFIABLE extends AbstractJoinGlobalIdentifier> extends AbstractFormModel<IDENTIFIABLE> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		protected AbstractIdentifiable joinedIdentifiable;
		
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete @NotNull protected GlobalIdentifier identifiableGlobalIdentifier;
				
		public static final String FIELD_GLOBAL_IDENTIFIER = "identifiableGlobalIdentifier";
		
	}
	
}
