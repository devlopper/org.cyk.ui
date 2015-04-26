package org.cyk.ui.api;

import org.cyk.system.root.business.api.chart.CartesianModel;
import org.cyk.system.root.business.api.chart.Series;

public interface UIChartManager<CHART_SERIES,BAR_CHART_MODEL,PIE_CHART_MODEL> {

	public static enum CartesianRender{BAR}
	
	CHART_SERIES series(Series series);
	
	BAR_CHART_MODEL barModel(CartesianModel cartesianModel);
	
	PIE_CHART_MODEL pieModel(Series series);
}
