package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.ui.web.primefaces.AbstractMetricValueCollection.AbstractMetricValueItem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MetricValueCollection extends AbstractMetricValueCollection<MetricValueCollection.Item, MetricValue> implements Serializable {

	private static final long serialVersionUID = -6459718386925539576L;

	public MetricValueCollection(String identifier) {
		super(identifier,Item.class,MetricValue.class);
	}
	
	/**/
	
	
	public static class Item extends AbstractMetricValueItem<org.cyk.system.root.model.mathematics.MetricValue> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
		
	
	/**/
	
	public static class Adapter extends AbstractAdapter<MetricValueCollection.Item, MetricValue> {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected MetricValue getMetricValue(MetricValue metricValue) {
			return metricValue;
		}
		
	}

}
