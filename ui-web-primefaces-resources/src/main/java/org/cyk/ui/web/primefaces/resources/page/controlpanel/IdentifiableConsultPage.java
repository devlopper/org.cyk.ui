package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Master;

@Named @ViewScoped @Getter @Setter
public class IdentifiableConsultPage extends org.cyk.ui.web.api.resources.page.IdentifiableConsultPage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Class<? extends Master> getFormMasterClass() {
		Class<? extends Master> clazz = super.getFormMasterClass();
		if(Form.Master.class.equals(clazz))
			clazz = ClassHelper.getInstance().getMapping(IdentifiableConsultPageFormMaster.class);
		return clazz;
	}
	
	
}
