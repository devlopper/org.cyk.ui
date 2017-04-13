package org.cyk.ui.web.primefaces.page.nestedset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.geography.AbstractDataTreeNodeDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.ui.web.primefaces.HierarchyNode;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.Tree;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;
import org.cyk.utility.common.Constant;
import org.primefaces.model.TreeNode;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractDataTreeNodeConsultPage<DATA_TREE extends AbstractDataTreeNode,DETAILS extends AbstractDataTreeNodeDetails<DATA_TREE>> extends AbstractConsultPage<DATA_TREE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private Table<DETAILS> childrenTable;
	
	@Override @SuppressWarnings("unchecked")
	protected void consultInitialisation() {
		super.consultInitialisation();
		Class<DETAILS> detailsClass = (Class<DETAILS>) commonUtils.getClassParameterAt(getClass(), 1);
		childrenTable = (Table<DETAILS>) createDetailsTable(detailsClass, new DetailsConfigurationListener.Table.Adapter<DATA_TREE,DETAILS>(identifiableClass, detailsClass){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<DATA_TREE> getIdentifiables() {
				return getBusiness().findDirectChildrenByParent(identifiable);
			}
			
			@Override
			public Boolean isRendered(AbstractPrimefacesPage page) {
				return true;
			}
			
		});
		
		childrenTable.setShowHierarchy(Boolean.TRUE);
		//for(Object node : getBusiness().findHierarchies()){
			childrenTable.setTree(new Tree());
			childrenTable.getTree().setBusinessEntityInfos(businessEntityInfos);
			childrenTable.getTree().setUseSpecificRedirectOnNodeSelected(Boolean.FALSE);
			childrenTable.getTree().getTreeListeners().add(new AbstractTree.Listener.Adapter.Default<TreeNode,HierarchyNode>(){
				private static final long serialVersionUID = 6817293162423539828L;
				@Override
				public void nodeSelected(TreeNode node) {
					if(childrenTable.getTree().nodeModel(node).getData() instanceof AbstractIdentifiable)
						navigationManager.redirectToCrudOne((AbstractIdentifiable) childrenTable.getTree().nodeModel(node).getData(), Crud.READ, CommonBusinessAction.CONSULT); 
					else
						navigationManager.redirectToCrudMany((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz(), null);
				}
			});
			childrenTable.getTree().build(identifiableClass, commonUtils.castCollection(getBusiness().findHierarchies(),identifiableClass), identifiable);
		//}		
	}
	
	@Override
	protected String getContentTitleIdentifiableText() {
		getBusiness().setParents(identifiable);
		Collection<AbstractIdentifiable> localities = new ArrayList<>();
		if(identifiable.getParents()!=null)
			localities.addAll(identifiable.getParents());
		localities.add(identifiable);
		return StringUtils.join(localities,Constant.CHARACTER_GREATER_THAN);
	}
	
	protected AbstractDataTreeNodeBusiness<DATA_TREE> getBusiness(){
		return (AbstractDataTreeNodeBusiness<DATA_TREE>) super.getBusiness();
	}
	
}
