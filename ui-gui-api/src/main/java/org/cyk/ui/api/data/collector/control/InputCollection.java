package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.ui.api.Constant;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.GridHelper;
import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputCollection<T,SELECT_ITEM> extends GridHelper.Grid<T,SELECT_ITEM> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	protected InputChoice<?, ?, ?, ?, ?, SELECT_ITEM> inputChoice;

	public InputCollection(Class<T> elementClass,Class<?> elementObjectClass,Class<SELECT_ITEM> sourceClass,Class<?> sourceObjectClass) {
		super(elementClass,elementObjectClass,sourceClass,sourceObjectClass);
		getAddCommand().setProperty(Constant.INPUT_VALUE_IS_NOT_REQUIRED, Boolean.TRUE);
		getAddCommand().setNameRendered(getAddCommand().getMappedIcon()==null);
		
		getDeleteCommand().setProperty(Constant.INPUT_VALUE_IS_NOT_REQUIRED, Boolean.TRUE);
		getDeleteCommand().setNameRendered(getDeleteCommand().getMappedIcon()==null);
	}
	
	public InputCollection<T,SELECT_ITEM> setInputChoice(InputChoice<?, ?, ?, ?, ?, SELECT_ITEM> inputChoice){
		this.inputChoice = inputChoice;
		if(this.inputChoice!=null){
			getCollection().setSources(this.inputChoice.getList());
			getCollection().setIsEachElementHasSource(Boolean.TRUE);
		}
		return this;
	}
	
	protected Object getSelectItemValue(SELECT_ITEM selectItem){
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public InputCollection<T,SELECT_ITEM> setInputChoice(FormOneData<?,?,?,?,?,?> form,String fieldName){
		setInputChoice(form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputChoice.class,fieldName));
		form.getInputCollections().add((InputCollection<?, SELECT_ITEM>) this);
		return this;
	}
	
	@Override
	protected void add() {
		if(inputChoice==null)
			super.add();
		else if(inputChoice.getValue()!=null)
			collection.addOne(inputChoice.getValue());
	}
	
	/**/

	
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
		protected void write(Object object, String fieldName, Object value) {
			if(GlobalIdentifier.FIELD_CODE.equals(fieldName) && object instanceof AbstractIdentifiable)
				((AbstractIdentifiable)object).setCode((String) value);
			else if(GlobalIdentifier.FIELD_NAME.equals(fieldName) && object instanceof AbstractIdentifiable)
				((AbstractIdentifiable)object).setName((String) value);
			else if(GlobalIdentifier.FIELD_OTHER_DETAILS.equals(fieldName) && object instanceof AbstractIdentifiable)
				((AbstractIdentifiable)object).setOtherDetails((String) value);
			else
				super.write(object, fieldName, value);
		}
		
		public static class Enumeration<T> extends Element<T> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@Input @InputText @NotNull private String name;
			
			public static final String FIELD_NAME = "name";
		}
		
	}
	
	/**/
	
	public static interface Listener<T> {
		
		public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;

		}
		
	}
}
