package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

@Named @ViewScoped @Getter @Setter
public class LocalityListPage extends AbstractCrudManyPage<Locality> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	
	@Override
	protected void initialisation() {
		// TODO Auto-generated method stub
		super.initialisation();
		//table.setGlobalFilter(Boolean.TRUE);
		//table.setUseDataModel(Boolean.TRUE);
		table.setLazyLoad(Boolean.TRUE);
		//table.setRowCount(10);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		//table.setLazyLoad(Boolean.TRUE);
		
	}
	
}
