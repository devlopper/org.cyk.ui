package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.userinterface.UserInterfaceComponent;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserInterfaceComponentEditPage extends AbstractCrudOnePage<UserInterfaceComponent> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	/**/
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<UserInterfaceComponent> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
		//@Input @InputChoice(set=ChoiceSet.YES_NO) @InputOneChoice @InputOneRadio @NotNull private Boolean expirable;
		//@Input @InputChoice(set=ChoiceSet.YES_NO) @InputOneChoice @InputOneRadio @NotNull private Boolean expired;
		
		@Override
		public void read() {
			super.read();
			
			//expirationDate = identifiable.getPeriod().getToDate();
		}
		
		@Override
		public void write() {
			super.write();
			//identifiable.getPeriod().setToDate(expirationDate);
		}
		
	}

}
