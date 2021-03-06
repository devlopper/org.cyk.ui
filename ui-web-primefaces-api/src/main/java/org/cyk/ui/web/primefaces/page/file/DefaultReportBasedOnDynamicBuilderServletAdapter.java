package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import org.cyk.system.root.business.impl.party.person.PersonBusinessImpl.AbstractPersonDetails;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.web.api.servlet.report.ReportBasedOnDynamicBuilderServletListener;
import org.cyk.utility.common.cdi.BeanAdapter;

public class DefaultReportBasedOnDynamicBuilderServletAdapter<ACTOR extends AbstractActor> extends BeanAdapter implements ReportBasedOnDynamicBuilderServletListener,Serializable {

	private static final long serialVersionUID = 4370361826462886031L;

	@Override
	public void beforeCreateReport(ReportBasedOnDynamicBuilderParameters<?> parameters) {
		if(AbstractActor.class.isAssignableFrom(parameters.getIdentifiableClass())){
			parameters.getColumnNamesToExclude().add(AbstractPersonDetails.FIELD_IMAGE);
		}
	}
		
}
