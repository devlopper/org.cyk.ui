package org.cyk.ui.web.primefaces.test;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

@Named
@RequestScoped
@Getter
@Setter
public class IndexController extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

			
	@Override
	protected void initialisation() { 
		super.initialisation();
		//uiManager.registerClassKey(MyEntity.class,MyDetails.class,MyDetails2.class,Master.class);
	}
	


}
