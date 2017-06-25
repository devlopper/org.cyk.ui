package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.userinterface.UserInterfaceCommand;
import org.cyk.system.root.model.userinterface.UserInterfaceComponent;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserInterfaceCommandEditPage extends AbstractCrudOnePage<UserInterfaceCommand> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	/**/
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<UserInterfaceCommand> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
		 @Input @InputChoice @InputOneChoice @InputChoiceAutoComplete @InputOneAutoComplete private UserInterfaceComponent component;
		 @Input @InputChoice @InputOneChoice @InputChoiceAutoComplete @InputOneAutoComplete private UniformResourceLocator uniformResourceLocator;
		 @Input @InputChoice @InputOneChoice @InputChoiceAutoComplete @InputOneAutoComplete private Script script;
		
		@Override
		public void read() {
			super.read();
			
		}
		
		@Override
		public void write() {
			super.write();
			
		}
		
		public static final String FIELD_COMPONENT = "component";
		public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
		public static final String FIELD_SCRIPT = "script";
		
	}

}
