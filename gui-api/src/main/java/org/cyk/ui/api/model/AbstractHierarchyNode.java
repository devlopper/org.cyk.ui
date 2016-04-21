package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.ui.api.Icon;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractHierarchyNode implements Serializable {

	private static final long serialVersionUID = -7119597731917014456L;

	protected Object data;
	protected String label;
	protected Boolean expanded=Boolean.TRUE;
	protected String consultViewId,editViewId;
	protected Icon collapsedIcon,expandedIcon;
	
	public AbstractHierarchyNode(Object data) {
		super();
		this.data = data;
		if(data!=null)
			this.label = RootBusinessLayer.getInstance().getFormatterBusiness().format(data);
	}
	
	public AbstractHierarchyNode(Object data, String label, Boolean expanded) {
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
