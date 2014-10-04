package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.AbstractView;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.Input;

public abstract class AbstractFormOneData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends AbstractView implements FormOneData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>,CommandListener, Serializable {

	private static final long serialVersionUID = -1043478880255116994L;
	
	@Getter @Setter protected DATA data;
	@Getter @Setter protected String fieldsRequiredMessage;
	@Getter @Setter protected Boolean showCommands = Boolean.TRUE,editable,dynamic=Boolean.TRUE;
	@Getter protected Stack<FormData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>> formDatas = new Stack<>(); 
	@Getter protected UICommandable submitCommandable;
	
	@Getter protected Collection<FormListener<MODEL,LABEL,CONTROL,SELECTITEM>> formListeners = new ArrayList<>();
	
	/**/
	
	public AbstractFormOneData() {
		submitCommandable = uiProvider.createCommandable(this,"command.save",IconType.ACTION_OK, EventListener.NONE,ProcessGroup.FORM);
	}
	
	@Override
	public <T> T findInputByFieldName(Class<T> aClass, String fieldName) {
		return getSelectedFormData().findInputByFieldName(aClass, fieldName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addChoices(String fieldName, List<SELECTITEM> choices) {
		findInputByFieldName(InputChoice.class, fieldName).getList().addAll(choices);
	}
	
	@Override
	public void __build__() {
		if(Boolean.TRUE.equals(getDynamic())){
			FormData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> formData = createFormData();
			formData.setData(getData());
			ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet = formData.createControlSet();
			for(Field field : commonUtils.getAllFields(getData().getClass(), Input.class))
				controlSet.row().addField(field);
		}
		super.__build__();
	}
	
	@Override
	public FormData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> getSelectedFormData() {
		return formDatas.isEmpty()?null: formDatas.peek();
	}
	
	/**/
	
	@Override
	public void transfer(UICommand command, Object parameter) {
		try {
			getFormDatas().peek().applyValuesToFields();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
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

	
}
