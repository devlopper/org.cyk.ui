package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.geography.LocationType;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LocationTypeEditPage extends AbstractCrudOnePage<LocationType> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
	
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<LocationType> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		
		
	}
	
}
