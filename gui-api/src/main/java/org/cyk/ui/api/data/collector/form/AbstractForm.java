package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.AbstractView;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;

@Getter @Setter
public abstract class AbstractForm<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractView implements Form<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM>,CommandListener, Serializable {

	private static final long serialVersionUID = -1043478880255116994L;
	
	
	protected Stack<FormData<DATA,FORM,ROW,OUTPUTLABEL,INPUT,SELECTITEM>> formDatas = new Stack<>(); 
	
	@Getter protected Collection<FormListener<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> listeners = new ArrayList<>();
	
	/**/
	
	
	/**/
	
	@Override
	public void transfer(UICommand command, Object parameter) {
		try {
			//for(AbstractInputComponent<?> input : inputs)
			//	input.updateValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		System.out.println("AbstractForm.serve()");
	}
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		//getWindow().getMessageManager().message(SeverityType.INFO, new Text("command.serve.success.summary"), new Text("command.serve.success.details"));
		return null;
	}

	@Override
	public Object fail(UICommand command, Object parameter,Throwable throwable) {
		//getWindow().getMessageManager().message(SeverityType.ERROR, new Text("command.serve.error.summary"), new Text("command.serve.error.details"));
		return null;
	}
	
	/**/
	
	
	

	
}
