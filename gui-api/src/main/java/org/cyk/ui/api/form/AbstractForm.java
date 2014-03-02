package org.cyk.ui.api.form;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;

import org.cyk.ui.api.AbstractView;

public abstract class AbstractForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractView implements IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;
	
	@Getter protected IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> parent;
	@Getter protected FORM model;
	protected OUTPUTLABEL currentLabel;
	
	@Getter protected Collection<IFormField> fields;
	
	@Getter protected Collection<IFormCommand> commands;
	
	//@Getter protected DefaultActionCommand command;
	
	@Override
	public void build() {
		model = createModel();
		super.build();
	}
	
	@Override
	public void setParent(IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form) {
		this.parent = form;
		//do set injected beans
		commonUtils = form.getCommonUtils();
		commonMethodProvider = form.getCommonMethodProvider();
	}
	
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
