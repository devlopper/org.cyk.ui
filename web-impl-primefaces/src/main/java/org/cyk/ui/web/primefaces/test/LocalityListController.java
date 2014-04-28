package org.cyk.ui.web.primefaces.test;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.UIMessageManager.Text;
import org.cyk.ui.web.primefaces.AbstractPrimefacesWebPage;
import org.cyk.ui.web.primefaces.PrimefacesTable;
import org.cyk.utility.common.AbstractMethod;

@Named
@ViewScoped
@Getter
@Setter
public class LocalityListController extends AbstractPrimefacesWebPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private PrimefacesTable<Locality> dataTable;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		dataTable =  (PrimefacesTable<Locality>) tableInstance(Locality.class);
		dataTable.bindToField("dataTable");
		
		Locality l = new Locality(null, null, "L1");l.setName("Ole");
		dataTable.addRow(l);
		
		l = new Locality(null, null, "L2");l.setName("Pole");
		dataTable.addRow(l);
		
		l = new Locality(null, null, "L3");l.setName("Babi");
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
