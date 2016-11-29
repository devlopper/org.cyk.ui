package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.MetricValueInputted;
import org.cyk.system.root.model.mathematics.MetricValueType;
import org.cyk.ui.api.SelectItemBuilderListener;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;

@Getter @Setter
public abstract class AbstractMetricValueCollection<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends ItemCollection<TYPE, IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = -6459718386925539576L;

	private MetricCollection metricCollection;
	private List<SelectItem> choices = new ArrayList<>();
	private Boolean autoSetShowOneChoiceInput=Boolean.TRUE,isNumber,showNumberColumn,showStringColumn,showOneChoiceInput,showCombobox,showRadio;
	private Long minimumNumberOfItemToShowCombobox=6l;
	
	public AbstractMetricValueCollection(String identifier,Class<TYPE> itemClass,Class<IDENTIFIABLE> identifiableClass) {
		super(identifier,itemClass,identifiableClass);
	}
	
	public void setMetricCollection(MetricCollection value){
		this.metricCollection = value;
		isNumber = MetricValueType.NUMBER.equals(metricCollection.getValueType());
		showNumberColumn = isNumber;
		showStringColumn = !isNumber;
		
		if(metricCollection.getValueIntervalCollection()==null){
			showOneChoiceInput = Boolean.FALSE;
		}else{
			showOneChoiceInput = Boolean.TRUE;
			if(inject(IntervalCollectionBusiness.class).isAllIntervalLowerEqualsToHigher(metricCollection.getValueIntervalCollection())){
				for(Interval interval : inject(IntervalBusiness.class).findByCollection(metricCollection.getValueIntervalCollection())){
					choices.add(new SelectItem(MetricValueInputted.VALUE_INTERVAL_CODE.equals(metricCollection.getValueInputted()) ? interval.getCode() : interval.getLow().getValue()
							, MetricValueInputted.VALUE_INTERVAL_CODE.equals(metricCollection.getValueInputted()) ? inject(IntervalBusiness.class).findRelativeCode(interval) : inject(NumberBusiness.class).format(interval.getLow().getValue())));
				}
			}
		}
		
		setLabel(value.getName());
	}
	
	public void addNullChoice(String label){
		choices.add(new SelectItem(null, label));
	}
	public void addNullChoice(){
		addNullChoice(inject(LanguageBusiness.class).findText(SelectItemBuilderListener.NULL_LABEL_ID));
	}
	
	public Boolean getShowCombobox(){
		if(showCombobox == null && Boolean.TRUE.equals(autoSetShowOneChoiceInput))
			showCombobox = choices.size() >= minimumNumberOfItemToShowCombobox;
		return showCombobox;
	}
	
	public Boolean getShowRadio(){
		if(showRadio == null && Boolean.TRUE.equals(autoSetShowOneChoiceInput))
			showRadio = choices.size() < minimumNumberOfItemToShowCombobox;
		return showRadio;
	}
	
	/**/
	
	@Getter @Setter
	public static class AbstractMetricValueItem<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractItemCollectionItem<IDENTIFIABLE> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		private String name;
		private BigDecimal numberValue;
		private String stringValue;
		
	}
	
	/**/
	
	public static class AbstractAdapter<TYPE extends AbstractMetricValueItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends ItemCollection.Adapter<TYPE, IDENTIFIABLE> {

		private static final long serialVersionUID = -91522966404798240L;
		
		@Override
		public void instanciated(AbstractItemCollection<TYPE, IDENTIFIABLE,SelectItem> itemCollection,TYPE item) {
			super.instanciated(itemCollection, item);
			item.setName(getMetricValue(item.getIdentifiable()).getMetric().getName());
			item.setNumberValue(getMetricValue(item.getIdentifiable()).getNumberValue().get());
			item.setStringValue(getMetricValue(item.getIdentifiable()).getStringValue());
		}
		
		@Override
		public void setLabel(AbstractItemCollection<TYPE, IDENTIFIABLE, SelectItem> itemCollection,TYPE item) {
			super.setLabel(itemCollection, item);
			item.setLabel(getMetricValue(item.getIdentifiable()).getMetric().getName() /*StringUtils.defaultIfBlank(item.getLabel(), item.getIdentifiable().getName())*/);
		}
		
		@Override
		public void write(TYPE item) {
			super.write(item);
			getMetricValue(item.getIdentifiable()).getNumberValue().setUser(item.getNumberValue());
			getMetricValue(item.getIdentifiable()).setStringValue(item.getStringValue());
		}
		
		protected MetricValue getMetricValue(IDENTIFIABLE identifiable){
			return (MetricValue) commonUtils.readProperty(identifiable, METRIC_VALUE);
		}
		
		public static final String METRIC_VALUE = "metricValue";
	}

}
