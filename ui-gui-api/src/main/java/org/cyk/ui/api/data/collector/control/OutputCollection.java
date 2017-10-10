package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.impl.DetailsClassLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputCollection<T> extends AbstractCollection<T,Object>  implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	protected Boolean isLazyLoaded = Boolean.TRUE;
	protected Object lazyDataModel;
	
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<T> elementClass,Class<?> elementObjectClass,Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables) {
		super(elementClass,elementObjectClass,null,null);
		this.identifiableClass = identifiableClass;
		if(this.identifiableClass!=null){
			//FIXME do not do this if it is lazy loaded
			getCollection().setFieldsContainerClass(getCollection().getElementObjectClass());
			getCollection().setIsCreatable(Boolean.TRUE).setIsUpdatable(Boolean.FALSE).setIsRemovable(Boolean.FALSE);
			add(identifiables);
		}
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<T> elementClass,Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables) {
		this(elementClass,inject(DetailsClassLocator.class).locate(identifiableClass),identifiableClass,identifiables);
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection<T> add(Collection<IDENTIFIABLE> identifiables){
		if(CollectionHelper.getInstance().isNotEmpty(identifiables)){
			Collection<Object> details = new ArrayList<>();
			for(AbstractIdentifiable identifiable : identifiables){
				details.add( ClassHelper.getInstance().instanciate(getCollection().getElementObjectClass(), new Object[]{identifiableClass,identifiable}) );
			}
			getCollection().setMany(details);
		}
		return this;
	}
	
	public Object getElements(){
		if(Boolean.TRUE.equals(getIsLazyLoaded())){
			if(lazyDataModel == null)
				lazyDataModel = instanciateLazyDataModel();
			return lazyDataModel;
		}
		return getCollection().getElements();
	}
	
	protected Object instanciateLazyDataModel(){
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
	/**/
	
	/**/
	
	public static class Element<T> extends AbstractCollection.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
	
		@Override
		protected Object read(Object object, String fieldName) {
			if(GlobalIdentifier.FIELD_CODE.equals(fieldName) && object instanceof AbstractIdentifiable)
				return ((AbstractIdentifiable)object).getCode();
			if(GlobalIdentifier.FIELD_NAME.equals(fieldName) && object instanceof AbstractIdentifiable)
				return ((AbstractIdentifiable)object).getName();
			if(GlobalIdentifier.FIELD_OTHER_DETAILS.equals(fieldName) && object instanceof AbstractIdentifiable)
				return ((AbstractIdentifiable)object).getOtherDetails();
			return super.read(object, fieldName);
		}
		
	}
	
	/**/
	
	public static interface Listener<T> {
		
		public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;

		}
		
	}
}
