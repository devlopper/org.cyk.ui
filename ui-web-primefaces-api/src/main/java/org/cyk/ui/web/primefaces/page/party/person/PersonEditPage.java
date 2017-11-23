package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.window.EditWindow;

@Named @ViewScoped @Getter @Setter
public class PersonEditPage extends EditWindow implements Serializable {
	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter @Accessors(chain=true)
	public static class FormMaster extends Form.Master implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Component prepare() {
			//controls
			//inputs
			Form.Detail detail = getDetail();
			detail.getLayout().setType(Layout.Type.ADAPTIVE);
			detail.setFieldsObjectFromMaster("globalIdentifier");
			detail.add("code");
			detail.add("image",1,3).addBreak();
			detail.add("name").addBreak();
			detail.setFieldsObjectFromMaster();
			detail.add("lastnames").addBreak();
			detail.add("nationality");
			detail.add("sex").addBreak();
			detail.setFieldsObjectFromMaster("globalIdentifier");
			detail.add("description").addBreak();
			detail.add("otherDetails");
			//commands
			setSubmitCommandActionAdapterClass(SubmitCommandActionAdapter.class);
			return this;
		}
		
		@Getter @Setter @Accessors(chain=true)
		public static class SubmitCommandActionAdapter extends org.cyk.utility.common.userinterface.container.Form.Master.SubmitCommandActionAdapter.Web implements Serializable{
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void create() {
				inject(GenericBusiness.class).create((Person) form.getObject());
			}
			
			@Override
			protected void update() {
				inject(GenericBusiness.class).update((Person) form.getObject());
			}
			
			@Override
			protected void delete() {
				inject(GenericBusiness.class).delete((Person) form.getObject());
			}
					
		}
		
	}

}

