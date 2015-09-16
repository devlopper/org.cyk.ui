package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;

public abstract class AbstractOutputDetails<IDENTIFIABLE extends AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 7439361240545541931L;

	protected UIManager uiManager = UIManager.getInstance();
	protected TimeBusiness timeBusiness = UIManager.getInstance().getTimeBusiness();
	protected NumberBusiness numberBusiness = UIManager.getInstance().getNumberBusiness();
	
	@Getter @Setter protected IDENTIFIABLE master;
	
	public AbstractOutputDetails(IDENTIFIABLE master) {
		super();
		this.master = master;
		
	}
	
	
	
}
