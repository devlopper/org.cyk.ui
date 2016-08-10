package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter
public abstract class AbstractContactEditPage<IDENTIFIABLE extends Contact> extends AbstractCrudOnePage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() {
		super.initialisation();
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			@Override
			public String fiedLabel(ControlSet<Object, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,Field field) {
				if(AbstractForm.FIELD_VALUE.equals(field.getName()))
					return text(getValueLabelId());
				return super.fiedLabel(controlSet, field);
			}
		});
	}
	
	protected String getValueLabelId(){
		return businessEntityInfos.getUserInterface().getLabelId();
	}
	
	@Getter @Setter
	public static abstract class AbstractForm<IDENTIFIABLE extends Contact> extends AbstractFormModel<IDENTIFIABLE> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo protected ContactCollection collection;
		
		@Input @InputText protected String value;
		@Input @InputNumber protected Byte orderNumber;
		
		public static final String FIELD_COLLECTION = "collection";
		public static final String FIELD_VALUE = "value";
		public static final String FIELD_ORDER_NUMBER = "orderNumber";
		
		@Override
		public void read() {
			super.read();
			value = Crud.CREATE.equals(((AbstractContactEditPage<?>)window).getCrud()) ? null : RootBusinessLayer.getInstance().getFormatterBusiness().format(identifiable);
		}
		
	}
	
	public static abstract class AbstractAdapter<IDENTIFIABLE extends Contact> extends AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.Adapter.Default<IDENTIFIABLE> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractAdapter(Class<IDENTIFIABLE> aClass) {
			super(aClass);
			FormConfiguration configuration = createFormConfiguration(Crud.CREATE, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addRequiredFieldNames(AbstractForm.FIELD_COLLECTION,AbstractForm.FIELD_VALUE);
			configuration.addFieldNames(AbstractForm.FIELD_ORDER_NUMBER);
			
			configuration = createFormConfiguration(Crud.UPDATE, UIManager.getInstance().businessEntityInfos(entityTypeClass).getUserInterface().getLabelId());
			configuration.addRequiredFieldNames(AbstractForm.FIELD_VALUE);
			configuration.addFieldNames(AbstractForm.FIELD_ORDER_NUMBER);
			
			configuration = createFormConfiguration(Crud.DELETE);
			configuration.addFieldNames(AbstractForm.FIELD_VALUE);
			
		}
		
	}
	
}
