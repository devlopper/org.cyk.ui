package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.language.LanguageEntry;
import org.cyk.ui.web.api.data.collector.control.WebInputAutoCompleteCommon;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputAutoCompleteCommon<VALUE_TYPE> extends AbstractBean implements WebInputAutoCompleteCommon<VALUE_TYPE>,Serializable {

	private static final long serialVersionUID = 770592182009797467L;

	private Integer numberOfCharacterBeforeQuery=1,numberOfMillisecondBetweenQueries=300,numberOfMillisecondQueryResultsCacheTimeOut=1000 * 300;
	private String noResultMessage=inject(LanguageBusiness.class).findText(LanguageEntry.NO_RESULT_FOUND);
	private Boolean forceSelectionEnabled=Boolean.TRUE,queryResultsCacheEnabled=Boolean.TRUE;

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
	public void onItemSelected(VALUE_TYPE item) {
		
	}

	@Override
	public void onItemUnSelected(VALUE_TYPE item) {
		
	}
	
	/**/
	
	public static interface Listener<VALUE_TYPE> {
		
		List<VALUE_TYPE> complete(String query);
		
		/**/
		
		public static class Adapter<VALUE_TYPE> extends BeanAdapter implements Listener<VALUE_TYPE> , Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			public List<VALUE_TYPE> complete(String query) {
				return null;
			}
			
			/**/
			
			public static class Default<VALUE_TYPE> extends Adapter<VALUE_TYPE> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				private Class<VALUE_TYPE> clazz;
				
				public Default(Class<VALUE_TYPE> aClass) {
					clazz = aClass;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public List<VALUE_TYPE> complete(String query) {
					if(AbstractIdentifiable.class.isAssignableFrom(clazz)){
						GlobalIdentifier.SearchCriteria searchCriteria = new GlobalIdentifier.SearchCriteria();
						searchCriteria.getCode().setValue(query);
						searchCriteria.getName().setValue(query);
						return (List<VALUE_TYPE>) inject(BusinessInterfaceLocator.class).injectTyped(((Class<AbstractIdentifiable>)clazz)).findByGlobalIdentifierSearchCriteria(searchCriteria);
					}
					return null;
				}
				
			}
		}
		
	}	

}
