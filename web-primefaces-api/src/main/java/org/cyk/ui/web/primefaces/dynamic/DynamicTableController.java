package org.cyk.ui.web.primefaces.dynamic;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.ui.web.primefaces.PrimefacesTable;
import org.cyk.utility.common.AbstractMethod;

@Named
@ViewScoped
@Getter
@Setter
public class DynamicTableController extends AbstractDynamicBusinessEntityPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private PrimefacesTable<AbstractIdentifiable> table;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		table = (PrimefacesTable<AbstractIdentifiable>) tableInstance(businessEntityInfos.getClazz());
		table.setEditable(true);
		table.setMaster(/*identifiableFromRequestParameter( (Class<AbstractIdentifiable>)businessEntityInfos.getClazz())*/ identifiable);
		if(DataTreeType.class.isAssignableFrom(businessEntityInfos.getClazz())){
			new TreeHandler<DataTreeTypeBusiness, DataTreeType>(dataTreeTypeBusiness,(DataTreeType)table.getMaster()).handle();
		}else if(AbstractDataTree.class.isAssignableFrom(businessEntityInfos.getClazz())){
			new TreeHandler<AbstractDataTreeBusiness<AbstractDataTree<DataTreeType>,DataTreeType>, AbstractDataTree<DataTreeType>>(
					AbstractBusinessLayer.findDataTreeBusinessBean((Class<AbstractDataTree<DataTreeType>>) businessEntityInfos.getClazz()),
					(AbstractDataTree<DataTreeType>)table.getMaster()).handle();
		}else{
			table.addRow(genericBusiness.use((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz()).find().all());	
		}
		
		table.getSaveRowCommand().setAfterFailureMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -4698491663673906259L;
			@Override
			protected Object __execute__(Object parameter) {
				messageDialogOkButtonOnClick="clickEditButtonRow('"+(table.getLastEditedRowIndex()+1)+"');";
				return null;
			}
		});
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return table.getShowHierarchy();
	}
	
	private class TreeHandler<B extends AbstractDataTreeNodeBusiness<T>,T extends AbstractDataTreeNode> implements Serializable{
		
		private static final long serialVersionUID = 7193929565231451122L;
		private B business;
		private T master;
		
		private TreeHandler(B business, T master) {
			super();
			this.business = business;
			this.master = master;
		}

		public void handle(){
			if(master==null)
				for(T node : business.findHierarchies())
					table.addRow(node);
			else{
				business.findHierarchy(master);
				if(master.getChildren()!=null)
					for(AbstractDataTreeNode node : master.getChildren())
						table.addRow(node);
				
			}
			for(T node : business.findHierarchies())
				table.getHierarchyData().add(node);
		}
		
	}

}
