package org.cyk.ui.api.model;

import java.io.Serializable;

import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.ui.api.UIManager;

public abstract class AbstractOutputDetails implements Serializable {

	private static final long serialVersionUID = 7439361240545541931L;

	protected UIManager uiManager = UIManager.getInstance();
	protected TimeBusiness timeBusiness = UIManager.getInstance().getTimeBusiness();
	protected NumberBusiness numberBusiness = UIManager.getInstance().getNumberBusiness();
	
}
