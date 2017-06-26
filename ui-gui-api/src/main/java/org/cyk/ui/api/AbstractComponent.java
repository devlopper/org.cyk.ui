package org.cyk.ui.api;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractComponent<USER_MODEL,TARGET_MODEL> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Define the user model
	 */
	protected USER_MODEL userModel;
	
	/**
	 * Define the target API model
	 */
	protected TARGET_MODEL targetModel;
	
	public AbstractComponent(USER_MODEL userModel) {
		super();
		this.userModel = userModel;
	}
	
	protected abstract TARGET_MODEL buildTargetModel();
	
	public TARGET_MODEL getTargetModel(){
		if(targetModel==null)
			targetModel = buildTargetModel();
		return targetModel;
	}
	
}
