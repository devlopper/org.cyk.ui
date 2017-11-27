package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Master;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class IdentifiableEditPage extends org.cyk.ui.web.api.resources.page.IdentifiableEditPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends Master> getFormMasterClass() {
		Class<? extends Master> clazz = super.getFormMasterClass();
		if(Form.Master.class.equals(clazz))
			clazz = FormMaster.class;
		return clazz;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class FormMaster extends Form.Master.Web implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Component prepare() {
			//controls
			//inputs
			Form.Detail detail = getDetail();
			detail.getLayout().setType(Layout.Type.ADAPTIVE);
			detail.setFieldsObjectFromMaster("globalIdentifier");
			detail.add("code").addBreak();
			detail.add("name").addBreak();
			return this;
		}
		
	}
	
}
