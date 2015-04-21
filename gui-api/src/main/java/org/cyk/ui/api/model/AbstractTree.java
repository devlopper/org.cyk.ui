package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractTree<NODE,MODEL extends HierarchyNode> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 763364839529624006L;
	
	@Getter protected NODE root,index;
	@Getter @Setter protected NODE selected;
	
	@Getter protected Collection<TreeListener<NODE,MODEL>> treeListeners = new ArrayList<>();
		
	public void build(MODEL model){
		for(TreeListener<NODE,MODEL> listener : treeListeners){
			NODE r = listener.createRootNode();
			if(r!=null)
				root = r;
		}
		
		if(model==null)
			index = root;
		else
			index = createNode(model, root);
		
		for(TreeListener<NODE,MODEL> listener : treeListeners)
			listener.expandNode(index);
	}
	
	public void expand(Object object,Boolean selected){
		if(object==null)
			return;
		NODE node = nodeOf(object);
		if(node==null){
			//System.out.println("PrimefacesTree.expand() : No node to select : "+object);
			return;
		}
		if(Boolean.TRUE.equals(selected)){
			//WebHierarchyNode webHierarchyNode = (WebHierarchyNode) node.getData();
			//webHierarchyNode.getCss().addClass("cyk-ui-tree-node-selected ui-state-highlight");
			for(TreeListener<NODE,MODEL> listener : treeListeners)
				listener.selectNode(node);
		}
		
		do{
			for(TreeListener<NODE,MODEL> listener : treeListeners)
				listener.expandNode(node);
			node = parentNode(node);
		}while(node!=null);
	}
	
	public void nodeSelected(NODE node){
		selected = node;
		for(TreeListener<NODE,MODEL> listener : treeListeners)
			listener.nodeSelected(node);
	}
	
	@SuppressWarnings("unchecked")
	public <TYPE> TYPE selectedAs(Class<TYPE> aClass){
		Object data = nodeModel(selected).getData();
		if(data==null)
			return null;
		return aClass.isAssignableFrom(data.getClass())?(TYPE)data:null;
	}
	

	
	public void populate(Object object){
		populate(object, index);
	}
	
	private void populate(Object root,NODE node){
		@SuppressWarnings("unchecked")
		NODE childNode = createNode(createModel((NODE) root),node);
		Collection<Object> children = children(root);
		if(children!=null)
			for(Object child : children)
				populate(child, childNode);
	}
	
	public NODE nodeOf(Object object){
		if(object==null)
			return null;
		for(NODE child : nodeChildren(root)){
			NODE n = nodeOf(child, object);
			if(n!=null)
				return n;
		}
		return null;
	}
	
	private NODE nodeOf(NODE node,Object object){
		MODEL model = nodeModel(node);
		
		if(object.equals(model.getData()))
			return node;
		
		for(NODE child : nodeChildren(node)){
			NODE n = nodeOf(child, object);
			if(n!=null)
				return n;
		}
		
		return null;
	}
	
	private NODE createNode(MODEL model,NODE parent){
		NODE node = null;
		for(TreeListener<NODE,MODEL> listener : treeListeners){
			NODE r = listener.createNode(model,parent);
			if(r!=null)
				node = r;
		}
		
		for(TreeListener<NODE,MODEL> listener : treeListeners){
			String label = listener.label(model.getData());
			if(StringUtils.isNotBlank(label))
				model.setLabel(label);
		}
		
		return node;
	}
	
	private NODE parentNode(NODE node){
		NODE parent = null;
		for(TreeListener<NODE,MODEL> listener : treeListeners){
			NODE r = listener.parentNode(node);
			if(r!=null)
				parent = r;
		}
		return parent;
	}
	
	private MODEL createModel(Object node){
		MODEL model = null;
		for(TreeListener<NODE,MODEL> listener : treeListeners){
			MODEL r = listener.createModel(node);
			if(r!=null)
				model = r;
		}
		model.setExpanded(true);
		return model;
	}
	
	public MODEL nodeModel(NODE node){
		MODEL data = null;
		for(TreeListener<NODE,MODEL> listener : treeListeners){
			MODEL r = listener.nodeModel(node);
			if(r!=null)
				data = r;
		}
		return data;
	}
	
	private Collection<Object> children(Object object){
		Collection<Object> collection = null;
		for(TreeListener<NODE,MODEL> listener : treeListeners){
			Collection<Object> r = listener.children(object);
			if(r!=null)
				collection = r;
		}
		return collection;
	}
	
	private Collection<NODE> nodeChildren(NODE node){
		Collection<NODE> collection = null;
		for(TreeListener<NODE,MODEL> listener : treeListeners){
			Collection<NODE> r = listener.nodeChildren(node);
			if(r!=null)
				collection = r;
		}
		return collection;
	}
	
}
