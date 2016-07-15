package org.cyk.ui.web.primefaces.page.information;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.information.CommentDetails;
import org.cyk.system.root.model.information.Comment;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class CommentConsultPage extends AbstractConsultPage<Comment> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private FormOneData<CommentDetails> details;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		details = createDetailsForm(CommentDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<Comment,CommentDetails>(Comment.class, CommentDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});
		
	}
	
}
