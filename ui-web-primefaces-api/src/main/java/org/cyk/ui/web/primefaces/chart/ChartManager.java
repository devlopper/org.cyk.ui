package org.cyk.ui.web.primefaces.chart;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.chart.AbstractChartModel;
import org.cyk.system.root.business.api.chart.CartesianModel;
import org.cyk.system.root.business.api.chart.PieModel;
import org.cyk.system.root.business.api.chart.Series;
import org.cyk.system.root.business.api.chart.SeriesItem;
import org.cyk.ui.api.UIChartManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class ChartManager extends AbstractBean implements UIChartManager<ChartSeries,BarChartModel,PieChartModel>,Serializable {

	private static final long serialVersionUID = -3652044913525036249L;

	private static ChartManager INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public static ChartManager getInstance() {
		return INSTANCE;
	}
	
	@Override
	public ChartSeries series(Series series) {
		ChartSeries chartSeries = new ChartSeries();
		chartSeries.setLabel(label(series.getLabel()));
		for(SeriesItem seriesItem : series.getItems())
			chartSeries.set(seriesItem.getX(), seriesItem.getY());
		return chartSeries;
	}
		
	@Override
	public BarChartModel barModel(CartesianModel cartesianModel) {
		if(cartesianModel==null)
			return null;
		BarChartModel model = new BarChartModel();
		for(Series series : cartesianModel.getSeriesCollection())
			model.addSeries(series(series));
		
		model(cartesianModel,model);
		
        Axis xAxis = model.getAxis(AxisType.X);
        axis(cartesianModel.getXAxis(),xAxis);
        
        Axis yAxis = model.getAxis(AxisType.Y);
        axis(cartesianModel.getYAxis(),yAxis);
        
		return model;
	}
	
	@Override
	public PieChartModel pieModel(PieModel pieModel) {
		PieChartModel model = new PieChartModel();
        for(SeriesItem item : pieModel.getSeries().getItems())
        	model.set(item.getX().toString(), item.getY());
		 
		model(pieModel,model);
		
		model.setShowDataLabels(Boolean.TRUE.equals(pieModel.getShowLabels()));
		if(pieModel.getDiameter()!=null)
			model.setDiameter(pieModel.getDiameter());
		
		return model;
	}
	
	private String label(String label){
		return StringUtils.replace(label, "'", " ");
	}
	
	private void axis(org.cyk.system.root.business.api.chart.Axis axis,Axis paxis){
		paxis.setLabel(label(axis.getLabel()));
		paxis.setMin(axis.getMinimum());
		if(axis.getTickAngle()!=null)
			paxis.setTickAngle(axis.getTickAngle());	
	}

	private void model(AbstractChartModel model,ChartModel chartModel){
		if(Boolean.TRUE.equals(model.getShowTitle()))
			chartModel.setTitle(label(model.getTitle()));
		switch(model.getLegendPosition()){
		case NORTH:chartModel.setLegendPosition("n");break;
		case NORTH_EAST:chartModel.setLegendPosition("ne");break;
		case NORTH_WEST:chartModel.setLegendPosition("nw");break;
		case SOUTH:chartModel.setLegendPosition("s");break;
		case SOUTH_EAST:chartModel.setLegendPosition("se");break;
		case SOUTH_WEST:chartModel.setLegendPosition("sw");break;
		case EAST:chartModel.setLegendPosition("e");break;
		case WEST:chartModel.setLegendPosition("w");break;
		}
	}

}
