package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.window.ListWindow;

@Named @ViewScoped @Getter @Setter
public class IdentifiableListPage extends org.cyk.ui.web.api.resources.page.IdentifiableListPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends org.cyk.utility.common.userinterface.collection.DataTable> getDataTableClass() {
		Class<? extends org.cyk.utility.common.userinterface.collection.DataTable> clazz = super.getDataTableClass();
		if(org.cyk.utility.common.userinterface.collection.DataTable.class.equals(clazz))
			clazz = ClassHelper.getInstance().getMapping(DataTable.class);
		return clazz;
	}

	@Override
	protected Class<? extends org.cyk.utility.common.userinterface.hierarchy.Hierarchy> getHierarchyClass() {
		Class<? extends org.cyk.utility.common.userinterface.hierarchy.Hierarchy> clazz = super.getHierarchyClass();
		if(org.cyk.utility.common.userinterface.hierarchy.Hierarchy.class.equals(clazz))
			clazz = Hierarchy.class;
		return clazz;
	}
	
	public static class DataTable extends ListWindow.DataTable implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}

	public static class Hierarchy extends ListWindow.Hierarchy implements Serializable {
		private static final long serialVersionUID = 1L;
			
	}
}
