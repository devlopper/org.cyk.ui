package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.ui.web.primefaces.CommandHelper.Command;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class CommandDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private Command defaultCommand,successCommand,failureCommand,customSuccessMessageCommand,customFailureMessageCommand,parameterCommand
		,successConfirmableCommand,failureConfirmableCommand;
	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		defaultCommand = (Command) new Command().setName("Default").setIcon("ui-icon-trash");
		successCommand = (Command) new Command(){
			private static final long serialVersionUID = 1L;
			protected Object __execute__() {return null;}
		}.setName("Success").setIcon("ui-icon-trash").setIsNotifiableOnStatusSuccess(Boolean.TRUE);
		failureCommand = (Command) new Command(){
			private static final long serialVersionUID = 1L;
			protected Object __execute__() {ThrowableHelper.getInstance().throw_("!!!ERROR!!!"); return null;}
		}.setName("Failure").setIcon("ui-icon-trash");
		customSuccessMessageCommand = (Command) new Command(){
			private static final long serialVersionUID = 1L;
			protected Object __execute__() {return null;}
		}.setName("Custom Success Message").setIcon("ui-icon-trash").setIsNotifiableOnStatusSuccess(Boolean.TRUE)
				.setStatusNotificationStringIdentifier(Command.Status.SUCCESS, "my_success_message_string_dentifier");
		customFailureMessageCommand = (Command) new Command(){
			private static final long serialVersionUID = 1L;
			protected Object __execute__() {ThrowableHelper.getInstance().throw_("!!!ERROR!!!"); return null;}
		}.setName("Custom Failure Message").setIcon("ui-icon-trash")
				.setStatusNotificationStringIdentifier(Command.Status.FAILURE, "my_failure_message_string_dentifier");
		parameterCommand = (Command) new Command(){
			private static final long serialVersionUID = 1L;
			protected Object __execute__() {
				System.out.println(getInput());
				return null;
			}
		}.setName("Parameter").setIcon("ui-icon-trash");
		
		successConfirmableCommand = (Command) new Command(){
			private static final long serialVersionUID = 1L;
			protected Object __execute__() {return null;}
		}.setName("Confirm Success").setIcon("ui-icon-trash").setIsNotifiableOnStatusSuccess(Boolean.TRUE).setIsConfirmable(Boolean.TRUE);
		failureConfirmableCommand = (Command) new Command(){
			private static final long serialVersionUID = 1L;
			protected Object __execute__() {ThrowableHelper.getInstance().throw_("!!!ERROR!!!"); return null;}
		}.setName("Confirm Failure").setIcon("ui-icon-trash").setIsConfirmable(Boolean.TRUE);
	}
	
}
