package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.ui.web.primefaces.page.AbstractCollectionItemEditPage;
import org.cyk.utility.common.annotation.FieldOverride;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserInterfaceMenuItemEditPage extends AbstractCollectionItemEditPage.Extends<UserInterfaceMenuItem,UserInterfaceMenu> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter @FieldOverride(name=AbstractForm.FIELD_COLLECTION,type=UserInterfaceMenu.class)
	public static class Form extends AbstractForm.AbstractDefault<Interval,IntervalCollection> implements Serializable{
		
		private static final long serialVersionUID = -4741435164709063863L;

		
	}

}
