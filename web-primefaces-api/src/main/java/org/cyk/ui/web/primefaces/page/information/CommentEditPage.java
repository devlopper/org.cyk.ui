package org.cyk.ui.web.primefaces.page.information;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.information.Comment;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifierEditPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

import lombok.Getter;
import lombok.Setter;
import net.sourceforge.htmlunit.corejs.javascript.Token.CommentType;

@Named @ViewScoped @Getter @Setter
public class CommentEditPage extends AbstractJoinGlobalIdentifierEditPage<Comment> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter
	public static class Form extends AbstractJoinGlobalIdentifierEditPage.AbstractForm<Comment> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo @NotNull private CommentType type;
		@Input @InputTextarea @NotNull private String message;
				
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_GLOBAL_MESSAGE = "message";
	}
	
}
