package org.cyk.ui.web.primefaces.file;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.impl.BusinessLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Named @ViewScoped @Getter @Setter
public class FileIdentifiableGlobalIdentifierEditPage extends AbstractCrudOnePage<FileIdentifiableGlobalIdentifier> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private AbstractIdentifiable joinedIdentifiable;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		String globalIdentifier = requestParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER);
		BusinessEntityInfos globalIdentifierOwnerBusinessEntityInfos = uiManager.classFromKey(requestParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS));
		joinedIdentifiable = BusinessLocator.getInstance().locate((Class<? extends AbstractIdentifiable>) globalIdentifierOwnerBusinessEntityInfos.getClazz())
				.findByGlobalIdentifierValue(globalIdentifier);
		
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			@Override
			public Boolean build(Field field) {
				if(Form.FIELD_GLOBAL_IDENTIFIER.equals(field.getName())){
					return joinedIdentifiable == null;
				}
				return super.build(field);
			}
		});
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		setChoicesAndGetAutoSelected(Form.FIELD_GLOBAL_IDENTIFIER, Arrays.asList( joinedIdentifiable.getGlobalIdentifier() ));
	}
	
	@Override
	protected String buildContentTitle() {
		return super.buildContentTitle()+Constant.CHARACTER_SLASH+formatUsingBusiness(joinedIdentifiable);
	}
	
	@Override
	protected void create() {
		if(identifiable.getIdentifiableGlobalIdentifier()==null && joinedIdentifiable!=null)
			identifiable.setIdentifiableGlobalIdentifier(joinedIdentifiable.getGlobalIdentifier());
		super.create();
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<FileIdentifiableGlobalIdentifier> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo @NotNull private GlobalIdentifier identifiableGlobalIdentifier;
				
		public static final String FIELD_GLOBAL_IDENTIFIER = "identifiableGlobalIdentifier";
		
	}
	
}
