package org.cyk.ui.web.primefaces.test;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.PrimefacesTable;

@Named
@ViewScoped
@Getter
@Setter
public class LocalityListController extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private PrimefacesTable<Locality> dataTable;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		dataTable =  tableInstance(Locality.class);
		dataTable.setEditable(true);
		
		Locality l = new Locality(null, null, "L1");l.setName("Ole");l.setAbbreviation("MA");
		dataTable.addRow(l);
		
		l = new Locality(null, null, "L2");l.setName("Pole");l.setAbbreviation("MA");
		dataTable.addRow(l);
		
		l = new Locality(null, null, "L3");l.setName("Babi");l.setAbbreviation("MA");
		dataTable.addRow(l);
		
		/*
		dataTable.getAddCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -2421175279479434675L;
			@Override
			protected Object __execute__(Object parameter) {
				Locality l = new Locality(null, null, "L3");l.setName("Babi");
				dataTable.addRow(l);
				messageManager.message(
						SeverityType.INFO,new Text("Recap",false),new Text("Something to add",false))
						.showDialog();
				return null;
			}
		});
		*/
	}
	


}
