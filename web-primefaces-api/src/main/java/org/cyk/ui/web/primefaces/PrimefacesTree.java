package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.ui.api.model.table.HierarchyNode;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Getter @Setter
public class PrimefacesTree extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 763364839529624006L;
	
	private TreeNode root,selected,index;
	private AbstractMethod<Object, Object> onNodeSelect;

	{
		root = new DefaultTreeNode("Root", null);
	}
	
	public PrimefacesTree() {
		this(null);
	}
	
	public PrimefacesTree(HierarchyNode node) {
		if(node==null)
			index = root;
		else
			index = new DefaultTreeNode(node, root);
	}
	
	public void onNodeSelect(NodeSelectEvent event) {
		if(onNodeSelect!=null)
			onNodeSelect.execute(((HierarchyNode) event.getTreeNode().getData()).getData());
    }
	
	public void populate(Object object){
		populate(object, index);
	}
	
	private void populate(Object root,TreeNode node){
		TreeNode childNode = new DefaultTreeNode(new HierarchyNode(root), node);
		Collection<Object> children = (Collection<Object>) CHILDREN.execute(root);
		if(children!=null)
			for(Object child : children)
				populate(child, childNode);
	}
	
	public static AbstractMethod<Collection<Object>, Object> CHILDREN = new AbstractMethod<Collection<Object>, Object>() {
		private static final long serialVersionUID = 6739817055207710222L;
		@SuppressWarnings("rawtypes")
		@Override
		protected Collection __execute__(Object parameter) {
			if(parameter instanceof DataTreeType)
				return ((DataTreeType)parameter).getChildren();
			return null;
		}
	};
}
