package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.api.AbstractWebPageLayoutManager;

@Named("mobilePageLayoutManager") @SessionScoped @Getter @Setter
public class PrimefacesDefaultMobilePageLayoutManager extends AbstractWebPageLayoutManager implements Serializable {

	private static final long serialVersionUID = 2282543573812258638L;
	
	public static final String DEFAULT_CONTRACTS = "defaultMobile";
	public static final String DEFAULT_TEMPLATE = "/template/defaultManyPage.xhtml";
	public static final String DEFAULT_DECORATED_TEMPLATE = "/org.cyk.ui.web.primefaces/template/page/mobile/default.xhtml";
	
	public PrimefacesDefaultMobilePageLayoutManager() {
		contracts = DEFAULT_CONTRACTS;
		template = DEFAULT_TEMPLATE;
		decoratedTemplate = DEFAULT_DECORATED_TEMPLATE;
	}
	
}
