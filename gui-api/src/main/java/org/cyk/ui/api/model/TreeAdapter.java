package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.ui.api.UIManager;

public class TreeAdapter<NODE, MODEL extends HierarchyNode> implements Serializable, TreeListener<NODE, MODEL> {

	private static final long serialVersionUID = 6046223006911747854L;

	@Override
	public NODE createRootNode() {
		return null;
	}

	@Override
	public NODE createNode(MODEL model, NODE parent) {
		return null;
	}

	@Override
	public void nodeSelected(NODE node) {
		
	}

	@Override
	public void expandNode(NODE node) {
		
	}
	
	@Override
	public void selectNode(NODE node) {
		
	}

	@Override
	public NODE parentNode(NODE node) {
		return null;
	}

	@Override
	public MODEL createModel(Object object) {
		return null;
	}

	@Override
	public MODEL nodeModel(NODE node) {
		return null;
	}

	@Override
	public Collection<Object> children(Object object) {
		return null;
	}

	@Override
	public Collection<NODE> nodeChildren(NODE node) {
		return null;
	}
 
	@Override
	public String label(Object data) {
		return UIManager.getInstance().getLanguageBusiness().findObjectLabelText(data); 
	}

}
