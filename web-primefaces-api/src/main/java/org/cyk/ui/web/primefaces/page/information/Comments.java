package org.cyk.ui.web.primefaces.page.information;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.information.CommentDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.information.Comment.SearchCriteria;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifiers;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifiers.AbstractJoinGlobalIdentifiersListener;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Getter @Setter
public class Comments extends AbstractJoinGlobalIdentifiers<Comment,Comments.CommentsListener,CommentDetails,Comment.SearchCriteria> implements CommandListener, Serializable {

	private static final long serialVersionUID = 2876480260626169563L;

	public Comments(AbstractPrimefacesPage page,Class<CommentDetails> detailsClass, AbstractIdentifiable identifiable) {
		super(page, detailsClass, identifiable);
	}
	
	@Override
	protected AbstractTableAdapter<Comment, CommentDetails, SearchCriteria> createTableAdapter(AbstractIdentifiable identifiable) {
		return new TableAdapter(identifiable);
	}

	@Override
	protected Boolean isUserDefinedObject(AbstractIdentifiable comment) {
		return Comment.isUserDefinedObject(comment);
	}
	
	/**/
	
	public static interface CommentsListener extends AbstractJoinGlobalIdentifiersListener {
		
	}
	
	/**/
	
	public static class TableAdapter extends AbstractTableAdapter<Comment, CommentDetails, Comment.SearchCriteria> implements Serializable{

		private static final long serialVersionUID = -4500309183317753415L;

		public TableAdapter(AbstractIdentifiable identifiable) {
			super(Comment.class,CommentDetails.class, identifiable);
		}

		@Override
		protected SearchCriteria createSearchCriteria() {
			SearchCriteria searchCriteria = new SearchCriteria();
			return searchCriteria;
		}

		@Override
		protected JoinGlobalIdentifierBusiness<Comment, SearchCriteria> getBusiness() {
			return RootBusinessLayer.getInstance().getCommentBusiness();
		}
		
	}

	
}
