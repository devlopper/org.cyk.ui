package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.web.api.WebHierarchyNode;
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
	
	public PrimefacesTree(WebHierarchyNode node) {
		if(node==null)
			index = root;
		else
			index = new DefaultTreeNode(node, root);
		index.setExpanded(true);
	}
	
	public void expand(Object object,Boolean selected){
		if(object==null)
			return;
		TreeNode node = nodeOf(object);
		if(node==null){
			System.out.println("PrimefacesTree.expand() : No node to select : "+object);
			return;
		}
		if(Boolean.TRUE.equals(selected)){
			//WebHierarchyNode webHierarchyNode = (WebHierarchyNode) node.getData();
			//webHierarchyNode.getCss().addClass("cyk-ui-tree-node-selected ui-state-highlight");
			node.setSelected(true);
		}
		do{
			node.setExpanded(true);
			node = node.getParent();
		}while(node!=null);
	}
	
	public void onNodeSelect(NodeSelectEvent event) {
		if(onNodeSelect!=null)
			onNodeSelect.execute(((WebHierarchyNode) event.getTreeNode().getData()).getData());
    }
	
	public void populate(Object object){
		populate(object, index);
	}
	
	private void populate(Object root,TreeNode node){
		TreeNode childNode = new DefaultTreeNode(new WebHierarchyNode(root), node);
		Collection<Object> children = (Collection<Object>) CHILDREN.execute(root);
		if(children!=null)
			for(Object child : children)
				populate(child, childNode);
	}
	
	public TreeNode nodeOf(Object object){
		if(object==null)
			return null;
		for(TreeNode child : root.getChildren()){
			TreeNode n = nodeOf(child, object);
			if(n!=null)
				return n;
		}
		return null;
	}
	
	private TreeNode nodeOf(TreeNode root,Object object){
		if(object.equals(((WebHierarchyNode) root.getData()).getData()))
			return root;
		for(TreeNode child : root.getChildren()){
			TreeNode n = nodeOf(child, object);
			if(n!=null)
				return n;
		}
		
		return null;
	}
	
	public static AbstractMethod<Collection<Object>, Object> CHILDREN = new AbstractMethod<Collection<Object>, Object>() {
		private static final long serialVersionUID = 6739817055207710222L;
		@SuppressWarnings("rawtypes")
		@Override
		protected Collection __execute__(Object parameter) {
			if(parameter instanceof AbstractDataTreeNode)
				return ((AbstractDataTreeNode)parameter).getChildren();
			return null;
		}
	};
}
