package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

@Named @ViewScoped @Getter @Setter
public class ContactCollectionEditPage extends AbstractCrudOnePage<ContactCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private ItemCollection<PhoneNumberItem,PhoneNumber> phoneNumberCollection;
	private ItemCollection<ElectronicMailItem,ElectronicMail> electronicMailCollection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		/*
		phoneNumberCollection = createItemCollection(PhoneNumberItem.class, PhoneNumber.class
				,new ItemCollectionWebAdapter<PhoneNumberItem,PhoneNumber>(){
			private static final long serialVersionUID = -3872058204105902514L;
			@Override
			public Collection<PhoneNumber> load() {
				return inject(PhoneNumberBusiness.class).findByCollection(identifiable);
			}
			@Override
			public void instanciated(AbstractItemCollection<PhoneNumberItem, PhoneNumber,SelectItem> itemCollection,PhoneNumberItem item) {
				super.instanciated(itemCollection, item);
				
		
			}	
			@Override
			public Crud getCrud() {
				return crud;
			}
			@Override
			public Boolean isShowAddButton() {
				return Boolean.TRUE;
			}
		});
		
		electronicMailCollection = createItemCollection(ElectronicMailItem.class, ElectronicMail.class
				,new ItemCollectionWebAdapter<ElectronicMailItem,ElectronicMail>(){
			private static final long serialVersionUID = -3872058204105902514L;
			@Override
			public Collection<ElectronicMail> load() {
				return inject(ElectronicMailBusiness.class).findByCollection(identifiable);
			}
			@Override
			public void instanciated(AbstractItemCollection<ElectronicMailItem, ElectronicMail,SelectItem> itemCollection,ElectronicMailItem item) {
				super.instanciated(itemCollection, item);
				
		
			}	
			@Override
			public Crud getCrud() {
				return crud;
			}
			@Override
			public Boolean isShowAddButton() {
				return Boolean.TRUE;
			}
		});
		*/
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
		
	}
	
	@Getter @Setter
	public static class ElectronicMailItem extends AbstractContactItem<ElectronicMail> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
	}
}
