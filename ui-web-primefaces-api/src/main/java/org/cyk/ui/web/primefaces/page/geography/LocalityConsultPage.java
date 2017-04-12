package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.business.impl.geography.LocalityDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.ui.web.primefaces.HierarchyNode;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.Tree;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;
import org.cyk.utility.common.Constant;
import org.primefaces.model.TreeNode;

@Named @ViewScoped @Getter @Setter
public class LocalityConsultPage extends AbstractConsultPage<Locality> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private Tree tree;
	private Table<LocalityDetails> childrenTable;
	
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		childrenTable = (Table<LocalityDetails>) createDetailsTable(LocalityDetails.class, new DetailsConfigurationListener.Table.Adapter<Locality,LocalityDetails>(Locality.class, LocalityDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<Locality> getIdentifiables() {
				Class<Locality> l = Locality.class;
				AbstractDataTreeNodeBusiness<Locality> b = (AbstractDataTreeNodeBusiness<Locality>) inject(BusinessInterfaceLocator.class).injectTyped(l);
				return b.findByParent(identifiable);
			}
			
			@Override
			public Boolean isRendered(AbstractPrimefacesPage page) {
				return true;
			}
			
		});
		
		childrenTable.setShowHierarchy(Boolean.TRUE);
		@SuppressWarnings({ "rawtypes" })
		AbstractDataTreeNodeBusiness business = (AbstractDataTreeNodeBusiness) BusinessInterfaceLocator.getInstance()
			.injectTyped((Class<AbstractIdentifiable>) businessEntityInfos.getClazz());
		
		for(Object node : business.findHierarchies()){
			childrenTable.setTree(new Tree());
			childrenTable.getTree().setBusinessEntityInfos(businessEntityInfos);
			childrenTable.getTree().setUseSpecificRedirectOnNodeSelected(Boolean.FALSE);
			childrenTable.getTree().getTreeListeners().add(new AbstractTree.Listener.Adapter.Default<TreeNode,HierarchyNode>(){
				private static final long serialVersionUID = 6817293162423539828L;
				@SuppressWarnings("unchecked")
				@Override
				public void nodeSelected(TreeNode node) {
					if(childrenTable.getTree().nodeModel(node).getData() instanceof AbstractIdentifiable)
						navigationManager.redirectToCrudOne((AbstractIdentifiable) childrenTable.getTree().nodeModel(node).getData(), Crud.READ, CommonBusinessAction.CONSULT); 
					else
						navigationManager.redirectToCrudMany((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz(), null);
				}
			});
			Class<Locality> lc = (Class<Locality>) businessEntityInfos.getClazz();
			childrenTable.getTree().build(lc, commonUtils.castCollection(business.findHierarchies(),Locality.class), identifiable);
			//childrenTable.getTree().populate(node);
		}
		//childrenTable.setMaster((AbstractIdentifiable) childrenTable.getReferenceFromHierarchy(childrenTable.getMaster(),childrenTable.getHierarchyData()));
				
	}
	
	@Override
	protected String getContentTitleIdentifiableText() {
		inject(LocalityBusiness.class).setParents(identifiable);
		Collection<AbstractIdentifiable> localities = new ArrayList<>();
		if(identifiable.getParents()!=null)
			localities.addAll(identifiable.getParents());
		localities.add(identifiable);
		return StringUtils.join(localities,Constant.CHARACTER_GREATER_THAN);
	}
	
}
