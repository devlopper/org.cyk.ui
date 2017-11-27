package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.userinterface.container.Form.Master;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class IdentifiableConsultPage extends org.cyk.ui.web.api.resources.page.IdentifiableConsultPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class< ? extends Master> getFormMasterClass() {
		return IdentifiableEditPage.FormMaster.class;
	}
	
}
