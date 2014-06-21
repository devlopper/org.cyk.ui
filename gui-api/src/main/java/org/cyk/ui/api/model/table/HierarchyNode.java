package org.cyk.ui.api.model.table;

import java.io.Serializable;

import org.cyk.ui.api.UIManager;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HierarchyNode implements Serializable {

	private static final long serialVersionUID = -7119597731917014456L;

	private Object data;
	private String label;
	private Boolean expanded=Boolean.TRUE;
	
	public HierarchyNode(Object data) {
		super();
		this.data = data;
		if(data!=null)
			label = UIManager.getInstance().getToStringMethod().execute(data);
	}
	
	
}
