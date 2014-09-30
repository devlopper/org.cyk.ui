/*package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.ui.api.AbstractUIPart;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.UIMessageManager.Text;
import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.command.UICommand.AbstractNotifyOnSucceedMethod;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.editor.input.AbstractInputComponent;
import org.cyk.utility.common.AbstractMethod;

@Getter @Setter
public abstract class AbstractForm<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractUIPart implements Form<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM>,CommandListener, Serializable {

	private static final long serialVersionUID = -1043478880255116994L;
	
	
	protected DATA data;
	
	protected String fieldsRequiredMessage;
	protected Boolean editable = Boolean.FALSE;
	 input fields 
	protected List<AbstractInputComponent<?>> inputs = new ArrayList<>();
	
	protected Stack<FormPart<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM>> stack = new Stack<>();  to keep navigation trace 
	@Setter @Getter protected ValidationPolicy validationPolicy;
	@Getter protected UICommandable submitCommandable,backCommandable,resetCommandable,closeCommandable,switchCommandable; commands 
	@Getter protected Collection<UICommand> commands;
	//TODO use listener instead
	@Setter @Getter protected AbstractMethod<Object, Object> submitMethodMain,submitDetails,onDebugSubmitMethodMain;
	@Getter @Setter protected Boolean showCommands = Boolean.TRUE,submitMethodMainExecuted=Boolean.FALSE;
	
	@Getter @Setter protected UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> window;

	@Getter @Setter protected UIMenu menu = new DefaultMenu();
	@Getter protected Object objectModel;
	@Getter @Setter protected Crud crud;
	
	@Getter protected Collection<FormListener<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> listeners = new ArrayList<>();
	
	 
	
	{
		submitCommandable = UIManager.getInstance().createCommandable("command.save",IconType.ACTION_OK, null,EventListener.NONE,ProcessGroup.FORM);
		submitCommandable.getCommand().setNotifyOnSucceedMethod(new AbstractNotifyOnSucceedMethod<Object>() {
			private static final long serialVersionUID = 8882624469264441089L;
			@Override
			protected Boolean __execute__(Object parameter) {
				return Boolean.TRUE.equals(getSubmitMethodMainExecuted());
			}
		});
		submitCommandable.getCommand().getListeners().add(this);
		submitCommandable.setLabel(text("bouton.envoyer"));
	}
	
	public AbstractForm(DATA data) {
		super();
		this.data = data;
	}
	
	
	
	@Override
	public void transfer(UICommand command, Object parameter) {
		try {
			for(AbstractInputComponent<?> input : inputs)
				input.updateValue();
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
		getWindow().getMessageManager().message(SeverityType.INFO, new Text("command.serve.success.summary"), new Text("command.serve.success.details"));
		return null;
	}

	@Override
	public Object fail(UICommand command, Object parameter,Throwable throwable) {
		getWindow().getMessageManager().message(SeverityType.ERROR, new Text("command.serve.error.summary"), new Text("command.serve.error.details"));
		return null;
	}
	
	
	
	@Override
	public FormPart<DATA,FORM, OUTPUTLABEL, INPUT, SELECTITEM> getSelected() {
		return stack.peek();
	}
	
	@Override 
	public void updateValues() throws Exception {
		stack.peek().updateFieldsValue();
	}
	
	@Override
	public Boolean getRoot() {
		return stack.size()==1;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public <T> T findInput(Class<T> inputClass,String fieldName){
		for(AbstractInputComponent<?> input : inputs)
			if(input.getField().getName().equals(fieldName) && inputClass.isAssignableFrom(input.getClass()))
				return (T) input;
		return null;
	}
	
}
*/