package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.ui.web.primefaces.CommandHelper;
import org.cyk.ui.web.primefaces.CommandHelper.Command;
import org.cyk.ui.web.primefaces.MarkupLanguageHelper;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.Action;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class AjaxStatusDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private CommandHelper.Command command1,command2,command3,command4;
	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		command1 = (Command) org.cyk.utility.common.helper.CommandHelper.getInstance().getCommand().addActionListener(new Action.ActionListener.Adapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public void __execute__(Action<?, ?> action) {
				doAction();
			}
		}).setName("Ajax False Global False").setIsImplemented(Boolean.TRUE);
		MarkupLanguageHelper.setAjax(command1, Boolean.FALSE);
		MarkupLanguageHelper.setGlobal(command1, Boolean.FALSE);
		
		command2 = (Command) org.cyk.utility.common.helper.CommandHelper.getInstance().getCommand().addActionListener(new Action.ActionListener.Adapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public void __execute__(Action<?, ?> action) {
				doAction();
			}
		}).setName("Ajax True Global False").setIsImplemented(Boolean.TRUE);
		MarkupLanguageHelper.setAjax(command2, Boolean.TRUE);
		MarkupLanguageHelper.setGlobal(command2, Boolean.FALSE);
		
		command3 = (Command) org.cyk.utility.common.helper.CommandHelper.getInstance().getCommand().addActionListener(new Action.ActionListener.Adapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public void __execute__(Action<?, ?> action) {
				doAction();
			}
		}).setName("Ajax False Global True").setIsImplemented(Boolean.TRUE);
		MarkupLanguageHelper.setAjax(command3, Boolean.FALSE);
		MarkupLanguageHelper.setGlobal(command3, Boolean.TRUE);
		
		command4 = (Command) org.cyk.utility.common.helper.CommandHelper.getInstance().getCommand().addActionListener(new Action.ActionListener.Adapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public void __execute__(Action<?, ?> action) {
				doAction();
			}
		}).setName("Ajax True Global True").setIsImplemented(Boolean.TRUE);
		MarkupLanguageHelper.setAjax(command4, Boolean.TRUE);
		MarkupLanguageHelper.setGlobal(command4, Boolean.TRUE);
	}
	
	public void doAction(){
		System.out.println("AjaxStatusDemoPage.doAction() : "+System.currentTimeMillis());
	}
	
}
