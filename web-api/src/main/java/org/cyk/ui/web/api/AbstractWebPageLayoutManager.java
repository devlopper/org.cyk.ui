package org.cyk.ui.web.api;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWebPageLayoutManager extends AbstractBean implements WebPageLayoutManager,Serializable {

	private static final long serialVersionUID = 640603408757885250L;
	
	public static final String DEFAULT_CONTRACTS = "defaultDesktop";
	public static final String DEFAULT_TEMPLATE = "/template/default.xhtml";
	public static final String DEFAULT_DECORATED_TEMPLATE = "/org.cyk.ui.web.primefaces/template/page/default.xhtml";
	
	@Getter @Setter protected String contracts = DEFAULT_CONTRACTS;
	@Getter @Setter protected String template = DEFAULT_TEMPLATE;
	@Getter @Setter protected String decoratedTemplate = DEFAULT_DECORATED_TEMPLATE;
	
}
