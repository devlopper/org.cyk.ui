package org.cyk.ui.web.primefaces.dynamic;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.ui.web.primefaces.PrimefacesTable;
import org.cyk.utility.common.AbstractMethod;

@Named
@ViewScoped
@Getter
@Setter
public class DynamicTableController extends AbstractDynamicBusinessEntityPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private DataTreeTypeBusiness dataTreeTypeBusiness;
	
	private AbstractIdentifiable master;
	private PrimefacesTable<AbstractIdentifiable> table;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		table = (PrimefacesTable<AbstractIdentifiable>) tableInstance(businessEntityInfos.getClazz());
		table.setEditable(true);
		master = identifiableFromRequestParameter( (Class<AbstractIdentifiable>)businessEntityInfos.getClazz());
		
		if(DataTreeType.class.isAssignableFrom(businessEntityInfos.getClazz())){
			if(master==null)
				for(DataTreeType dataTreeType : dataTreeTypeBusiness.findHierarchies())
					table.addRow(dataTreeType);
			else{
				dataTreeTypeBusiness.findHierarchy((DataTreeType) master);
				for(DataTreeType dataTreeType : ((DataTreeType)master).getChildren())
					table.addRow(dataTreeType);
			}
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

}
