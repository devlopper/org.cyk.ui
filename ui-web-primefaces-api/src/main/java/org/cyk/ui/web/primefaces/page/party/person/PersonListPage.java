package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.userinterface.container.window.ListWindow;

@Named @ViewScoped @Getter @Setter
public class PersonListPage extends ListWindow implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static class DataTable extends org.cyk.utility.common.userinterface.collection.DataTable implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void __prepare__() {
			super.__prepare__();
			//columns
			addColumn("code", "globalIdentifier.code");
			addColumn("name", "globalIdentifier.name");
			addColumn("lastnames", "lastnames");
			addColumn("sex", "sex");
		}
		
	}
	
	
}
