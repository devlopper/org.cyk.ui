package org.cyk.ui.web.primefaces.page.information;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.impl.BusinessLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.GlobalIdentifier;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.information.CommentType;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

@Named @ViewScoped @Getter @Setter
public class CommentEditPage extends AbstractCrudOnePage<Comment> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private AbstractIdentifiable commented;
	private Collection<CommentType> commentTypes;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		String globalIdentifier = requestParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER);
		BusinessEntityInfos globalIdentifierOwnerBusinessEntityInfos = uiManager.classFromKey(requestParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS));
		commented = BusinessLocator.getInstance().locate((Class<? extends AbstractIdentifiable>) globalIdentifierOwnerBusinessEntityInfos.getClazz())
				.findByGlobalIdentifierValue(globalIdentifier);
		commentTypes = rootBusinessLayer.getCommentTypeBusiness().findAll();
		
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			@Override
			public Boolean build(Field field) {
				if(Form.FIELD_TYPE.equals(field.getName()))
					return commentTypes.size() > 1;
				if(Form.FIELD_GLOBAL_IDENTIFIER.equals(field.getName())){
					return commented == null;
				}
				return super.build(field);
			}
		});
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		setChoicesAndGetAutoSelected(Form.FIELD_TYPE, commentTypes);	
		setChoicesAndGetAutoSelected(Form.FIELD_GLOBAL_IDENTIFIER, Arrays.asList( commented.getGlobalIdentifier() ));
	}
	
	@Override
	protected String buildContentTitle() {
		return super.buildContentTitle()+Constant.CHARACTER_SLASH+formatUsingBusiness(commented);
	}
	
	@Override
	protected void create() {
		if(identifiable.getType()==null && commentTypes.size() == 1)
			identifiable.setType(commentTypes.iterator().next());
		if(identifiable.getIdentifiableGlobalIdentifier()==null && commented!=null)
			identifiable.setIdentifiableGlobalIdentifier(commented.getGlobalIdentifier());
		super.create();
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<Comment> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo @NotNull private CommentType type;
		@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo @NotNull private GlobalIdentifier identifiableGlobalIdentifier;
		@Input @InputTextarea @NotNull private String message;
				
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_GLOBAL_IDENTIFIER = "identifiableGlobalIdentifier";
		public static final String FIELD_GLOBAL_MESSAGE = "message";
	}
	
}
