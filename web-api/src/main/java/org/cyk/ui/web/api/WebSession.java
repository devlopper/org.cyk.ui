package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import org.cyk.ui.api.AbstractUserSession;

@SessionScoped
public class WebSession extends AbstractUserSession implements Serializable {

	private static final long serialVersionUID = 7799444210756287076L;

}
