package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.mathematics.MetricCollectionIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifiers;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class MetricCollectionIdentifiableGlobalIdentifiers extends AbstractJoinGlobalIdentifiers<MetricCollectionIdentifiableGlobalIdentifier,MetricCollectionIdentifiableGlobalIdentifiers.MetricCollectionIdentifiableGlobalIdentifiersListener,MetricCollectionIdentifiableGlobalIdentifierDetails,MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria> implements CommandListener, Serializable {

	private static final long serialVersionUID = 2876480260626169563L;

	public MetricCollectionIdentifiableGlobalIdentifiers(AbstractPrimefacesPage page,Class<MetricCollectionIdentifiableGlobalIdentifierDetails> detailsClass, AbstractIdentifiable identifiable) {
		super(page, detailsClass, identifiable);
	}
	
	@Override
	protected AbstractTableAdapter<MetricCollectionIdentifiableGlobalIdentifier, MetricCollectionIdentifiableGlobalIdentifierDetails, SearchCriteria> createTableAdapter(AbstractIdentifiable identifiable) {
		return new TableAdapter(identifiable);
	}

	@Override
	protected Boolean isUserDefinedObject(AbstractIdentifiable comment) {
		return MetricCollectionIdentifiableGlobalIdentifier.isUserDefinedObject(comment);
	}
	
	/**/
	
	public static interface MetricCollectionIdentifiableGlobalIdentifiersListener extends org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifiers.AbstractJoinGlobalIdentifiersListener {
		
	}
	
	/**/
	
	public static class TableAdapter extends AbstractTableAdapter<MetricCollectionIdentifiableGlobalIdentifier, MetricCollectionIdentifiableGlobalIdentifierDetails, MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria> implements Serializable{

		private static final long serialVersionUID = -4500309183317753415L;

		public TableAdapter(AbstractIdentifiable identifiable) {
			super(MetricCollectionIdentifiableGlobalIdentifier.class,MetricCollectionIdentifiableGlobalIdentifierDetails.class, identifiable);
		}

		@Override
		protected SearchCriteria createSearchCriteria() {
			SearchCriteria searchCriteria = new SearchCriteria();
			return searchCriteria;
		}

		@Override
		protected JoinGlobalIdentifierBusiness<MetricCollectionIdentifiableGlobalIdentifier, SearchCriteria> getBusiness() {
			return inject(MetricCollectionIdentifiableGlobalIdentifierBusiness.class);
		}
		
	}

	
}

