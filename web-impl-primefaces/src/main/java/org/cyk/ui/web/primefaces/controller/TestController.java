package org.cyk.ui.web.primefaces.controller;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.DefaultActionCommand;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

@Named @ViewScoped @Getter @Setter
public class TestController extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject
	private MessageManager messageManager;
	
	private String name;
	
	private DefaultActionCommand command;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		command = new DefaultActionCommand();
		command.setMessageManager(messageManager);
		
		command.setExecuteMethod(new AbstractMethod<Object, Object>() {
			
			private static final long serialVersionUID = -3554292967012003944L;

			@Override
			protected Object __execute__(Object parameter) {
				// TODO Auto-generated method stub
				return null;
			}
		});

	}
	
	public String action(){
		System.out.println("TestController.action()");
		messageManager.addInfo(new Date(), false);
		return null;
	}
	
}
