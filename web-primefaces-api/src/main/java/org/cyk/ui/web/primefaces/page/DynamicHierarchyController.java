package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.ui.web.primefaces.PrimefacesHierarchycalData;

@Named
@ViewScoped
@Getter
@Setter
public class DynamicHierarchyController extends AbstractDynamicBusinessEntityPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private DataTreeTypeBusiness dataTreeTypeBusiness;
	
	private PrimefacesHierarchycalData<DataTreeType> hierarchycalData;

	//@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		//hierarchycalData=(PrimefacesHierarchycalData<DataTreeType>) hierarchyInstance(businessEntityInfos.getClazz());
		//for(DataTreeType dataTreeType : dataTreeTypeBusiness.findHierarchies())
		//	hierarchycalData.getData().add(dataTreeType);
		
	}
	

}
