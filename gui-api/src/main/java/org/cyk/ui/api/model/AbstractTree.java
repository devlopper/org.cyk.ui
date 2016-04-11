package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractTree<NODE,MODEL extends HierarchyNode> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 763364839529624006L;
	
	public static final String ROOT = "Root";
	
	@Getter protected NODE root,index;
	@Getter @Setter protected NODE selected,lastExpanded;
	@Getter @Setter protected Boolean dynamic = Boolean.TRUE,expanded=Boolean.TRUE;
	
	@Getter protected Collection<Listener<NODE,MODEL>> treeListeners = new ArrayList<>();
	
	public AbstractTree() {
		treeListeners.add(new Listener.Adapter.Default<NODE,MODEL>());
	}
	
	public void build(MODEL model){
		for(Listener<NODE,MODEL> listener : treeListeners){
			NODE r = listener.createRootNode();
			if(r!=null)
				root = r;
		}
		
		if(model==null)
			index = root;
		else{
			index = createNode(model, root);
		}
		for(Listener<NODE,MODEL> listener : treeListeners)
			listener.expandNode(index);
	}
	
	public <TYPE> void build(Class<TYPE> aClass,Collection<TYPE> aCollection,TYPE selected){
		build(createModel(UIManager.getInstance().getLanguageBusiness().findClassLabelText(aClass)));
		for(TYPE element : aCollection)
			populate(element);	
		expand(selected, Boolean.TRUE);
		lastExpanded = this.selected;
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
			for(Listener<NODE,MODEL> listener : treeListeners)
				listener.selectNode(node);
			this.selected = node;
		}
		
		do{
			for(Listener<NODE,MODEL> listener : treeListeners)
				listener.expandNode(node);
			node = parentNode(node);
		}while(node!=null);
	}
	
	public void nodeSelected(NODE node){
		//System.out.println("AbstractTree.nodeSelected() : "+node);
		lastExpanded = selected = node;
		for(Listener<NODE,MODEL> listener : treeListeners)
			listener.nodeSelected(node);
	}
	
	public void nodeExpanded(NODE node){
		lastExpanded = node;
		for(Listener<NODE,MODEL> listener : treeListeners)
			listener.nodeExpanded(node);
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
		for(Listener<NODE,MODEL> listener : treeListeners){
			NODE r = listener.createNode(model,parent);
			if(r!=null)
				node = r;
		}
		/*
		if(parent!=root)
			for(Listener<NODE,MODEL> listener : treeListeners){
				String label = listener.label(model.getData());
				if(StringUtils.isNotBlank(label))
					model.setLabel(label);
			}
		*/
		return node;
	}
	
	private NODE parentNode(NODE node){
		NODE parent = null;
		for(Listener<NODE,MODEL> listener : treeListeners){
			NODE r = listener.parentNode(node);
			if(r!=null)
				parent = r;
		}
		return parent;
	}
	
	private MODEL createModel(Object node){
		MODEL model = null;
		for(Listener<NODE,MODEL> listener : treeListeners){
			MODEL r = listener.createModel(node);
			if(r!=null)
				model = r;
		}
		model.setExpanded(true);
		return model;
	}
	
	public MODEL nodeModel(NODE node){
		MODEL data = null;
		for(Listener<NODE,MODEL> listener : treeListeners){
			MODEL r = listener.nodeModel(node);
			if(r!=null)
				data = r;
		}
		return data;
	}
	
	public Collection<Object> children(Object object){
		Collection<Object> collection = null;
		for(Listener<NODE,MODEL> listener : treeListeners){
			Collection<Object> r = listener.children(object);
			if(r!=null)
				collection = r;
		}
		return collection;
	}
	
	public Collection<NODE> nodeChildren(NODE node){
		Collection<NODE> collection = null;
		for(Listener<NODE,MODEL> listener : treeListeners){
			Collection<NODE> r = listener.nodeChildren(node);
			if(r!=null)
				collection = r;
		}
		return collection;
	}
	
	public Boolean isLeaf(NODE node){
		Boolean isLeaf = null;
		for(Listener<NODE,MODEL> listener : treeListeners){
			Boolean v = listener.isLeaf(node);
			if(v!=null)
				isLeaf = v;
		}
		return isLeaf;
	}
	
	public Collection<NODE> getMobileNodes(){
		Collection<NODE> collection = new ArrayList<NODE>();
		Collection<NODE> selecteds = new ArrayList<NODE>();
		//System.out.println("AbstractTree.getMobileNodes() : "+lastExpanded);
		if(lastExpanded==null){
			selecteds.addAll(nodeChildren(root));
		}else{
			selecteds.add(lastExpanded);
			NODE parent = parentNode(lastExpanded);
			if(parent!=root)
				collection.add(parent);
		}
		for(NODE node : selecteds){
			collection.add(node);
			collection.addAll(nodeChildren(node));
		}
		
		return collection;
	}
	
	public Boolean expandable(NODE node){
		//System.out.println("AbstractTree.expandable() "+node+"/"+lastExpanded);
		if(lastExpanded==null)
			return !Boolean.TRUE.equals(isLeaf(node)) && parentNode(node)!=root;
		else
			return !Boolean.TRUE.equals(isLeaf(node)) && node != lastExpanded;
	}
	
	/**/
	
	public static interface Listener<NODE,MODEL extends HierarchyNode> {

		NODE createRootNode();
		
		NODE createNode(MODEL model,NODE parent);
		
		void nodeSelected(NODE node);
		
		void nodeExpanded(NODE node);
		
		void expandNode(NODE node);
		
		void selectNode(NODE node);
		
		NODE parentNode(NODE node);
		
		MODEL createModel(Object object);
		
		MODEL nodeModel(NODE node);
		
		Boolean isLeaf(NODE node);
		
		Collection<Object> children(Object object);
		
		Collection<NODE> nodeChildren(NODE node);

		String label(Object data);
		
		/**/
		
		public class Adapter<NODE, MODEL extends HierarchyNode> implements Serializable, Listener<NODE, MODEL> {

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

			@Override
			public void nodeExpanded(NODE node) {
				
			}

			@Override
			public Boolean isLeaf(NODE node) {
				return null;
			}

			/**/
			
			public static class Default<NODE, MODEL extends HierarchyNode> extends Adapter<NODE, MODEL> implements Serializable {
				private static final long serialVersionUID = -7748963854147708112L;
				
				public Collection<Object> children(Object object) {
					if(object instanceof AbstractDataTreeNode){
						Collection<Object> collection = new ArrayList<>();
						if(((AbstractDataTreeNode)object).getChildren()!=null)
							for(Object o : ((AbstractDataTreeNode)object).getChildren())
								collection.add(o);
						return collection;
					}
					return null;
				}
				
			}
		}

	}
	
}
