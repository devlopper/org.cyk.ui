package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.information.CommentDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener;

@Getter @Setter
public class Comments implements CommandListener, Serializable {

	private static final long serialVersionUID = 2876480260626169563L;

	protected Collection<CommentsListener> commentsListeners = new ArrayList<>();
	
	protected Table<CommentDetails> table;
	protected TableAdapter tableAdapter;
	
	public Comments(AbstractPrimefacesPage page,final AbstractIdentifiable identifiable) {
		if(!Comment.USER_DEFINED_COMMENTABLE_CLASSES.contains(identifiable.getClass()))
			return;
		tableAdapter = new TableAdapter(identifiable);
		table = (Table<CommentDetails>) page.createDetailsTable(CommentDetails.class, tableAdapter);
		
		table.setShowHeader(Boolean.TRUE);
		table.setShowToolBar(Boolean.TRUE);
		table.getAddRowCommandable().addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER, identifiable.getGlobalIdentifier().getIdentifier());
		table.getAddRowCommandable().addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS, UIManager.getInstance().businessEntityInfos(identifiable.getClass()).getIdentifier());
		/*
		if(!Comment.USER_DEFINED_COMMENTABLE_CLASSES.contains(identifiable.getClass()))
			page.removeDetailsMenuCommandable(tableAdapter.getTabId());
		*/
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {}

	@Override
	public Boolean validate(UICommand command, Object parameter) {
		return null;
	}

	@Override
	public void serve(UICommand command, Object parameter) {
		
	}

	@Override
	public Object succeed(UICommand command, Object parameter) {
		return null;
	}

	@Override
	public Object fail(UICommand command, Object parameter, Throwable throwable) {
		return null;
	}

	@Override
	public Boolean notifyAfterServe(UICommand command, AfterServeState state) {
		return null;
	}

	@Override
	public String notificationMessageIdAfterServe(UICommand command,Object parameter, AfterServeState state) {
		return null;
	}

	/**/
	
	public static interface CommentsListener {
		
	}
	
	/**/
	
	public static class TableAdapter extends DetailsConfigurationListener.Table.Adapter<Comment,CommentDetails> implements Serializable{

		private static final long serialVersionUID = -4500309183317753415L;

		protected AbstractIdentifiable identifiable;
		
		public TableAdapter(AbstractIdentifiable identifiable) {
			super(Comment.class, CommentDetails.class);
			this.identifiable = identifiable;
			//rendered = Boolean.FALSE;
		}
		
		@Override
		public Collection<Comment> getIdentifiables() {
			Comment.SearchCriteria searchCriteria = new Comment.SearchCriteria();
			searchCriteria.addCommentTypes(RootBusinessLayer.getInstance().getCommentTypeBusiness().findAll());
			searchCriteria.addGlobalIdentifier(identifiable.getGlobalIdentifier());
			return RootBusinessLayer.getInstance().getCommentBusiness().findByCriteria(searchCriteria);
		}
		@Override
		public Crud[] getCruds() {
			return new Crud[]{Crud.CREATE,Crud.READ,Crud.UPDATE};
		}
		
		
	}
}
