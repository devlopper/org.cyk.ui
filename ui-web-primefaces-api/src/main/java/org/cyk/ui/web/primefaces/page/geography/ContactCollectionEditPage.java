package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.data.collector.control.InputCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ContactCollectionEditPage extends AbstractCrudOnePage<ContactCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private InputCollection<PhoneNumberItem> phoneNumberCollection;
	private InputCollection<ElectronicMailAddressItem> electronicMailAddressCollection;
	private InputCollection<LocationItem> locationCollection;
	private InputCollection<PostalBoxItem> postalBoxCollection;
	private InputCollection<WebsiteItem> websiteCollection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		inject(ContactCollectionBusiness.class).prepare(identifiable, crud);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		phoneNumberCollection = instanciateInputCollection(PhoneNumber.class);	
		electronicMailAddressCollection = instanciateInputCollection(ElectronicMailAddress.class);
		locationCollection = instanciateInputCollection(Location.class);
		postalBoxCollection = instanciateInputCollection(PostalBox.class);
		websiteCollection = instanciateInputCollection(Website.class);
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
	
	@Getter @Setter
	public static class LocationItem extends AbstractContactItem<Location> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		@NotNull @Input @InputChoice @InputOneChoice @InputOneCombo private LocationType type;
		@NotNull @Input @InputChoice @InputOneChoice @InputOneCombo private Locality locality;
		@NotNull @Input @InputText private String otherDetails;
		/*
		@NotNull @Input @InputText protected BigDecimal longitude;
		@NotNull @Input @InputText protected BigDecimal latitude;
		@NotNull @Input @InputText protected BigDecimal altitude;
		*/
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_LOCALITY = "locality";
		public static final String FIELD_OTHER_DETAILS = "otherDetails";
		
	}
	
	@Getter @Setter
	public static class PostalBoxItem extends AbstractContactItem<PostalBox> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		@NotNull @Input @InputText protected String value;
		
		public static final String FIELD_VALUE = "value";
		
	}
	
	@Getter @Setter
	public static class WebsiteItem extends AbstractContactItem<Website> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		@NotNull @Input @InputChoice @InputOneChoice @InputOneCombo
		private UniformResourceLocator uniformResourceLocator;
		
		public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
		
	}
}
