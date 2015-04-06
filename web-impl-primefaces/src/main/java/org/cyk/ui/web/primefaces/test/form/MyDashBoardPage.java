package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.ui.web.primefaces.page.AbstractDashboardPage;
import org.primefaces.model.DashboardColumn;

@Named @ViewScoped @Getter
public class MyDashBoardPage extends AbstractDashboardPage implements Serializable {

	private static final long serialVersionUID = -185384901090813349L;

	@Override
	protected void initialisation() {
		super.initialisation();
		DashboardColumn column = addColumn();
		addWidget(column, "sports");
		addWidget(column, "finance");
		
		column = addColumn();
		addWidget(column, "lifestyle");
		addWidget(column, "weather");
		
		column = addColumn();
		addWidget(column, "politics");
	}
	
}
