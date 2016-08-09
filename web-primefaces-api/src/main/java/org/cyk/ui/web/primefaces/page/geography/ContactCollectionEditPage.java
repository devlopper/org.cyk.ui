package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

@Named @ViewScoped @Getter @Setter
public class ContactCollectionEditPage extends AbstractCrudOnePage<ContactCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	public static class Form extends AbstractBusinessIdentifiedEditFormModel<ContactCollection> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
	}
	
	public static class Adapter extends AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.Adapter.Default<ContactCollection> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(ContactCollection.class);
			FormConfiguration configuration = createFormConfiguration(Crud.CREATE, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addRequiredFieldNames(Form.FIELD_CODE,Form.FIELD_NAME);
			
			configuration = createFormConfiguration(Crud.UPDATE, UIManager.getInstance().businessEntityInfos(entityTypeClass).getUserInterface().getLabelId());
			configuration.addRequiredFieldNames(Form.FIELD_CODE,Form.FIELD_NAME);
			
		}
		
	}
	
}
