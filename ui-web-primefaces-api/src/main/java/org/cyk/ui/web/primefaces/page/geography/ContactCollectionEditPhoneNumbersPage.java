package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.geography.PhoneNumberBusiness;
import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.helper.CollectionHelper;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ContactCollectionEditPhoneNumbersPage extends AbstractCrudOnePage<ContactCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private ItemCollection<PhoneNumberItem,PhoneNumber,ContactCollection> phoneNumberCollection;
	private IdentifiableRuntimeCollection<PhoneNumber> phoneNumbers = new IdentifiableRuntimeCollection<>();
	private List<SelectItem> phoneNumberTypes = WebManager.getInstance().getSelectItems(PhoneNumberType.class);
	private List<SelectItem> countries = WebManager.getInstance().getSelectItems(PhoneNumberType.class);
	private List<SelectItem> locationTypes = WebManager.getInstance().getSelectItems(LocationType.class);
	
	@Override
	protected void initialisation() {
		super.initialisation();
		phoneNumbers.addMany(CollectionHelper.getInstance().cast(PhoneNumber.class,inject(PhoneNumberBusiness.class).findByCollection(identifiable)));
		phoneNumberCollection = createItemCollection(PhoneNumberItem.class, PhoneNumber.class,identifiable 
				,new org.cyk.ui.web.primefaces.ItemCollectionAdapter<PhoneNumberItem,PhoneNumber,ContactCollection>(identifiable,crud,form,PhoneNumber.class){
			private static final long serialVersionUID = 1L;
			
			@Override
			public IdentifiableRuntimeCollection<PhoneNumber> getRuntimeCollection() {
				return phoneNumbers.setSynchonizationEnabled(Boolean.TRUE);
			}
			
			@Override
			public Collection<PhoneNumber> findByCollection(ContactCollection collection) {
				return CollectionHelper.getInstance().cast(PhoneNumber.class,inject(PhoneNumberBusiness.class).findByCollection(identifiable));
			}
			
			@Override
			public String getFieldOneItemMasterSelectedName() {
				return null;
			}
			
		});
	}
	
	@Override
	protected Class<?> __formModelClass__() {
		return Form.class;
	}
	
	@Override
	public Class<?> getFormModelClass() {
		return Form.class;
	}
	
	/**/
	
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<ContactCollection> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
	}
	
	/**/
	
	@Getter @Setter
	public static abstract class AbstractContactItem<CONTACT extends Contact> extends AbstractItemCollectionItem<CONTACT> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		protected String value;
	}
	
	@Getter @Setter
	public static class PhoneNumberItem extends AbstractContactItem<PhoneNumber> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		private PhoneNumberType phoneNumberType;
		private Country country;
		private LocationType locationType;
		
	}
	
	@Getter @Setter
	public static class ElectronicMailItem extends AbstractContactItem<ElectronicMail> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
	}
}
