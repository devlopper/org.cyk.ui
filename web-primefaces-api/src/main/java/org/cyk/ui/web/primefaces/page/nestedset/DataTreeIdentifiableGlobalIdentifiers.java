package org.cyk.ui.web.primefaces.page.nestedset;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.pattern.tree.DataTreeIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.DataTreeIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.pattern.tree.DataTreeIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifiers;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifiers.AbstractJoinGlobalIdentifiersListener;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;


@Getter @Setter
public class DataTreeIdentifiableGlobalIdentifiers extends AbstractJoinGlobalIdentifiers<DataTreeIdentifiableGlobalIdentifier,DataTreeIdentifiableGlobalIdentifiers.DataTreeIdentifiableGlobalIdentifiersListener,DataTreeIdentifiableGlobalIdentifierDetails,DataTreeIdentifiableGlobalIdentifier.SearchCriteria> implements CommandListener, Serializable {

	private static final long serialVersionUID = 2876480260626169563L;

	public DataTreeIdentifiableGlobalIdentifiers(AbstractPrimefacesPage page,Class<DataTreeIdentifiableGlobalIdentifierDetails> detailsClass, AbstractIdentifiable identifiable) {
		super(page, detailsClass, identifiable);
	}
	
	@Override
	protected AbstractTableAdapter<DataTreeIdentifiableGlobalIdentifier, DataTreeIdentifiableGlobalIdentifierDetails, SearchCriteria> createTableAdapter(AbstractIdentifiable identifiable) {
		return new TableAdapter(identifiable);
	}

	@Override
	protected Boolean isUserDefinedObject(AbstractIdentifiable identifiable) {
		return DataTreeIdentifiableGlobalIdentifier.isUserDefinedObject(identifiable);
	}
	
	/**/
	
	public static interface DataTreeIdentifiableGlobalIdentifiersListener extends AbstractJoinGlobalIdentifiersListener {
		
	}
	
	/**/
	
	public static class TableAdapter extends AbstractTableAdapter<DataTreeIdentifiableGlobalIdentifier, DataTreeIdentifiableGlobalIdentifierDetails, DataTreeIdentifiableGlobalIdentifier.SearchCriteria> implements Serializable{

		private static final long serialVersionUID = -4500309183317753415L;

		public TableAdapter(AbstractIdentifiable identifiable) {
			super(DataTreeIdentifiableGlobalIdentifier.class,DataTreeIdentifiableGlobalIdentifierDetails.class, identifiable);
		}

		@Override
		protected SearchCriteria createSearchCriteria() {
			SearchCriteria searchCriteria = new SearchCriteria();
			return searchCriteria;
		}

		@Override
		protected JoinGlobalIdentifierBusiness<DataTreeIdentifiableGlobalIdentifier, SearchCriteria> getBusiness() {
			return RootBusinessLayer.getInstance().getDataTreeIdentifiableGlobalIdentifierBusiness();
		}
		
	}

	
}

