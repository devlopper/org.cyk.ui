package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.ui.api.model.HierarchyNode;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WebHierarchyNode extends HierarchyNode implements Serializable {

	private static final long serialVersionUID = 4491175433342649956L;

	private CascadeStyleSheet css = new CascadeStyleSheet();
	
	public WebHierarchyNode(Object data) {
		super(data);
	}
	
}
