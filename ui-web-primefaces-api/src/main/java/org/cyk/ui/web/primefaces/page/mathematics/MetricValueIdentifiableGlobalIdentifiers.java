package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.mathematics.MetricValueIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifiers;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class MetricValueIdentifiableGlobalIdentifiers extends AbstractJoinGlobalIdentifiers<MetricValueIdentifiableGlobalIdentifier,MetricValueIdentifiableGlobalIdentifiers.MetricValueIdentifiableGlobalIdentifiersListener,MetricValueIdentifiableGlobalIdentifierDetails,MetricValueIdentifiableGlobalIdentifier.SearchCriteria> implements CommandListener, Serializable {

	private static final long serialVersionUID = 2876480260626169563L;

	public MetricValueIdentifiableGlobalIdentifiers(AbstractPrimefacesPage page,Class<MetricValueIdentifiableGlobalIdentifierDetails> detailsClass, AbstractIdentifiable identifiable) {
		super(page, detailsClass, identifiable);
	}
	
	@Override
	protected AbstractTableAdapter<MetricValueIdentifiableGlobalIdentifier, MetricValueIdentifiableGlobalIdentifierDetails, SearchCriteria> createTableAdapter(AbstractIdentifiable identifiable) {
		return new TableAdapter(identifiable);
	}

	@Override
	protected Boolean isUserDefinedObject(AbstractIdentifiable comment) {
		return MetricValueIdentifiableGlobalIdentifier.isUserDefinedObject(comment);
	}
	
	/**/
	
	public static interface MetricValueIdentifiableGlobalIdentifiersListener extends org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifiers.AbstractJoinGlobalIdentifiersListener {
		
	}
	
	/**/
	
	public static class TableAdapter extends AbstractTableAdapter<MetricValueIdentifiableGlobalIdentifier, MetricValueIdentifiableGlobalIdentifierDetails, MetricValueIdentifiableGlobalIdentifier.SearchCriteria> implements Serializable{

		private static final long serialVersionUID = -4500309183317753415L;

		public TableAdapter(AbstractIdentifiable identifiable) {
			super(MetricValueIdentifiableGlobalIdentifier.class,MetricValueIdentifiableGlobalIdentifierDetails.class, identifiable);
		}

		@Override
		protected SearchCriteria createSearchCriteria() {
			SearchCriteria searchCriteria = new SearchCriteria();
			return searchCriteria;
		}

		@Override
		protected JoinGlobalIdentifierBusiness<MetricValueIdentifiableGlobalIdentifier, SearchCriteria> getBusiness() {
			return inject(MetricValueIdentifiableGlobalIdentifierBusiness.class);
		}
		
	}

	
}

