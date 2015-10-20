package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractOutputDetails<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7439361240545541931L;

	protected UIManager uiManager = UIManager.getInstance();
	protected TimeBusiness timeBusiness = UIManager.getInstance().getTimeBusiness();
	protected NumberBusiness numberBusiness = UIManager.getInstance().getNumberBusiness();
	
	@Getter @Setter protected IDENTIFIABLE master;
	
	/*@Getter @Setter protected String identifier;
	@Getter @Setter protected Boolean buildable = Boolean.TRUE;*/
	
	public AbstractOutputDetails(IDENTIFIABLE master) {
		super();
		this.master = master;
	}
	
}
