 package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.DetailsClassLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.web.api.WebManager;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.primefaces.model.SortOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputCollection<T> extends org.cyk.ui.web.api.data.collector.control.OutputCollection<T> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<T> elementClass,Class<?> elementObjectClass,Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables) {
		super(elementClass,elementObjectClass,identifiableClass,identifiables);
	}
	
	@SuppressWarnings("unchecked")
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables) {
		this((Class<T>) Element.class,inject(DetailsClassLocator.class).locate(identifiableClass),identifiableClass,identifiables);
	}
	
	@Override
	protected Object instanciateLazyDataModel() {
		return new LazyDataModel<T>(this,identifiableClass);
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.ui.web.api.data.collector.control.OutputCollection.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
	
		public static class Enumeration<T> extends org.cyk.ui.web.api.data.collector.control.OutputCollection.Element<T> implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}
		
	}
	
	/**/
	
	public static class LazyDataModel<T> extends org.primefaces.model.LazyDataModel<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private OutputCollection<T> outputCollection;
		private Class<? extends AbstractIdentifiable> identifiableClass;
		private Long maximumResultCount;
		
		public LazyDataModel(OutputCollection<T> outputCollection,Class<? extends AbstractIdentifiable> identifiableClass) {
			this.outputCollection = outputCollection;
			this.identifiableClass = identifiableClass;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<T> load(int first, int pageSize,String sortField, SortOrder sortOrder,Map<String, Object> filters) {
			String filter = filters == null ? null : (String)filters.get("globalFilter");
			if(StringUtils.isBlank(filter))
				filter = WebManager.getInstance().getRequestParameter(UniformResourceLocatorParameter.FILTER);
			
			DataReadConfiguration configuration = new DataReadConfiguration((long)first,3l, sortField, SortOrder.ASCENDING.equals(sortOrder), filters, filter);
			/*
			if(Boolean.TRUE.equals(fetch)){
				Table.this.load(configuration);
			}
			fetch = Boolean.TRUE;
			
			resultsCount =  count(configuration);
			*/
			outputCollection.getCollection().getElements().clear();
			outputCollection.add(InstanceHelper.getInstance().get(identifiableClass, configuration));
			
			/*List<Object> details = new ArrayList<>();
			for(AbstractIdentifiable identifiable : InstanceHelper.getInstance().get(identifiableClass, configuration)){
				details.add( ClassHelper.getInstance().instanciate(outputCollection.getCollection().getElementObjectClass(), new Object[]{identifiableClass,identifiable}) );
			}*/
			return (List<T>) outputCollection.getCollection().getElements();
		}
		
		@Override
		public int getRowCount() {
			return InstanceHelper.getInstance().getIfNotNullElseDefault(InstanceHelper.getInstance().count(identifiableClass, null),0l).intValue();
			//rowCount == null ? (resultsCount==null?0:resultsCount.intValue()) : rowCount;
		}
		
	}
	
	/**/
	
	public static interface Listener<T> {
		
		public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;

		}
		
	}
}
