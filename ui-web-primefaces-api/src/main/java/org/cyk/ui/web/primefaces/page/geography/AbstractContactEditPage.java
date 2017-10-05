package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractContactEditPage<IDENTIFIABLE extends Contact> extends AbstractCrudOnePage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		setChoices(AbstractForm.FIELD_COLLECTION, inject(ContactCollectionBusiness.class).findAll(), identifiable.getCollection());
	}
	
	@Override
	protected IDENTIFIABLE instanciateIdentifiable() {
		IDENTIFIABLE contact =  super.instanciateIdentifiable();
		contact.setCollection(webManager.getIdentifiableFromRequestParameter(ContactCollection.class,Boolean.TRUE));
		return contact;
	}
	
	@Getter @Setter
	public static abstract class AbstractForm<IDENTIFIABLE extends Contact> extends AbstractFormModel<IDENTIFIABLE> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo protected ContactCollection collection;
		
		@Input @InputNumber protected Byte orderNumber;
		
		public static final String FIELD_COLLECTION = "collection";
		public static final String FIELD_ORDER_NUMBER = "orderNumber";
		
	}
	
}
