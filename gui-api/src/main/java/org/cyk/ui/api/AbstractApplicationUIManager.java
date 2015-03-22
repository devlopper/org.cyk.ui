package org.cyk.ui.api;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractApplicationUIManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 406884223652214395L;

	@Getter @Setter private Integer orderIndex;
	
	@Inject protected UIProvider uiProvider;
	@Inject protected UIManager uiManager;
	@Inject protected MenuManager menuManager;
	@Inject protected LanguageBusiness languageBusiness;
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected BusinessManager businessManager;
	
	public abstract SystemMenu systemMenu(AbstractUserSession userSession);
	
}
