package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.helper.ClassHelper;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class IdentifiableListPage extends org.cyk.ui.web.api.resources.page.IdentifiableListPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends org.cyk.utility.common.userinterface.collection.DataTable> getDataTableClass() {
		Class<? extends org.cyk.utility.common.userinterface.collection.DataTable> clazz = super.getDataTableClass();
		if(org.cyk.utility.common.userinterface.collection.DataTable.class.equals(clazz))
			clazz = DataTable.class;
		return clazz;
	}
	
	public static class DataTable extends org.cyk.utility.common.userinterface.collection.DataTable implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void __prepare__() {
			addColumnsByFieldNames("globalIdentifier.code","globalIdentifier.name");
			if(ClassHelper.getInstance().isHierarchy(getActionOnClass()))
				addColumnsByFieldNames("parent");
			if(ClassHelper.getInstance().isTyped(getActionOnClass()))
				addColumnsByFieldNames("type");
			
		}
		
	}
}
