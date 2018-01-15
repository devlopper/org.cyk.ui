package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.hierarchy.Hierarchy;
import org.cyk.utility.common.userinterface.hierarchy.HierarchyNode;
import org.primefaces.model.DefaultTreeNode;

import lombok.Getter;
import lombok.Setter;


public class HierarchyBasedOnTreeNode extends Hierarchy.Builder.Target.Adapter.Default<HierarchyBasedOnTreeNode.Node> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*@Override
	protected DefaultTreeNode instanciateOutput() {
		return new DefaultTreeNode(){
			private static final long serialVersionUID = 1L;
			
			
			
		};
	}*/
	
	@Override
	protected void initializeRoot(Node hierarchy) {
		hierarchy.setData("Root");
	}
	
	@Override
	protected Object __createNode__(Node hierarchy, HierarchyNode hierarchyNode, Object parent) {
		hierarchyNode.getPropertiesMap().setType(StringUtils.defaultIfBlank((String)hierarchyNode.getPropertiesMap().getType(), "default"));
		hierarchyNode.getPropertiesMap().setInclude(StringUtils.defaultIfBlank((String)hierarchyNode.getPropertiesMap().getInclude()
				, (String)PrimefacesResourcesManager.getInstance().getTreeNodeDefaultInclude()));
		Node defaultTreeNode =  new Node((String)hierarchyNode.getPropertiesMap().getType(), hierarchyNode, (org.primefaces.model.TreeNode)parent,hierarchyNode);
		return defaultTreeNode;
	}
		
	@Override
	protected void addNode(Node treeNode, Object node, Object parent) {
		//((TreeNode)parent).getChildren().add(treeNode);
	}
	
	@Override
	protected Node __execute__() {
		getInput().getPropertiesMap().setTemplate(PrimefacesResourcesManager.getInstance().getHierarchyTemplate(getInput()));
		getInput().getPropertiesMap().setInclude(PrimefacesResourcesManager.getInstance().getHierarchyInclude(getInput()));
		return super.__execute__();
	}
	
	/**/
	
	@Getter @Setter
	public static class Node extends DefaultTreeNode implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private HierarchyNode hierarchyNode;
		
		public Node() {}
		
		public Node(String type, Object data, org.primefaces.model.TreeNode parent, HierarchyNode hierarchyNode) {
			super(type, data, parent);
			this.hierarchyNode = hierarchyNode;
		}
		
		@Override
		public List<org.primefaces.model.TreeNode> getChildren() {
			if(hierarchyNode==null){
				return super.getChildren();
			}else{
				System.out.println("HierarchyBasedOnTreeNode.Node.getChildren() : "+hierarchyNode.getIsChildrenLoaded());
				if(Boolean.TRUE.equals(hierarchyNode.getIsChildrenLoaded())){
					return super.getChildren();
				}else{
					Collection<Object> collection = InstanceHelper.getInstance().getHierarchyChildren(hierarchyNode.getPropertiesMap().getValue());
					List<org.primefaces.model.TreeNode> nodes = new ArrayList<>();
					if(CollectionHelper.getInstance().isNotEmpty(collection)){
						hierarchyNode.addNode(collection,1l);
						for(Component index : hierarchyNode.getChildren().getElements()){
							if(index instanceof HierarchyNode){
								Node defaultTreeNode =  new Node((String)index.getPropertiesMap().getType(), index, this,(HierarchyNode)index);
								nodes.add(defaultTreeNode);
							}
						}
					}
					return nodes;
				}
			}
		}
		
		@Override
		public boolean isLeaf() {
			return hierarchyNode.getNumberOfChildren() == 0;
		}

		
	}
}