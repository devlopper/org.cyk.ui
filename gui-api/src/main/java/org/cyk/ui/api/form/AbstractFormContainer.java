package org.cyk.ui.api.form;

import java.io.Serializable;
import java.util.Stack;

import lombok.Getter;

import org.cyk.ui.api.AbstractView;
import org.cyk.ui.api.component.IComponent;

public abstract class AbstractFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractView implements IFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;
	
	protected Stack<IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> stack = new Stack<>();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		switchTo(mainModel());
	}
	
	protected abstract IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> mainModel();

	@Override
	public void switchTo(IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> selected) {
		stack.push(selected);
	}
	
	@Override
	public void back() {
		stack.pop();
	}
	
	@Override
	public void createRow() {
		throw new IllegalStateException("Must not be called");
	}
	
	@Override
	public void add(IComponent<?> component) {
		throw new IllegalStateException("Must not be called");
	}
	
	@Override
	public IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getSelected() {
		return stack.peek();
	}
	
}
