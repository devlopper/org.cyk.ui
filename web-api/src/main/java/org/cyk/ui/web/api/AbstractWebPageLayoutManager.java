package org.cyk.ui.web.api;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWebPageLayoutManager extends AbstractBean implements WebPageLayoutManager,Serializable {

	private static final long serialVersionUID = 640603408757885250L;
	
	@Getter @Setter protected String contracts = "defaultDesktop";
	@Getter @Setter protected String template = "/template/default.xhtml";
	@Getter @Setter protected String decoratedTemplate = "/org.cyk.ui.web.primefaces/include/template.xhtml";
	
}
