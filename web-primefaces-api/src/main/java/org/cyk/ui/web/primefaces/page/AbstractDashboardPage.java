package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.primefaces.chart.ChartManager;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@Getter @Setter
public abstract class AbstractDashboardPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 9040359120893077422L;

	@Inject transient protected ChartManager chartManager;
	
	protected DashboardModel model = new DefaultDashboardModel();
	protected DashboardColumn currentColumn;
	
	protected DashboardColumn addColumn(){
		DashboardColumn column = currentColumn = new DefaultDashboardColumn();
		
		model.addColumn(column);
		return column;
	}
	
	protected AbstractDashboardPage addWidget(DashboardColumn column,String id){
		column.addWidget(id);
		return this;
	}
	
	protected AbstractDashboardPage addWidget(String id){
		return addWidget(currentColumn, id);
	}
	
}