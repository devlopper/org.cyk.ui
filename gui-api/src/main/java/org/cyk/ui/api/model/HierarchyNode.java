package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.business.impl.RootBusinessLayer;

@Getter @Setter @NoArgsConstructor
public class HierarchyNode implements Serializable {

	private static final long serialVersionUID = -7119597731917014456L;

	private Object data;
	private String label;
	private Boolean expanded=Boolean.TRUE;
	private String consultViewId;
	
	public HierarchyNode(Object data) {
		super();
		this.data = data;
		if(data!=null)
			this.label = RootBusinessLayer.getInstance().getFormatterBusiness().format(data); //UIManager.getInstance().getLanguageBusiness().findObjectLabelText(data);
	}
	
	public HierarchyNode(Object data, String label, Boolean expanded) {
		super();
		this.data = data;
		this.label = label;
		this.expanded = expanded;
	}
	
	@Override
	public String toString() {
		return label;
	}

}
