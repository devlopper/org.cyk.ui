package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter
public class PrintDataTablePage extends AbstractExportDataTablePage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected String fileExtension() {
		return "pdf";
	}
	
	@Override
	protected Boolean print() {
		return Boolean.TRUE;
	}

}
