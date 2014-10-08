package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.model.table.Table;
import org.cyk.ui.test.model.MyEntity;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

@Named
@ViewScoped
@Getter
@Setter
public class TableDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private static List<SelectItem> CHOICES = new ArrayList<>();
	static{
		for(int i=1;i<=6;i++)
			CHOICES.add(new SelectItem("Choix "+i));
	}
	
	private Table<MyEntity> table = new Table<>();

	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		
	}
	
	
}
