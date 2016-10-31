package org.cyk.ui.web.api.servlet.report;

import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;

public interface ReportBasedOnDynamicBuilderServletListener {

	void beforeCreateReport(ReportBasedOnDynamicBuilderParameters<?> parameters);
	
}
