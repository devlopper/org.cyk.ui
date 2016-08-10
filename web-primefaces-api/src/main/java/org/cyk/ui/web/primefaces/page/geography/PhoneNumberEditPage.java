package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Named @ViewScoped @Getter @Setter
public class PhoneNumberEditPage extends AbstractContactEditPage<PhoneNumber> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	public static class Form extends AbstractForm<PhoneNumber> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private Country country;
		@Input @InputChoice @InputOneChoice @InputOneCombo private PhoneNumberType type;
		
		@Override
		public void write() {
			super.write();
			identifiable.setNumber(value);
		}
		
		public static final String FIELD_COUNTRY = "country";
		public static final String FIELD_TYPE = "type";
	}
	
	public static class Adapter extends AbstractAdapter<PhoneNumber> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(PhoneNumber.class);
			FormConfiguration configuration = getFormConfiguration(Crud.CREATE, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addRequiredFieldNames(Form.FIELD_COUNTRY,Form.FIELD_TYPE);
			
			configuration = getFormConfiguration(Crud.UPDATE, UIManager.getInstance().businessEntityInfos(entityTypeClass).getUserInterface().getLabelId());
			configuration.addRequiredFieldNames(Form.FIELD_COUNTRY,Form.FIELD_TYPE);
		}
		
	}
	
}