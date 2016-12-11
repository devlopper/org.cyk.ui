package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.value.MeasureBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueSet;
import org.cyk.system.root.model.value.ValueType;
import org.cyk.ui.api.SelectItemBuilderListener;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.api.WebManager;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractMetricValueCollection<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends ItemCollection<TYPE, IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = -6459718386925539576L;

	private static final String NULL_STRING = RandomStringUtils.randomAlphanumeric(20)+System.currentTimeMillis();
	
	private MetricCollection metricCollection;
	private List<SelectItem> choices = new ArrayList<>();
	private Boolean autoSetShowOneChoiceInput=Boolean.TRUE,isNumber,showNumberColumn,isBoolean,showStringColumn,showBooleanColumn,showOneChoiceInput,showCombobox,showRadio;
	private Long minimumNumberOfItemToShowCombobox=6l;
	
	public AbstractMetricValueCollection(String identifier,Class<TYPE> itemClass,Class<IDENTIFIABLE> identifiableClass) {
		super(identifier,itemClass,identifiableClass);
	}
	
	public void setMetricCollection(MetricCollection value){
		this.metricCollection = value;
		showNumberColumn = isNumber = ValueType.NUMBER.equals(metricCollection.getValueProperties().getType());
		showBooleanColumn = isBoolean = ValueType.BOOLEAN.equals(metricCollection.getValueType());
		showStringColumn = ValueType.STRING.equals(metricCollection.getValueType());
		
		if(metricCollection.getValueIntervalCollection()==null){
			showOneChoiceInput = Boolean.FALSE;
			if(isBoolean){
				choices.addAll(WebManager.getInstance().getBooleanSelectItemsNoNull());
			}
		}else{
			showOneChoiceInput = Boolean.TRUE;
			String choiceName;
			Object choiceValue = null;
			if(Boolean.TRUE.equals(metricCollection.getValueProperties().getNullable())){
				if(ValueSet.INTERVAL_RELATIVE_CODE.equals(metricCollection.getValueSet()))
					choiceValue = NULL_STRING;
				
				choices.add(new SelectItem(choiceValue,metricCollection.getValueProperties().getNullString().getCode()));
			}
			if(inject(IntervalCollectionBusiness.class).isAllIntervalLowerEqualsToHigher(metricCollection.getValueIntervalCollection())){
				for(Interval interval : inject(IntervalBusiness.class).findByCollection(metricCollection.getValueIntervalCollection())){
					if(ValueSet.INTERVAL_RELATIVE_CODE.equals(metricCollection.getValueSet())){
						choiceValue = choiceName = RootConstant.Code.getRelativeCode(interval);
					}else{
						choiceValue = interval.getLow().getValue();
						choiceName = inject(NumberBusiness.class).format(interval.getLow().getValue());
					}
					choices.add(new SelectItem(choiceValue,choiceName));
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
		private Boolean booleanValue;
	}
	
	/**/
	
	public static class AbstractAdapter<TYPE extends AbstractMetricValueItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends ItemCollection.Adapter<TYPE, IDENTIFIABLE> {

		private static final long serialVersionUID = -91522966404798240L;
		
		@Override
		public void instanciated(AbstractItemCollection<TYPE, IDENTIFIABLE,SelectItem> itemCollection,TYPE item) {
			super.instanciated(itemCollection, item);
			MetricValue metricValue = getMetricValue(item.getIdentifiable());
			Value value = metricValue.getValue();
			item.setName(inject(FormatterBusiness.class).format(metricValue.getMetric()));
			item.setNumberValue(inject(MeasureBusiness.class).computeQuotient(value.getMeasure(), value.getNumberValue().get()));
			if(Boolean.TRUE.equals(metricValue.getValue().getNullable())){
				if(value.getStringValue().get()==null && Boolean.TRUE.equals(value.getInitialized()))
					item.setStringValue(NULL_STRING);
				else
					item.setStringValue(value.getStringValue().get());
			}
			item.setBooleanValue(value.getBooleanValue().get());
		}
		
		@Override
		public void setLabel(AbstractItemCollection<TYPE, IDENTIFIABLE, SelectItem> itemCollection,TYPE item) {
			super.setLabel(itemCollection, item);
			item.setLabel(inject(FormatterBusiness.class).format(getMetricValue(item.getIdentifiable()).getMetric()));
		}
		
		@Override
		public void write(TYPE item) {
			super.write(item);
			MetricValue metricValue = getMetricValue(item.getIdentifiable());
			Value value = metricValue.getValue();
			value.getNumberValue().set(inject(MeasureBusiness.class).computeMultiple(value.getMeasure(), item.getNumberValue()));
			if(Boolean.TRUE.equals(metricValue.getValue().getNullable())){
				if(NULL_STRING.equals(item.getStringValue()))
					value.getStringValue().set(null);
				else
					value.getStringValue().set(item.getStringValue());	
			}
			value.getBooleanValue().set(item.getBooleanValue());
			value.setInitialized(Boolean.TRUE);
		}
		
		protected MetricValue getMetricValue(IDENTIFIABLE identifiable){
			return (MetricValue) commonUtils.readProperty(identifiable, METRIC_VALUE);
		}
		
		public static final String METRIC_VALUE = "metricValue";
	}

}
