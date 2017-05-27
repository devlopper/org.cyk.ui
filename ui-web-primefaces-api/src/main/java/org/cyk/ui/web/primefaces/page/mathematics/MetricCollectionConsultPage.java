package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.mathematics.MetricDetails;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.web.primefaces.page.AbstractCollectionConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MetricCollectionConsultPage extends AbstractCollectionConsultPage.Extends<MetricCollection,Metric,MetricDetails> implements Serializable {
	
	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		//itemTable.setTitle(inject(LanguageBusiness.class).findClassLabelText(Metric.class));
		itemTable.getAddRowCommandable().addParameter(identifiable);
	}
	
	@Override
	protected Boolean getEnableItemTableInDefaultTab() {
		return Boolean.TRUE;
	}
	
	@Override
	protected String getItemTableTabId() {
		return IdentifierProvider.Adapter.getTabOf(MetricCollection.class);
	}
	
}
