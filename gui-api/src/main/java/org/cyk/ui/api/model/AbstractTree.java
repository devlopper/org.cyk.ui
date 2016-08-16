package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractTree<NODE,MODEL extends AbstractHierarchyNode> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 763364839529624006L;
	
	public static final String ROOT = "Root";
	
	@Getter protected NODE root,index;
	@Getter @Setter protected NODE selected,lastExpanded,lastCollapsed;
	@Getter @Setter protected Boolean dynamic = Boolean.TRUE,expanded=Boolean.TRUE;
	@Getter @Setter protected String consultViewId,editViewId;
	@Getter @Setter protected Boolean redirectable,expand=Boolean.TRUE,useSpecificRedirectOnNodeSelected=Boolean.TRUE;
	@Getter @Setter protected BusinessEntityInfos businessEntityInfos;
	
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
		if(Boolean.TRUE.equals(expand))
			for(Listener<NODE,MODEL> listener : treeListeners)
				listener.expandNode(index);
	}
		
	public <TYPE> void build(final Class<TYPE> aClass,Collection<TYPE> aCollection,TYPE selected){
		String rootLabel = ListenerUtils.getInstance().getString(treeListeners, new ListenerUtils.StringMethod<Listener<NODE,MODEL>>() {
			@Override
			public String execute(Listener<NODE,MODEL> listener) {
				return listener.getRootNodeLabel(aClass);
			}
		});
		build(createModel(rootLabel));
		for(TYPE element : aCollection)
			populate(element);	
		expand(selected, Boolean.TRUE);
		lastExpanded = this.selected;
	}
	
	public void expand(Object object,Boolean selected){
		//System.out.println("AbstractTree.expand() : "+object);
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
		lastExpanded = selected = node;
		for(Listener<NODE,MODEL> listener : treeListeners)
			listener.nodeSelected(node);
	}
	
	public void nodeExpanded(NODE node){
		lastExpanded = node;
		for(Listener<NODE,MODEL> listener : treeListeners)
			listener.nodeExpanded(node);
	}
	
	public void nodeCollapsed(NODE node){
		lastCollapsed = node;
		for(Listener<NODE,MODEL> listener : treeListeners)
			listener.nodeCollapsed(node);
	}
	
	@SuppressWarnings("unchecked")
	public <TYPE> TYPE selectedAs(Class<TYPE> aClass){
		Object data = nodeModel(selected).getData();
		if(data!=null && data instanceof AbstractHierarchyNode)
			data = ((AbstractHierarchyNode)data).getData();
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
		Collection<?> children = children(root);
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
	
	public Collection<?> children(Object object){
		Collection<?> collection = null;
		for(Listener<NODE,MODEL> listener : treeListeners){
			Collection<?> r = listener.children(object);
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
	
	protected Boolean isRedirectable(final NODE node){
		return ListenerUtils.getInstance().getBoolean(treeListeners, new ListenerUtils.BooleanMethod<Listener<NODE,MODEL>>() {
			@Override
			public Boolean execute(Listener<NODE,MODEL> listener) {
				return listener.isRedirectable(node);
			}
		});
	}
	
	protected Object getRedirectionObject(final NODE node){
		return ListenerUtils.getInstance().getValue(Object.class, treeListeners, new ListenerUtils.ResultMethod<Listener<NODE,MODEL>,Object>() {
			@Override
			public Object execute(Listener<NODE,MODEL> listener) {
				return listener.getRedirectionObject(node);
			}

			@Override
			public Object getNullValue() {
				return null;
			}
		});
	}
	
	protected Crud getRedirectionCrud(final NODE node){
		return ListenerUtils.getInstance().getValue(Crud.class, treeListeners, new ListenerUtils.ResultMethod<Listener<NODE,MODEL>,Crud>() {
			@Override
			public Crud execute(Listener<NODE,MODEL> listener) {
				return listener.getRedirectionCrud(node);
			}

			@Override
			public Crud getNullValue() {
				return null;
			}
		});
	}
	
	public void redirectTo(NODE node){
		if(Boolean.TRUE.equals(isRedirectable(node))){
			Object object = getRedirectionObject(node);
			Crud crud = getRedirectionCrud(node);
			String viewId = getRedirectToViewId(node,crud,object);
			if(StringUtils.isBlank(viewId)){
				logTrace("No viewId specified to redirect node {}", node);
			}else{
				Object[] parameters = getRedirectToParameters(node,crud,object);
				logTrace("Tree Redirecting to {} with parameters {}", viewId,StringUtils.join(parameters,Constant.CHARACTER_COMA.toString()));
				if(Boolean.TRUE.equals(useSpecificRedirectOnNodeSelected))
					__redirectTo__(node, viewId, parameters);	
			}
		}else{
			
		}
	}
	
	protected abstract void __redirectTo__(NODE node,String viewId,Object[] parameters);
	
	protected String getRedirectToViewId(final NODE node,final Crud crud,final Object object){
		return ListenerUtils.getInstance().getString(treeListeners, new ListenerUtils.StringMethod<Listener<NODE,MODEL>>() {
			@Override
			public String execute(Listener<NODE,MODEL> listener) {
				return listener.getRedirectToViewId(node,crud,object);
			}
			@Override
			public String getNullValue() {
				return UIManager.getInstance().getViewDynamic(CommonBusinessAction.READ, Boolean.FALSE);
			}
		});
	}
	
	protected Object[] getRedirectToParameters(final NODE node,final Crud crud,final Object object){
		return ListenerUtils.getInstance().getValue(Object[].class, treeListeners, new ListenerUtils.ResultMethod<Listener<NODE,MODEL>,Object[]>() {
			@Override
			public Object[] execute(Listener<NODE,MODEL> listener) {
				return listener.getRedirectToParameters(node,crud,object);
			}

			@Override
			public Object[] getNullValue() {
				return null;
			}
		});
	}
	
	/**/
	
	public static interface Listener<NODE,MODEL extends AbstractHierarchyNode> {

		AbstractTree<NODE, MODEL> getTree();
		
		NODE createRootNode();
		String getRootNodeLabel(Class<?> dataClass);
		NODE createNode(MODEL model,NODE parent);
		
		void nodeSelected(NODE node);
		
		void nodeExpanded(NODE node);
		void expandNode(NODE node);
		
		void nodeCollapsed(NODE node);
		void collapseNode(NODE node);
		
		void selectNode(NODE node);
		
		NODE parentNode(NODE node);
		
		MODEL createModel(Object object);
		
		MODEL nodeModel(NODE node);
		
		Boolean isLeaf(NODE node);
		
		Collection<?> children(Object object);
		
		Collection<NODE> nodeChildren(NODE node);

		String label(Object data);
		
		Boolean isRedirectable(NODE node);
		
		Object getRedirectionObject(NODE node);
		
		Crud getRedirectionCrud(NODE node);
		
		String getRedirectToViewId(NODE node,Crud crud,Object object);
		
		Object[] getRedirectToParameters(NODE node,Crud crud,Object object);
		/**/
		
		public class Adapter<NODE, MODEL extends AbstractHierarchyNode> implements Serializable, Listener<NODE, MODEL> {

			private static final long serialVersionUID = 6046223006911747854L;

			@Override
			public AbstractTree<NODE, MODEL> getTree() {
				return null;
			}
			@Override
			public String getRootNodeLabel(Class<?> dataClass) {
				return null;
			}
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
			public Collection<?> children(Object object) {
				return null;
			}

			@Override
			public Collection<NODE> nodeChildren(NODE node) {
				return null;
			}
		 
			@Override
			public String label(Object data) {
				return null;//RootBusinessLayer.getInstance().getFormatterBusiness().format(data); 
			}

			@Override
			public void nodeExpanded(NODE node) {
				
			}

			@Override
			public Boolean isLeaf(NODE node) {
				return null;
			}
			
			@Override
			public Boolean isRedirectable(NODE node) {
				return null;
			}
			
			@Override
			public Object getRedirectionObject(NODE node) {
				return null;
			}

			@Override
			public String getRedirectToViewId(NODE node,Crud crud,Object object) {
				return null;
			}

			@Override
			public Object[] getRedirectToParameters(NODE node,Crud crud,Object object) {
				return null;
			}
			
			@Override
			public Crud getRedirectionCrud(NODE node) {
				return null;
			}
			
			@Override
			public void nodeCollapsed(NODE node) {}
			
			@Override
			public void collapseNode(NODE node) {}
			
			/**/
			
			public static class Default<NODE, MODEL extends AbstractHierarchyNode> extends Adapter<NODE, MODEL> implements Serializable {
				private static final long serialVersionUID = -7748963854147708112L;
				
				@Override
				public String getRootNodeLabel(Class<?> dataClass) {
					return inject(LanguageBusiness.class).findClassLabelText(dataClass);
				}
				@Override
				public String label(Object data) {
					return RootBusinessLayer.getInstance().getFormatterBusiness().format(data); 
				}
				
				public Collection<?> children(Object object) {
					if(object instanceof AbstractDataTreeNode){
						Collection<Object> collection = new ArrayList<>();
						if(((AbstractDataTreeNode)object).getChildren()!=null)
							for(Object o : ((AbstractDataTreeNode)object).getChildren())
								collection.add(o);
						return collection;
					}
					return null;
				}
				
				@Override
				public void nodeSelected(NODE node) {
					//System.out.println("AbstractTree.Listener.Adapter.Default.nodeSelected() : "+isRedirectable(node));
					AbstractTree<NODE, MODEL> tree = getTree();
					if(Boolean.TRUE.equals(isRedirectable(node)) && tree!=null && Boolean.TRUE.equals(tree.getRedirectable()))
						tree.redirectTo(node);
					//else
					//	super.nodeSelected(node);
				}
				
			}

			
		}

	}
	
}
