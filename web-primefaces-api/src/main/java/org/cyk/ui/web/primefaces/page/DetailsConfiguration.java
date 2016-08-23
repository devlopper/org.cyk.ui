package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.Table.ColumnAdapter;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener;
import org.cyk.utility.common.AbstractMethod;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetailsConfiguration implements Serializable {

	private static final long serialVersionUID = 4302974500599359687L;
	
	/* One using form */
	private DetailsConfigurationListener.Form.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>> formConfigurationAdapter;
	private AbstractMethod<DetailsConfigurationListener.Form.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>>
	, Class<AbstractOutputDetails<AbstractIdentifiable>>> getFormConfigurationAdapterMethod;
	
	private ControlSetAdapter<AbstractOutputDetails<AbstractIdentifiable>> formControlSetAdapter = new DefaultControlSetAdapter();
	private AbstractMethod<ControlSetAdapter<AbstractOutputDetails<AbstractIdentifiable>>
	, Class<AbstractOutputDetails<AbstractIdentifiable>>> getFormControlSetAdapterMethod;
	
	/* Many using table */
	private DetailsConfigurationListener.Table.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>> tableConfigurationAdapter;
	private AbstractMethod<DetailsConfigurationListener.Table.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>>
	, Class<AbstractOutputDetails<AbstractIdentifiable>>> getTableConfigurationAdapterMethod;
	
	private ColumnAdapter tableColumnAdapter = new DefaultColumnAdapter();
	private AbstractMethod<ColumnAdapter, Class<AbstractOutputDetails<AbstractIdentifiable>>> getTableColumnAdapterMethod;
	
	@SuppressWarnings("rawtypes")
	public ControlSetAdapter getFormControlSetAdapter(Class clazz){
		AbstractMethod<ControlSetAdapter<AbstractOutputDetails<AbstractIdentifiable>>
		, Class<AbstractOutputDetails<AbstractIdentifiable>>> method = getGetFormControlSetAdapterMethod();
		if(method==null)
			return getFormControlSetAdapter();
		@SuppressWarnings("unchecked")
		ControlSetAdapter<AbstractOutputDetails<AbstractIdentifiable>> result = method.execute(clazz);
		if(result == null)
			result = getFormControlSetAdapter();
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  DetailsConfigurationListener.Form.Adapter getFormConfigurationAdapter
		(Class identifiableClass,Class detailsClass){
		AbstractMethod<DetailsConfigurationListener.Form.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>>
		, Class<AbstractOutputDetails<AbstractIdentifiable>>> method = getGetFormConfigurationAdapterMethod();
		DetailsConfigurationListener.Form.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>> result = null;
		if(method==null){
			result = getFormConfigurationAdapter();
			if(result==null)
				return new DetailsConfigurationListener.Form.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>>(
						(Class<AbstractIdentifiable>) identifiableClass,detailsClass){
					private static final long serialVersionUID = 1L;
	
				};
			else
				return result;
		}
		result = method.execute(detailsClass);
		if(result == null)
			result = getFormConfigurationAdapter();
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public ColumnAdapter getTableColumnAdapter(Class clazz){
		AbstractMethod<ColumnAdapter, Class<AbstractOutputDetails<AbstractIdentifiable>>> method = getGetTableColumnAdapterMethod();
		if(method==null)
			return getTableColumnAdapter();
		@SuppressWarnings("unchecked")
		ColumnAdapter result = method.execute(clazz);
		if(result == null)
			result = getTableColumnAdapter();
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  DetailsConfigurationListener.Table.Adapter getTableConfigurationAdapter
		(Class identifiableClass,Class detailsClass){
		AbstractMethod<DetailsConfigurationListener.Table.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>>
		, Class<AbstractOutputDetails<AbstractIdentifiable>>> method = getGetTableConfigurationAdapterMethod();
		DetailsConfigurationListener.Table.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>> result = null;
		if(method==null){
			result = getTableConfigurationAdapter();
			if(result==null)
				return new DetailsConfigurationListener.Table.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>>(
						(Class<AbstractIdentifiable>) identifiableClass,detailsClass){
					private static final long serialVersionUID = 1L;
	
				};
			else
				return result;
		}
		result = method.execute(detailsClass);
		if(result == null)
			result = getTableConfigurationAdapter();
		return result;
	}
	
	/**/
	
	public static class GetDetailsConstrolSetAdapterMethod extends AbstractMethod<ControlSetAdapter<AbstractOutputDetails<AbstractIdentifiable>>, AbstractIdentifiable>{
		private static final long serialVersionUID = 7432888281842913514L;

		@Override
		protected ControlSetAdapter<AbstractOutputDetails<AbstractIdentifiable>> __execute__(AbstractIdentifiable identifiable) {
			return null;
		}
		
	}
	
	public static class DefaultControlSetAdapter extends ControlSetAdapter<AbstractOutputDetails<AbstractIdentifiable>> implements Serializable {
		private static final long serialVersionUID = -4644620620046718336L;

		@Override
		public Boolean build(Object data,Field field) {
			return isFieldNameNotIn(field, AbstractOutputDetails.FIELD_CODE,AbstractOutputDetails.FIELD_NAME,AbstractOutputDetails.FIELD_IMAGE
					,AbstractOutputDetails.FIELD_ABBREVIATION,AbstractOutputDetails.FIELD_DESCRIPTION);
		}
		
	}
	
	public static class DefaultColumnAdapter extends ColumnAdapter implements Serializable {
		private static final long serialVersionUID = -6823895736787162731L;
		
		@Override
		public Boolean isColumn(Field field) {
			if(ArrayUtils.contains(new String[]{AbstractOutputDetails.FIELD_CODE,AbstractOutputDetails.FIELD_NAME,AbstractOutputDetails.FIELD_IMAGE
					,AbstractOutputDetails.FIELD_ABBREVIATION,AbstractOutputDetails.FIELD_DESCRIPTION,AbstractOutputDetails.FIELD_EXISTENCE_PERIOD}, field.getName()))
				return Boolean.FALSE;
			return super.isColumn(field);
		}
	}
	
}
