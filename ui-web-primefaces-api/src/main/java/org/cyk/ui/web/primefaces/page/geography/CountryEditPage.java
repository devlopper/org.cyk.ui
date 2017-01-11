package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CountryEditPage extends AbstractCrudOnePage<Country> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		setChoices(Form.FIELD_CONTINENT, inject(LocalityBusiness.class).findByType(
				inject(LocalityTypeBusiness.class).find(RootConstant.Code.LocalityType.CONTINENT)));
	}
	
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<Country> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo private Locality continent;
		@Input @InputNumber @Sequence(direction=Direction.AFTER,field=FIELD_NAME) private Integer phoneNumberCode;
		
		public static final String FIELD_CONTINENT = "continent";
		public static final String FIELD_PHONE_NUMBER_CODE = "phoneNumberCode";
		
		@Override @Sequence(direction=Direction.AFTER,field=FIELD_CONTINENT)
		public String getCode() {
			return super.getCode();
		}
		
		@Override @Sequence(direction=Direction.AFTER,field=FIELD_CODE)
		public String getName() {
			return super.getName();
		}
		
		@Override
		public void read() {
			super.read();
			continent = identifiable.getLocality();
		}
		
		@Override
		public void write() {
			super.write();
			identifiable.setContinent(continent);
		}
		
	}
	
}
