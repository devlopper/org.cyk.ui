package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.geography.ContactBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.data.collector.control.InputCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.GridHelper;
import org.cyk.utility.common.helper.GridHelper.Grid;
import org.cyk.utility.common.helper.GridHelper.Grid.Column;
import org.cyk.utility.common.helper.MarkupLanguageHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ContactCollectionEditPage extends AbstractCrudOnePage<ContactCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private InputCollection<PhoneNumberItem> phoneNumberCollection;
	private InputCollection<ElectronicMailAddressItem> electronicMailAddressColection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		identifiable.getItems().setSynchonizationEnabled(Boolean.TRUE);
		//identifiable.getItems().addMany(inject(ContactBusiness.class).findByCollection(identifiable));
			
		phoneNumberCollection = instanciateInputCollection(PhoneNumber.class);	
		electronicMailAddressColection = instanciateInputCollection(ElectronicMailAddress.class);
	}
	
	@SuppressWarnings({ "unchecked" })
	protected <T,IDENTIFIABLE extends AbstractIdentifiable> InputCollection<T> instanciateInputCollection(Class<IDENTIFIABLE> identifiableClass,Class<T> aClass,Class<?> sourceObjectClass,String...fieldNames){
		InputCollection<T> inputCollection = (InputCollection<T>) new GridHelper.Grid.Builder.Adapter.Default<T>()
				.setElementObjectClass(identifiableClass)
				.setElementClassContainerClass(getClass())
				.setElementClass(aClass)
				.setMasterObject(identifiable)
				.setGridCollection((java.util.Collection<Grid<T,?>>) MethodHelper.getInstance().callGet(form, java.util.Collection.class, "inputCollections"))
				.addListener(new GridHelper.Grid.Listener.Adapter<T>(){
					private static final long serialVersionUID = 1L;
					
					@Override
					public void addColumn(Grid<T, ?> grid, Column<?> column) {
						column.getFieldDescriptor().getPropertiesMap().set(MarkupLanguageHelper.Attributes.STYLE_CLASS, "cyk-ui-form-inputfield-relative");
					}
				})
				.setOutput(new InputCollection<>(StringHelper.getInstance().getClazz(identifiableClass),aClass,identifiableClass))
				.execute();	
		inputCollection.getCollection().setMasterElementObjectCollection(MethodHelper.getInstance().callGet(identifiable, CollectionHelper.Instance.class, "items"));
		return inputCollection;
	}
	
	protected <T,IDENTIFIABLE extends AbstractIdentifiable> InputCollection<T> instanciateInputCollection(Class<IDENTIFIABLE> identifiableClass,Class<T> aClass,String...fieldNames){
		return instanciateInputCollection(identifiableClass,aClass,null, fieldNames);
	}
	
	protected <T,IDENTIFIABLE extends AbstractIdentifiable> InputCollection<T> instanciateInputCollection(Class<IDENTIFIABLE> identifiableClass,String...fieldNames){
		return instanciateInputCollection(identifiableClass, null, fieldNames);
	}
		
	/**/
	
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<ContactCollection> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
	}
	
	/**/
	
	@Getter @Setter
	public static abstract class AbstractContactItem<CONTACT extends Contact> extends InputCollection.Element<CONTACT> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
	}
	
	@Getter @Setter
	public static class PhoneNumberItem extends AbstractContactItem<PhoneNumber> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@NotNull @Input @InputChoice @InputOneChoice @InputOneCombo private Country country;
		@NotNull @Input @InputChoice @InputOneChoice @InputOneCombo private PhoneNumberType type;
		@NotNull @Input @InputText protected String number;
		@Input @InputChoice @InputOneChoice @InputOneCombo private LocationType locationType;
		
		public static final String FIELD_COUNTRY = "country";
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_NUMBER = "number";
		public static final String FIELD_LOCATION_TYPE = "locationType";
	}
	
	@Getter @Setter
	public static class ElectronicMailAddressItem extends AbstractContactItem<ElectronicMailAddress> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		@NotNull @Email @Input @InputText protected String address;
		
		public static final String FIELD_ADDRESS = "address";
		
	}
}
