package org.cyk.ui.web.primefaces.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.ui.api.model.table.CrudDataTable;
import org.cyk.ui.web.primefaces.AbstractPrimefacesWebPage;
import org.cyk.ui.web.primefaces.PrimefacesForm;

@Named
@ViewScoped
@Getter
@Setter
public class LocalityListController extends AbstractPrimefacesWebPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private PrimefacesForm myForm;
	private CrudDataTable<Locality> dataTable = new CrudDataTable<>();		
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		List<Locality> localities = new ArrayList<>();
		
		Locality l = new Locality(null, null, "L1");l.setName("Ole");
		localities.add(l);
		
		l = new Locality(null, null, "L2");l.setName("Pole");
		localities.add(l);
		
		l = new Locality(null, null, "L3");l.setName("Babi");
		localities.add(l);
		
		dataTable.load(localities);		
		
	}
	


}
