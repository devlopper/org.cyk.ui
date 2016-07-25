package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorParameterBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.userinterface.ClassUserInterface;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.ui.api.model.EnumerationForm;
import org.cyk.ui.web.api.WebNavigationManager;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Getter @Setter
public class Tree extends AbstractTree<TreeNode, HierarchyNode> implements Serializable {

	private static final long serialVersionUID = 763364839529624006L;
	
	private CascadeStyleSheet css = new CascadeStyleSheet();
	private Listener<TreeNode, HierarchyNode> listener;
	
	public Tree() {
		treeListeners.add(listener = new Listener.Adapter.Default<TreeNode, HierarchyNode>(){
			private static final long serialVersionUID = 1L;
			@Override
			public TreeNode createRootNode() {
				return new DefaultTreeNode(ROOT, null);
			}
			
			@Override
			public TreeNode createNode(HierarchyNode model, TreeNode parent) {
				return new DefaultTreeNode(model, parent);
			}
			
			@Override
			public HierarchyNode createModel(Object node) {
				return new HierarchyNode(node);
			}
			
			@Override
			public void expandNode(TreeNode node) {
				node.setExpanded(Boolean.TRUE);
			}
			
			@Override
			public void selectNode(TreeNode node) {
				node.setSelected(Boolean.TRUE);
			}
			
			@Override
			public void collapseNode(TreeNode node) {
				node.setExpanded(Boolean.FALSE);
			}
			
			@Override
			public TreeNode parentNode(TreeNode node) {
				return node.getParent();
			}
			
			@Override
			public Collection<TreeNode> nodeChildren(TreeNode node) {
				return node.getChildren();
			}
			
			@Override
			public HierarchyNode nodeModel(TreeNode node) {
				return (HierarchyNode) node.getData();
			}
			@Override
			public Boolean isLeaf(TreeNode node) {
				return node.isLeaf();
			}
			
			@Override
			public Object getRedirectionObject(TreeNode node) {
				EnumerationForm enumerationForm = selectedAs(EnumerationForm.class);
				return enumerationForm == null ? null : enumerationForm.getIdentifiable();
			}
			
			@Override
			public String getRedirectToViewId(TreeNode node,Crud crud,Object object) {
				if(object instanceof EnumerationForm)
					object = ((EnumerationForm)object).getIdentifiable();
				Boolean edit = Crud.isCreateOrUpdate(crud);
				HierarchyNode model = nodeModel(selected);
				String v_outcome = model == null ? (edit ? editViewId : consultViewId) : (edit ? model.getEditViewId() : model.getConsultViewId());
				if(StringUtils.isBlank(v_outcome))
					v_outcome = edit ? editViewId : consultViewId;
				//Object data = getRedirectionObject(node);
				if(object==null)
					;
				else{
					if(StringUtils.isBlank(v_outcome)){
						ClassUserInterface userInterface = UIManager.getInstance().businessEntityInfos(object.getClass()).getUserInterface();
						v_outcome = edit ? userInterface.getEditViewId() : userInterface.getConsultViewId();
					}
				}
				return v_outcome;
			}
			
			@Override
			public Object[] getRedirectToParameters(TreeNode node,Crud crud,Object object) {
				if(object instanceof EnumerationForm)
					object = ((EnumerationForm)object).getIdentifiable();
				//Object data = getRedirectionObject(node);
				Object nodeObject = ((HierarchyNode)node.getData()).getData();
				Object[] parameters = null;
				if(object==null)
					;
				else{
					parameters = new Object[]{UniformResourceLocatorParameter.CLASS,UIManager.getInstance().businessEntityInfos(object.getClass()).getIdentifier()
							,UniformResourceLocatorParameter.CRUD,UniformResourceLocatorParameterBusinessImpl.getCrudAsString(crud)
							,UniformResourceLocatorParameter.IDENTIFIABLE, Crud.CREATE.equals(crud) ? null : object
									,UIManager.getInstance().businessEntityInfos(nodeObject.getClass()).getIdentifier()
									,nodeObject instanceof AbstractIdentifiable ? ((AbstractIdentifiable)nodeObject).getIdentifier() : null};
				}
				return parameters;
			}
						
			@Override
			public Boolean isRedirectable(TreeNode node) {
				return Boolean.TRUE; //nodeModel(node).getData() instanceof AbstractIdentifiable;
			}
			
			@Override
			public AbstractTree<TreeNode, HierarchyNode> getTree() {
				return Tree.this;
			}
		});
		
		redirectable = Boolean.TRUE;
	}
	
	@Override
	protected void __redirectTo__(TreeNode node, String viewId,Object[] parameters) {
		WebNavigationManager.getInstance().redirectTo(viewId,parameters);
	}
	
	public <TYPE> void build(Class<TYPE> aClass,Collection<TYPE> aCollection,TYPE selected,String consultViewId){
		build(aClass, aCollection,selected);
		this.consultViewId = consultViewId;
	}
	
	/* Ajax behaviors */
	
	public void onNodeSelect(NodeSelectEvent event){
		nodeSelected(event.getTreeNode());
	}
	
	public void onNodeExpand(NodeExpandEvent event){
		nodeExpanded(event.getTreeNode());
	}
	
	public void onNodeCollapse(NodeCollapseEvent event){
		nodeCollapsed(event.getTreeNode());
	}
}
