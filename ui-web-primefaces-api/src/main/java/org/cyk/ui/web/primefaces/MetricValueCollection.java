package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.model.value.Value;
import org.cyk.ui.web.primefaces.AbstractMetricValueCollection.AbstractMetricValueItem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MetricValueCollection extends AbstractMetricValueCollection<MetricValueCollection.Item, Value> implements Serializable {

	private static final long serialVersionUID = -6459718386925539576L;

	public MetricValueCollection(String identifier) {
		super(identifier,Item.class,Value.class);
	}
	
	/**/
	
	
	public static class Item extends AbstractMetricValueItem<Value> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
		
	
	/**/
	
	/*public static class Adapter extends AbstractAdapter<MetricValueCollection.Item, MetricValue> {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected MetricValue getMetricValue(MetricValue metricValue) {
			return metricValue;
		}
		
	}*/
	
	public static class Adapter extends AbstractAdapter<MetricValueCollection.Item, Value> {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected Value getValue(Value value) {
			return value;
		}
		
	}

}
