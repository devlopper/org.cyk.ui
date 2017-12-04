package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.userinterface.hierarchy.Hierarchy;
import org.cyk.utility.common.userinterface.hierarchy.HierarchyNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class HierarchyBasedOnTreeNode extends Hierarchy.Builder.Target.Adapter.Default<DefaultTreeNode> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void initializeRoot(DefaultTreeNode hierarchy) {
		hierarchy.setData("Root");
	}
	
	@Override
	protected Object __createNode__(DefaultTreeNode hierarchy, HierarchyNode hierarchyNode, Object parent) {
		hierarchyNode.getPropertiesMap().setType(StringUtils.defaultIfBlank((String)hierarchyNode.getPropertiesMap().getType(), "default"));
		hierarchyNode.getPropertiesMap().setInclude(StringUtils.defaultIfBlank((String)hierarchyNode.getPropertiesMap().getInclude()
				, (String)PrimefacesResourcesManager.getInstance().getTreeNodeDefaultInclude()));
		DefaultTreeNode defaultTreeNode =  new DefaultTreeNode((String)hierarchyNode.getPropertiesMap().getType(), hierarchyNode, (TreeNode)parent);
		return defaultTreeNode;
	}
		
	@Override
	protected void addNode(DefaultTreeNode treeNode, Object node, Object parent) {
		//((TreeNode)parent).getChildren().add(treeNode);
	}
	
	@Override
	protected DefaultTreeNode __execute__() {
		getInput().getPropertiesMap().setTemplate(PrimefacesResourcesManager.getInstance().getHierarchyTemplate(getInput()));
		getInput().getPropertiesMap().setInclude(PrimefacesResourcesManager.getInstance().getHierarchyInclude(getInput()));
		return super.__execute__();
	}
}