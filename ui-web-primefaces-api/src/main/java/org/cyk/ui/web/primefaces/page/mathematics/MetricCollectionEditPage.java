package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named @ViewScoped
public class MetricCollectionEditPage extends AbstractCollectionEditPage.Extends<MetricCollection,Metric,MetricCollectionEditPage.MetricItem> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private List<SelectItem> valueProperties;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		valueProperties = webManager.getSelectItems(ValueProperties.class);
	}
	
	@Override
	protected ItemCollection<MetricItem, Metric, MetricCollection> instanciateItemCollection() {
		return createItemCollection(MetricItem.class, Metric.class,identifiable 
			,new org.cyk.ui.web.primefaces.ItemCollectionAdapter<MetricItem,Metric,MetricCollection>(identifiable,crud,form){
				private static final long serialVersionUID = 1L;
				/*
				@Override
				public Collection<Metric> load() {
					getCollection().getItems().setCollection(inject(MetricBusiness.class).findByCollection(getCollection()));
					return getCollection().getItems().getCollection();
				}
				
				@Override
				public Metric instanciate(AbstractItemCollection<MetricItem, Metric, MetricCollection, SelectItem> itemCollection) {
					Metric interval = inject(MetricBusiness.class).instanciateOne();
					interval.setCollection(collection);
					return interval;
				}
				
				@Override
				public Boolean isShowAddButton() {
					return Boolean.TRUE;
				}
				*/
				@Override
				public void read(MetricItem item) {
					super.read(item);
					item.setCode(item.getIdentifiable().getCode());
					item.setName(item.getIdentifiable().getName());
					item.setValueProperties(item.getIdentifiable().getValueProperties());
				}
				
				@Override
				public void write(MetricItem item) {
					super.write(item);
					item.getIdentifiable().setCode(item.getCode());
					item.getIdentifiable().setName(item.getCode());
					item.getIdentifiable().setValueProperties(item.getValueProperties());
				}		
		});
	}
		
	@Getter @Setter
	public static class Form extends AbstractForm.Extends<MetricCollection,Metric> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private MetricCollectionType type;
		@Input @InputChoice @InputOneChoice @InputOneCombo private ValueProperties valueProperties;
		@Input @InputChoice @InputOneChoice @InputOneCombo private Value value;
		
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_VALUE_PROPERTIES = "valueProperties";
		public static final String FIELD_VALUE = "value";
	}

	@Getter @Setter
	public static class MetricItem extends AbstractItemCollectionItem<Metric> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		private String code,name;
		private ValueProperties valueProperties;

	}
}
