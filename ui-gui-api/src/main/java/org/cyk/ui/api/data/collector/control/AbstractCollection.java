package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.ui.api.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.GridHelper;
import org.cyk.utility.common.helper.MarkupLanguageHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class AbstractCollection<T,SELECT_ITEM> extends GridHelper.Grid<T,SELECT_ITEM> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	protected Class<? extends AbstractIdentifiable> identifiableClass;
	
	public AbstractCollection(Class<T> elementClass,Class<?> elementObjectClass,Class<SELECT_ITEM> sourceClass,Class<?> sourceObjectClass) {
		super(elementClass,elementObjectClass,sourceClass,sourceObjectClass);
		((MarkupLanguageHelper.Attributes) (Object)getPropertiesMap()).setRendered(Boolean.TRUE.toString());
		((MarkupLanguageHelper.Attributes) (Object)getPropertiesMap()).setEmptyMessage("MY CUSTOM EMPTY MESSAGE");
		
		((MarkupLanguageHelper.Attributes) (Object)getPropertiesMap()).setLazy(Boolean.TRUE.toString());
		((MarkupLanguageHelper.Attributes) (Object)getPropertiesMap()).setPaginator(Boolean.TRUE.toString());
		
		getAddCommand().setProperty(Constant.INPUT_VALUE_IS_NOT_REQUIRED, Boolean.TRUE);
		getAddCommand().setNameRendered(getAddCommand().getMappedIcon()==null);
		
		getDeleteCommand().setProperty(Constant.INPUT_VALUE_IS_NOT_REQUIRED, Boolean.TRUE);
		getDeleteCommand().setNameRendered(getDeleteCommand().getMappedIcon()==null);
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.utility.common.helper.CollectionHelper.Element<T> implements Serializable {
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
		
		@Override
		protected Object instanciatePropertiesMap() {
			return new MarkupLanguageHelper.Attributes();
		}
		
	}
	
	/**/
	
	public static interface Listener<T> {
		
		public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;

		}
		
	}
}
