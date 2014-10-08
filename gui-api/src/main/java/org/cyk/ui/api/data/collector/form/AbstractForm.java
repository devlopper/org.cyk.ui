package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.AbstractView;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;

public abstract class AbstractForm<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends AbstractView implements Form<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>,CommandListener, Serializable {

	private static final long serialVersionUID = -1043478880255116994L;
	
	@Getter @Setter protected DATA data;
	@Getter @Setter protected String fieldsRequiredMessage;
	@Getter @Setter protected Boolean showCommands = Boolean.TRUE,editable,dynamic=Boolean.TRUE;
	@Getter protected UICommandable submitCommandable;
	
	@Getter protected Collection<FormListener<MODEL,ROW,LABEL,CONTROL,SELECTITEM>> formListeners = new ArrayList<>();
	
	/**/
	

	/**/
	
	@Override
	public Boolean validate(UICommand command, Object parameter) {
		return Boolean.TRUE;
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {}
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		if(Boolean.TRUE.equals(notifyAfterServe(command, AfterServeState.SUCCEED)))
			showInfoMessage(notificationMessageIdAfterServe(command, parameter, AfterServeState.SUCCEED));
		return null;
	}

	@Override
	public Object fail(UICommand command, Object parameter,Throwable throwable) {
		if(Boolean.TRUE.equals(notifyAfterServe(command, AfterServeState.FAIL)))
			showErrorMessage(notificationMessageIdAfterServe(command, parameter, AfterServeState.FAIL));
		return null;
	}
	
	/**/
	
	@Override
	public Boolean notifyAfterServe(UICommand command, AfterServeState state) {
		return Boolean.TRUE.equals(state.getNotify());
	}
	
	@Override
	public String notificationMessageIdAfterServe(UICommand command, Object parameter, AfterServeState state) {
		return state.getNotificationMessageId();
	}
	
	/**/

	protected Boolean commandEquals(UICommandable aCommandable,UICommand aCommand){
		return aCommandable.getCommand() == aCommand;
	}
}
