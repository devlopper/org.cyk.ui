package org.cyk.ui.api.model;

import java.util.Collection;

public interface TreeListener<NODE,MODEL extends HierarchyNode> {

	NODE createRootNode();
	
	NODE createNode(MODEL model,NODE parent);
	
	void nodeSelected(NODE node);
	
	void expandNode(NODE node);
	
	void selectNode(NODE node);
	
	NODE parentNode(NODE node);
	
	MODEL createModel(Object object);
	
	MODEL nodeModel(NODE node);
	
	Collection<Object> children(Object object);
	
	Collection<NODE> nodeChildren(NODE node);

	String label(Object data);
	
}