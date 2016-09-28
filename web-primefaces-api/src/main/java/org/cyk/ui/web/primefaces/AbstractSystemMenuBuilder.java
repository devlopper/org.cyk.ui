package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.ui.api.model.AbstractTree;
import org.primefaces.model.TreeNode;

public abstract class AbstractSystemMenuBuilder extends org.cyk.ui.api.command.menu.AbstractSystemMenuBuilder<Commandable,TreeNode,HierarchyNode,UserSession> implements Serializable {

	private static final long serialVersionUID = 6995162040038809581L;

	@Override
	protected AbstractTree<TreeNode, HierarchyNode> createNavigatorTree(UserSession userSession) {
		return new Tree();
	}
	
	/**/
	
	public static interface Listener extends AbstractSystemMenuBuilderListener<Commandable,TreeNode,HierarchyNode,UserSession> {
		
		/**/
		
		public static class Adapter extends AbstractSystemMenuBuilderListener.Adapter<Commandable,TreeNode,HierarchyNode,UserSession> implements Serializable{
			private static final long serialVersionUID = 3034803382486669232L;
			
		}
	}
}
