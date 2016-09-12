package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.language.LanguageEntry;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.web.api.data.collector.control.WebInputAutoCompleteCommon;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputAutoCompleteCommon<VALUE_TYPE> extends AbstractBean implements WebInputAutoCompleteCommon<VALUE_TYPE>,Serializable {

	private static final long serialVersionUID = 770592182009797467L;

	private Integer numberOfCharacterBeforeQuery=1,numberOfMillisecondBetweenQueries=300,numberOfMillisecondQueryResultsCacheTimeOut=1000 * 300;
	private String noResultMessage=inject(LanguageBusiness.class).findText(LanguageEntry.NO_RESULT_FOUND),appendTo;
	private Boolean forceSelectionEnabled=Boolean.TRUE,queryResultsCacheEnabled=Boolean.TRUE;
	private CascadeStyleSheet resultsContainerCascadeStyleSheet = new CascadeStyleSheet();

	private Collection<Listener<VALUE_TYPE>> autoCompleteListeners = new ArrayList<>();
	
	@Override
	public List<VALUE_TYPE> complete(final String query) {
		return (List<VALUE_TYPE>) listenerUtils.getCollection(autoCompleteListeners, new ListenerUtils.CollectionMethod<Listener<VALUE_TYPE>, VALUE_TYPE>() {
			@Override
			public Collection<VALUE_TYPE> execute(Listener<VALUE_TYPE> listener) {
				return listener.complete(query);
			}
		});
	}
	
	@Override
	public String getLabel(final VALUE_TYPE item) {
		return listenerUtils.getString(autoCompleteListeners, new ListenerUtils.StringMethod<Listener<VALUE_TYPE>>() {
			@Override
			public String execute(Listener<VALUE_TYPE> listener) {
				return listener.getLabel(item);
			}
		});
	}

	@Override
	public void onItemSelected(VALUE_TYPE item) {
		
	}

	@Override
	public void onItemUnSelected(VALUE_TYPE item) {
		
	}
	
	/**/
	
	public static interface Listener<VALUE_TYPE> {
		
		List<VALUE_TYPE> complete(String query);
		String getLabel(VALUE_TYPE item);
		/**/
		
		public static class Adapter<VALUE_TYPE> extends BeanAdapter implements Listener<VALUE_TYPE> , Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			public List<VALUE_TYPE> complete(String query) {
				return null;
			}
			@Override
			public String getLabel(VALUE_TYPE item) {
				return null;
			}
			/**/
			
			public static class Default<VALUE_TYPE> extends Adapter<VALUE_TYPE> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				private Class<VALUE_TYPE> clazz;
				
				public Default(Class<VALUE_TYPE> aClass) {
					clazz = aClass;
				}
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public List<VALUE_TYPE> complete(String query) {
					List<VALUE_TYPE> results = null;
					if(AbstractIdentifiable.class.isAssignableFrom(clazz)){
						GlobalIdentifier.SearchCriteria searchCriteria = new GlobalIdentifier.SearchCriteria();
						searchCriteria.getCode().setValue(query);
						searchCriteria.getName().setValue(query);
						results = (List<VALUE_TYPE>) inject(BusinessInterfaceLocator.class).injectTyped(((Class<AbstractIdentifiable>)clazz)).findByGlobalIdentifierSearchCriteria(searchCriteria);
						if(AbstractDataTreeNode.class.isAssignableFrom(clazz)){
							((AbstractDataTreeNodeBusiness) inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)clazz)).setParents(results);
						}
					}
					return results;
				}
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public String getLabel(VALUE_TYPE item) {
					if(item==null)
						return Constant.EMPTY_STRING;
					if(AbstractDataTreeNode.class.isAssignableFrom(clazz)){
						if(((AbstractDataTreeNode)item).getParents()==null)
							((AbstractDataTreeNodeBusiness) inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)clazz)).setParents((AbstractEnumeration) item);
						Collection<AbstractDataTreeNode> collection = new ArrayList<>(((AbstractDataTreeNode)item).getParents());
						collection.add((AbstractDataTreeNode) item);
						return StringUtils.join(collection,Constant.CHARACTER_GREATER_THAN);
					}
					if(AbstractModelElement.class.isAssignableFrom(clazz)){
						return ((AbstractModelElement)item).getUiString();
					}
					return Constant.EMPTY_STRING;
				}
				
			}
		}
		
	}	

}
