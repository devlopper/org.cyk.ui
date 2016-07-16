package org.cyk.ui.web.primefaces.page.information;

import java.io.Serializable;
import java.util.Arrays;

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
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

@Named @ViewScoped @Getter @Setter
public class CommentEditPage extends AbstractCrudOnePage<Comment> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		String globalIdentifier = requestParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER);
		BusinessEntityInfos globalIdentifierOwnerBusinessEntityInfos = uiManager.classFromKey(requestParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS));
		@SuppressWarnings("unchecked")
		AbstractIdentifiable identifiable = BusinessLocator.getInstance().locate((Class<? extends AbstractIdentifiable>) globalIdentifierOwnerBusinessEntityInfos.getClazz())
				.findByGlobalIdentifierValue(globalIdentifier);
		setChoicesAndGetAutoSelected(Form.FIELD_GLOBAL_IDENTIFIER, Arrays.asList( identifiable.getGlobalIdentifier() ));
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<Comment> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private CommentType type;
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private GlobalIdentifier identifiableGlobalIdentifier;
		@Input @InputTextarea @NotNull private String message;
				
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_GLOBAL_IDENTIFIER = "identifiableGlobalIdentifier";
		public static final String FIELD_GLOBAL_MESSAGE = "message";
	}
	
}
