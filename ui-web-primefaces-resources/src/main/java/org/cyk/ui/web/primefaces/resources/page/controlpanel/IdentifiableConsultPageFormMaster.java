package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;

@Getter @Setter @Accessors(chain=true)
public class IdentifiableConsultPageFormMaster extends IdentifiableEditPage.FormMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __prepare__() {
		super.__prepare__();
		//Form.Detail detail = getDetail();
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		if(ClassHelper.getInstance().isHierarchy(actionOnClass)){
			DataTable dataTable = instanciateDataTable(actionOnClass,null,null,Boolean.TRUE);
			dataTable.getPropertiesMap().setOnPrepareAddMenu(Boolean.TRUE);
			dataTable.getPropertiesMap().setOnPrepareAddColumnAction(Boolean.TRUE);
			dataTable.prepare();
			dataTable.build();
		}
	}
	
}