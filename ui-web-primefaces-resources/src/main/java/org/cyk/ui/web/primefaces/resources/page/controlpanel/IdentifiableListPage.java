package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.RequestHelper;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class IdentifiableListPage extends org.cyk.ui.web.api.resources.page.IdentifiableListPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends org.cyk.utility.common.userinterface.collection.DataTable> getDataTableClass() {
		Class<? extends org.cyk.utility.common.userinterface.collection.DataTable> clazz = super.getDataTableClass();
		System.out.println("IdentifiableListPage.getDataTableClass() : "+clazz);
		if(org.cyk.utility.common.userinterface.collection.DataTable.class.equals(clazz))
			clazz = DataTable.class;
		return clazz;
	}
	
	public static class DataTable extends org.cyk.utility.common.userinterface.collection.DataTable implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public DataTable() {
			super(RequestHelper.getInstance().getParameterAsClass(UniformResourceLocatorHelper.QueryParameter.Name.CLASS));
			//columns
			addColumn("code", "globalIdentifier.code");
			addColumn("name", "globalIdentifier.name");
			
			//rows
			addManyRow(InstanceHelper.getInstance().get(getActionOnClass()));
		}
		
	}
}
