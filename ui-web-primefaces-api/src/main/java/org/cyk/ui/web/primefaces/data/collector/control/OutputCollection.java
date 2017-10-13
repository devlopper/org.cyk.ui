 package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.FilterClassLocator;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.DetailsClassLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.JavascriptHelper;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.JavaScriptHelper;
import org.cyk.utility.common.helper.MarkupLanguageHelper;
import org.primefaces.model.SortOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputCollection<T> extends org.cyk.ui.web.api.data.collector.control.OutputCollection<T> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<T> elementClass,Class<?> elementObjectClass,Class<IDENTIFIABLE> identifiableClass,String[] elementObjectClassFieldNames,Collection<IDENTIFIABLE> identifiables) {
		super(elementClass,elementObjectClass,identifiableClass,elementObjectClassFieldNames,identifiables);
		
		org.cyk.ui.web.primefaces.MarkupLanguageHelper.setOnClick(getFilterCommand(), JavascriptHelper.getFilterDatatable(this));
		
	}
	
	@SuppressWarnings("unchecked")
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<IDENTIFIABLE> identifiableClass,String[] elementObjectClassFieldNames,Collection<IDENTIFIABLE> identifiables) {
		this((Class<T>) Element.class,inject(DetailsClassLocator.class).locate(identifiableClass),identifiableClass,elementObjectClassFieldNames,identifiables);
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<IDENTIFIABLE> identifiableClass,String[] elementObjectClassFieldNames) {
		this(identifiableClass,elementObjectClassFieldNames,null);
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<IDENTIFIABLE> identifiableClass) {
		this(identifiableClass,null);
	}
	
	@Override
	protected Object instanciateLazyDataModel() {
		return new LazyDataModel<T>(this,identifiableClass,4l);
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.ui.web.api.data.collector.control.OutputCollection.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
	
		public static class Enumeration<T> extends org.cyk.ui.web.api.data.collector.control.OutputCollection.Element<T> implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}
		
		@Override
		public org.cyk.utility.common.helper.CollectionHelper.Element<T> setObject(T object) {
			if(object!=null){
				org.cyk.ui.web.primefaces.MarkupLanguageHelper.setOnClick(getReadCommand(),new JavaScriptHelper.Script.Window.Navigate.Adapter
						.Default().setUniformResourceLocatorStringifier(Constant.Action.CONSULT, ((AbstractOutputDetails<?>)object).getMaster() ).execute());
				
				org.cyk.ui.web.primefaces.MarkupLanguageHelper.setOnClick(getUpdateCommand(),new JavaScriptHelper.Script.Window.Navigate.Adapter
						.Default().setUniformResourceLocatorStringifier(Constant.Action.UPDATE, ((AbstractOutputDetails<?>)object).getMaster() ).execute());
				
				org.cyk.ui.web.primefaces.MarkupLanguageHelper.setOnClick(getRemoveCommand(),new JavaScriptHelper.Script.Window.Navigate.Adapter
						.Default().setUniformResourceLocatorStringifier(Constant.Action.DELETE, ((AbstractOutputDetails<?>)object).getMaster() ).execute());
				
			}
			return super.setObject(object);
		}
		
	}
	
	/**/
	
	public static class LazyDataModel<T> extends org.primefaces.model.LazyDataModel<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private OutputCollection<T> outputCollection;
		private Class<? extends AbstractIdentifiable> identifiableClass;
		private Long maximumResultCount,rowCount;
		private String globalFilter;
		
		public LazyDataModel(OutputCollection<T> outputCollection,Class<? extends AbstractIdentifiable> identifiableClass,Long maximumResultCount) {
			this.outputCollection = outputCollection;
			this.identifiableClass = identifiableClass;
			this.maximumResultCount = maximumResultCount;
			((MarkupLanguageHelper.Attributes) (Object)outputCollection.getPropertiesMap()).setRows(maximumResultCount == null ? Constant.EMPTY_STRING
					: maximumResultCount.toString());
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<T> load(int first, int pageSize,String sortField, SortOrder sortOrder,Map<String, Object> filters) {
			String filter = filters == null ? null : (String)filters.get("globalFilter");
			if(StringUtils.isBlank(filter))
				filter = globalFilter;
			DataReadConfiguration configuration = new DataReadConfiguration((long)first,maximumResultCount, sortField, SortOrder.ASCENDING.equals(sortOrder), filters, filter);
			FilterHelper.Filter<AbstractIdentifiable> filterInstance = (Filter<AbstractIdentifiable>) ClassHelper.getInstance().instanciateOne(FilterClassLocator.getInstance().locate(identifiableClass));
			filterInstance.set(filter);
			outputCollection.getCollection().removeAll();
			outputCollection.add(InstanceHelper.getInstance().get((Class<AbstractIdentifiable>)identifiableClass,filterInstance, configuration));
			rowCount = InstanceHelper.getInstance().getIfNotNullElseDefault(
					InstanceHelper.getInstance().count((Class<AbstractIdentifiable>)identifiableClass,filterInstance, configuration),0l);
			return (List<T>) outputCollection.getCollection().getElements();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public int getRowCount() {
			return rowCount == null ? InstanceHelper.getInstance().count((Class<AbstractIdentifiable>)identifiableClass, null).intValue() : rowCount.intValue();
		}
		
	}
	
	/**/
	
	public static interface Listener<T> {
		
		public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;

		}
		
	}
}
