package org.cyk.ui.web.primefaces.page.message.mail;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.message.MailBusiness;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.primefaces.page.AbstractFormPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputManyAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputManyChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;
import org.cyk.utility.common.message.Message;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named @ViewScoped
public class MailSendPage extends AbstractFormPage<MailSendPage.Form> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		form.getSubmitCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4119943624542439662L;
			@Override
			public void serve(UICommand command, Object parameter) {
				super.serve(command, parameter);
				Message message = new Message();
				//message.getReceiverIdentifiers().addAll(commonUtils.getPropertyValue(((Form)form.getData()).getElectronicMails(), String.class, ElectronicMail.FIELD_ADDRESS));
				//for(ElectronicMail electronicMail : ((Form)form.getData()).getElectronicMails())
				//	message.addReceiverIdentifiers(electronicMail.getAddress());
				
				message.setSubject(((Form)form.getData()).getSubject());
				message.setContent(((Form)form.getData()).getContent());
				
				inject(MailBusiness.class).send(message,((Form)form.getData()).getElectronicMailAddresses(), ((Form)form.getData()).getSmtpProperties());
				/*MailSender sender = new MailSender();
				sender.setProperties(inject(SmtpPropertiesBusiness.class).convertToProperties(((Form)form.getData()).getSmtpProperties()));
				sender.setInput(message);
				sender.execute();*/
			}
		});
	}
	
	/**/
	
	@Getter @Setter
	public static class Form implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private SmtpProperties smtpProperties;
		@Input @InputChoice @InputChoiceAutoComplete @InputManyChoice @InputManyAutoComplete @NotNull private List<ElectronicMailAddress> electronicMailAddresses;
		//@Input @InputText private String otherElectronicMails;
		@Input @InputText @NotNull private String subject;
		@Input @InputTextarea @NotNull private String content;
		
	}
	
}
