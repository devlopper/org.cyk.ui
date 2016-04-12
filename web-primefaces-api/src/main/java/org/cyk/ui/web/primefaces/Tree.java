package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.ui.web.api.WebHierarchyNode;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.utility.common.Constant;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Getter @Setter
public class Tree extends AbstractTree<TreeNode, WebHierarchyNode> implements Serializable {

	private static final long serialVersionUID = 763364839529624006L;
	
	private CascadeStyleSheet css = new CascadeStyleSheet();
	private Listener<TreeNode, WebHierarchyNode> listener;
	
	public Tree() {
		treeListeners.add(listener = new Listener.Adapter.Default<TreeNode, WebHierarchyNode>(){
			private static final long serialVersionUID = 1L;
			@Override
			public TreeNode createRootNode() {
				return new DefaultTreeNode(ROOT, null);
			}
			
			@Override
			public TreeNode createNode(WebHierarchyNode model, TreeNode parent) {
				return new DefaultTreeNode(model, parent);
			}
			
			@Override
			public WebHierarchyNode createModel(Object node) {
				return new WebHierarchyNode(node);
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
			public TreeNode parentNode(TreeNode node) {
				return node.getParent();
			}
			
			@Override
			public Collection<TreeNode> nodeChildren(TreeNode node) {
				return node.getChildren();
			}
			
			@Override
			public WebHierarchyNode nodeModel(TreeNode node) {
				return (WebHierarchyNode) node.getData();
			}
			@Override
			public Boolean isLeaf(TreeNode node) {
				return node.isLeaf();
			}
			
			@Override
			public void redirect(TreeNode node) {
				WebHierarchyNode model = nodeModel(selected);
				String v_outcome = model == null ? consultViewId : model.getConsultViewId();
				if(StringUtils.isBlank(v_outcome))
					v_outcome = consultViewId;
				Object data = selectedAs(AbstractIdentifiable.class);
				Object[] parameters = null;
				if(data==null)
					;
				else{
					if(StringUtils.isBlank(v_outcome))
						v_outcome = UIManager.getInstance().businessEntityInfos(data.getClass()).getUserInterface().getConsultViewId();
					parameters = new Object[]{UIManager.getInstance().getClassParameter(),UIManager.getInstance().businessEntityInfos(data.getClass()).getIdentifier()
							,UIManager.getInstance().getIdentifiableParameter(), data};
				}
				logTrace("Tree Redirecting to {} with parameters {}", v_outcome,StringUtils.join(parameters,Constant.CHARACTER_COMA.toString()));
				WebNavigationManager.getInstance().redirectTo(v_outcome,parameters);
			}
			
			@Override
			public Boolean isRedirectable(TreeNode node) {
				return Boolean.TRUE; //nodeModel(node).getData() instanceof AbstractIdentifiable;
			}
		});
	}

	public void onNodeSelect(NodeSelectEvent event){
		nodeSelected(event.getTreeNode());
	}
	
	public <TYPE> void build(Class<TYPE> aClass,Collection<TYPE> aCollection,TYPE selected,String consultViewId){
		build(aClass, aCollection,selected);
		this.consultViewId = consultViewId;
	}
		
}
