package org.cyk.ui.web.primefaces.globalidentification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener;

@Getter @Setter
public abstract class AbstractJoinGlobalIdentifiers<IDENTIFIABLE extends AbstractJoinGlobalIdentifier,LISTENER extends AbstractJoinGlobalIdentifiers.AbstractJoinGlobalIdentifiersListener
	,DETAILS extends AbstractJoinGlobalIdentifierDetails<IDENTIFIABLE>,SEARCH_CRITERIA extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria> implements CommandListener, Serializable {

	private static final long serialVersionUID = 2876480260626169563L;
 
	protected Collection<LISTENER> commentsListeners = new ArrayList<>();
	
	protected Table<DETAILS> table;
	protected AbstractTableAdapter<IDENTIFIABLE,DETAILS,SEARCH_CRITERIA> tableAdapter;
	
	public AbstractJoinGlobalIdentifiers(AbstractPrimefacesPage page,Class<DETAILS> detailsClass,final AbstractIdentifiable identifiable) {
		if(! Boolean.TRUE.equals(isUserDefinedObject(identifiable)) )
			return;
		tableAdapter = createTableAdapter(identifiable);
		table = (Table<DETAILS>) page.createDetailsTable(detailsClass, tableAdapter);
		 
		table.setShowHeader(Boolean.TRUE);
		table.setShowToolBar(Boolean.TRUE);
		
		BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(identifiable.getClass());
		
		table.getOpenRowCommandable().addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER, identifiable.getGlobalIdentifier().getIdentifier());
		table.getOpenRowCommandable().addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS, businessEntityInfos.getIdentifier());
		
		table.getAddRowCommandable().addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER, identifiable.getGlobalIdentifier().getIdentifier());
		table.getAddRowCommandable().addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS, businessEntityInfos.getIdentifier());
		
		table.getUpdateRowCommandable().addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER, identifiable.getGlobalIdentifier().getIdentifier());
		table.getUpdateRowCommandable().addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS, businessEntityInfos.getIdentifier());
		
		table.getRemoveRowCommandable().addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER, identifiable.getGlobalIdentifier().getIdentifier());
		table.getRemoveRowCommandable().addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS, businessEntityInfos.getIdentifier());
	
	}
	
	protected abstract AbstractTableAdapter<IDENTIFIABLE,DETAILS,SEARCH_CRITERIA> createTableAdapter(AbstractIdentifiable identifiable);
	
	protected abstract Boolean isUserDefinedObject(AbstractIdentifiable identifiable);
	
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
	
	public static interface AbstractJoinGlobalIdentifiersListener {
		
	}
	
	/**/
	
	public static abstract class AbstractTableAdapter<IDENTIFIABLE extends AbstractJoinGlobalIdentifier,DETAILS extends AbstractJoinGlobalIdentifierDetails<IDENTIFIABLE>
		,SEARCH_CRITERIA extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria> extends DetailsConfigurationListener.Table.Adapter<IDENTIFIABLE,DETAILS> implements Serializable{

		private static final long serialVersionUID = -4500309183317753415L;

		protected AbstractIdentifiable identifiable;
		
		public AbstractTableAdapter(Class<IDENTIFIABLE> identifiableClass,Class<DETAILS> detailsClass,AbstractIdentifiable identifiable) {
			super(identifiableClass, detailsClass);
			this.identifiable = identifiable;
		}
		 
		protected abstract SEARCH_CRITERIA createSearchCriteria();
		
		protected abstract JoinGlobalIdentifierBusiness<IDENTIFIABLE, SEARCH_CRITERIA> getBusiness();
		
		@Override
		public Collection<IDENTIFIABLE> getIdentifiables() {
			SEARCH_CRITERIA searchCriteria = createSearchCriteria();
			searchCriteria.addGlobalIdentifier(identifiable.getGlobalIdentifier());
			return getBusiness().findByCriteria(searchCriteria);
		}
		@Override
		public Crud[] getCruds() {
			return new Crud[]{Crud.CREATE,Crud.READ,Crud.UPDATE,Crud.DELETE};
		}
		
		
	}
}
