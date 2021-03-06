package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.form.Form;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class IdentifiableConsultPage extends org.cyk.ui.web.api.resources.page.IdentifiableConsultPage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Class<? extends Form> getFormMasterClass() {
		Class<? extends Form> clazz = super.getFormMasterClass();
		if(Form.class.equals(clazz))
			clazz = ClassHelper.getInstance().getMapping(FormMaster.class);
		return clazz;
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class FormMaster extends org.cyk.ui.web.api.resources.page.IdentifiableEditPage.FormMaster implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void __prepare__() {
			super.__prepare__();
			//Form.Detail detail = getDetail();
			Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
			if(ClassHelper.getInstance().isHierarchy(actionOnClass)){
				DataTable dataTable = instanciateDataTable(actionOnClass,null,null,Boolean.TRUE);
				dataTable.prepare();
				dataTable.build();
			}
		}
		
	}
	
}
