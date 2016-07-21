package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named @RequestScoped
public class ExportDataTableToPdfPage extends AbstractExportDataTablePage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected String fileExtension() {
		return "pdf";
	}
}
