package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.table.Table.UsedFor;
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
		table = (PrimefacesTable<AbstractIdentifiable>) tableInstance(businessEntityInfos.getClazz(),UsedFor.ENTITY_INPUT,null);
		table.setEditable(true);
		table.setMaster(identifiable);
		table.fetchData();
		/*
		if(AbstractDataTreeNode.class.isAssignableFrom(businessEntityInfos.getClazz())){
			@SuppressWarnings("rawtypes")
			AbstractDataTreeNodeBusiness bean = (AbstractDataTreeNodeBusiness) BusinessLocator.getInstance().locate((Class<AbstractIdentifiable>) businessEntityInfos.getClazz());
			table.handle(bean);
		}else{
			table.addRow(genericBusiness.use((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz()).find().all());	
		}
		*/
		/*
		table.getSaveRowCommand().getCommand().setAfterFailureMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -4698491663673906259L;
			@Override
			protected Object __execute__(Object parameter) {
				messageDialogOkButtonOnClick="clickEditButtonRow('"+table.getUpdateStyleClass()+"','"+(table.getLastEditedRowIndex()+1)+"');";
				return null;
			}
		});*/
		
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return table.getShowHierarchy();
	}

}
