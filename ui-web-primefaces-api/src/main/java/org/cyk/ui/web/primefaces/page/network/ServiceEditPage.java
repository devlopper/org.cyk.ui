package org.cyk.ui.web.primefaces.page.network;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.network.Computer;
import org.cyk.system.root.model.network.Service;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice.ChoiceSet;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputOneRadio;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ServiceEditPage extends AbstractCrudOnePage<Service> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	/**/
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<Service> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private Computer computer;
		@Input @InputNumber private Integer port;
		@Input @InputChoice(set=ChoiceSet.YES_NO) @InputOneChoice @InputOneRadio @NotNull private Boolean authenticated;
		@Input @InputChoice(set=ChoiceSet.YES_NO) @InputOneChoice @InputOneRadio @NotNull private Boolean secured;
		
		public static final String FIELD_COMPUTER = "computer";
		public static final String FIELD_PORT = "port";
		public static final String FIELD_AUTHENTICATED = "authenticated";
		public static final String FIELD_SECURED = "secured";
	}

}
