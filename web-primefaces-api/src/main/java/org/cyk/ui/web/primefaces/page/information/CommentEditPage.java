package org.cyk.ui.web.primefaces.page.information;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.information.CommentType;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifierEditPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

@Named @ViewScoped @Getter @Setter
public class CommentEditPage extends AbstractJoinGlobalIdentifierEditPage<Comment> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private Collection<CommentType> commentTypes;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		commentTypes = rootBusinessLayer.getCommentTypeBusiness().findAll();
		
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			@Override
			public Boolean build(Field field) {
				if(Form.FIELD_TYPE.equals(field.getName()))
					return commentTypes.size() > 1;
				return super.build(field);
			}
		});
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		setChoicesAndGetAutoSelected(Form.FIELD_TYPE, commentTypes);	
	}
	
	@Override
	protected void create() {
		if(identifiable.getType()==null && commentTypes.size() == 1)
			identifiable.setType(commentTypes.iterator().next());
		super.create();
	}
	
	@Getter @Setter
	public static class Form extends AbstractJoinGlobalIdentifierEditPage.AbstractForm<Comment> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo @NotNull private CommentType type;
		@Input @InputTextarea @NotNull private String message;
				
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_GLOBAL_MESSAGE = "message";
	}
	
}
