package org.cyk.ui.web.primefaces.page.party.person.__OLD__;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.web.primefaces.page.party.AbstractPersonEditPage;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

//@Named @ViewScoped 
@Getter @Setter
public class PersonEditPage extends AbstractPersonEditPage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private org.cyk.utility.common.userinterface.container.Form.Master form2;
	private Form d = new Form();
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		Person person = new Person();
		person.setGlobalIdentifier(new GlobalIdentifier());
		//person.getGlobalIdentifier().setImage(new File(FileHelper.getInstance().get(ContextListener.class, "image001.png")));
		//System.out.println("CreatePersonPage.initialisation() : "+person.getGlobalIdentifier().getImage());
		form2 = null;//new org.cyk.utility.common.userinterface.container.Form.Master(this,person,SubmitCommandActionAdapter.class);
		
		org.cyk.utility.common.userinterface.container.Form.Detail detail = form2.getDetail();
		detail.getLayout().setType(org.cyk.utility.common.userinterface.Layout.Type.ADAPTIVE);
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
		//detail.add("otherDetails");
		
		form2.build();
		
		form2.getSubmitCommand().getPropertiesMap().setAjax(Boolean.FALSE);//because of file upload
		
	}
	
	@Override
	protected Person getPerson() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends /*AbstractPersonEditFormModel.Extends.Default*/AbstractPersonEditPage.AbstractForm<Person> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;

		@Override
		protected Person getPerson() {
			return identifiable;
		}
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class SubmitCommandActionAdapter extends org.cyk.utility.common.userinterface.container.Form.Master.SubmitCommandActionAdapter implements Serializable{
		private static final long serialVersionUID = 1L;

		@Override
		protected Object __execute__() {
			System.out.println("PersonEditPage.SubmitCommandActionAdapter.__execute__()");
			super.__execute__();
			inject(GenericBusiness.class).create((Person) form.getObject());
			//System.out.println(ToStringBuilder.reflectionToString(form.getObject(), ToStringStyle.MULTI_LINE_STYLE));
			return null;
		}
		
	}
}

