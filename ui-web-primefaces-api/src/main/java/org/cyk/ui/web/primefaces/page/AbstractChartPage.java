package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.ui.web.primefaces.chart.ChartManager;

import lombok.Getter;
import lombok.Setter;

@Named @Getter @Setter
public abstract class AbstractChartPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 9040359120893077422L;

	@Inject transient protected ChartManager chartManager;
		 
}