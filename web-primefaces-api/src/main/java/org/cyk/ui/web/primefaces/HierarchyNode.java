package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.api.WebHierarchyNode;

@Getter @Setter
public class HierarchyNode extends WebHierarchyNode implements Serializable {

	private static final long serialVersionUID = 4491175433342649956L;

	public HierarchyNode(Object data) {
		super(data);
	}

	public HierarchyNode(Object data, String label, Boolean expanded) {
		super(data, label, expanded);
	}

}