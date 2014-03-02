package org.cyk.ui.api.form;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;

import org.cyk.ui.api.AbstractView;

public abstract class AbstractForm<OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractView implements IForm<OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;

	protected OUTPUTLABEL currentLabel;
	
	@Getter protected Collection<IFormField> fields;
	
	@Getter protected Collection<IFormCommand> commands;
	
	//@Getter protected DefaultActionCommand command;
	
	/*
	public WebForm(ViewBuilder aViewBuilder,MessageManager messageManager,Object aDto) {
		super(aDto);
		this.viewBuilder = aViewBuilder;
		 
		command = new DefaultActionCommand();
		command.setMessageManager(messageManager);

		command.setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -3554292967012003944L;
			@Override
			protected Object __execute__(Object parameter) {
				System.out.println("WebForm.WebForm(...).new AbstractMethod() {...}.__execute__()");
				return null;
			}
		});		
	}*/
	
}
