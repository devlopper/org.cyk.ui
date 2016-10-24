package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.mathematics.MetricDetails;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.ui.web.primefaces.page.AbstractCollectionConsultPage;

@Named @ViewScoped @Getter @Setter
public class MetricCollectionConsultPage extends AbstractCollectionConsultPage<MetricCollection,MetricCollection,Metric,MetricDetails> implements Serializable {
	
	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected MetricCollection getCollection() {
		return identifiable;
	}
	
}
