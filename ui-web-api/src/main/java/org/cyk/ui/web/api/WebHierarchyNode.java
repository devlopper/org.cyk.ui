package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;
import org.cyk.ui.api.model.AbstractHierarchyNode;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WebHierarchyNode extends AbstractHierarchyNode implements Serializable {

	private static final long serialVersionUID = 4491175433342649956L;

	protected CascadeStyleSheet css = new CascadeStyleSheet();
	
	public WebHierarchyNode(Object data) {
		super(data);
	}

	public WebHierarchyNode(Object data, String label, Boolean expanded) {
		super(data, label, expanded);
	}
	
}
