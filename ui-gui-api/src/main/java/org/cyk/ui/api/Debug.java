package org.cyk.ui.api;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Debug implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8900109823328570869L;
	
	private static final Debug INSTANCE = new Debug();
	
	private final Boolean inputIgnoreRequired = Boolean.FALSE;
	
	/**/
	
	public static Debug getInstance() {
		return INSTANCE;
	}
}
