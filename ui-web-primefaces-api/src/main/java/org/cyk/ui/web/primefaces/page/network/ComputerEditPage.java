package org.cyk.ui.web.primefaces.page.network;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.network.Computer;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ComputerEditPage extends AbstractCrudOnePage<Computer> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	/**/
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<Computer> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
		@Input @InputText private String ipAddress,ipAddressName;
		
		public static final String FIELD_IP_ADDRESS = "ipAddress";
		public static final String FIELD_IP_ADDRESS_NAME = "ipAddressName";
	}

}
