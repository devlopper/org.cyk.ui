package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValueInputted;
import org.cyk.system.root.model.mathematics.MetricValueType;
import org.cyk.ui.api.SelectItemBuilderListener;
import org.cyk.ui.api.model.AbstractItemCollectionItem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MetricValueCollection<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends ItemCollection<TYPE, IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = -6459718386925539576L;

	private MetricCollection metricCollection;
	private List<SelectItem> choices = new ArrayList<>();
	private Boolean isNumber,showNumberColumn,showStringColumn,showCombobox;
	
	public MetricValueCollection(String identifier,Class<TYPE> itemClass,Class<IDENTIFIABLE> identifiableClass) {
		super(identifier,itemClass,identifiableClass);
		
	}
	
	public void setMetricCollection(MetricCollection value){
		this.metricCollection = value;
		setLabel(this.metricCollection.getName());
		isNumber = MetricValueType.NUMBER.equals(metricCollection.getValueType());
		showNumberColumn = isNumber;
		showStringColumn = !isNumber;
		if(showCombobox = RootBusinessLayer.getInstance().getIntervalCollectionBusiness().isAllIntervalLowerEqualsToHigher(metricCollection.getValueIntervalCollection())){
			choices.add(new SelectItem(null, RootBusinessLayer.getInstance().getLanguageBusiness().findText(SelectItemBuilderListener.NULL_LABEL_ID)));
			for(Interval interval : RootBusinessLayer.getInstance().getIntervalBusiness().findByCollection(metricCollection.getValueIntervalCollection())){
				choices.add(new SelectItem(MetricValueInputted.VALUE_INTERVAL_CODE.equals(metricCollection.getValueInputted()) ? interval.getCode() : interval.getLow().getValue()
						, MetricValueInputted.VALUE_INTERVAL_CODE.equals(metricCollection.getValueInputted()) ? RootBusinessLayer.getInstance().getIntervalBusiness().findRelativeCode(interval) : RootBusinessLayer.getInstance().getNumberBusiness().format(interval.getLow().getValue())));
			}
		}
	}
	
	/**/
	
	@Getter @Setter
	public static class AbstractMetricValueItem<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractItemCollectionItem<IDENTIFIABLE> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		private String name;
		private BigDecimal numberValue;
		private String stringValue;
	}

}
