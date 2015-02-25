package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.UIManager;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.DualListModel;

@Getter @Setter
public class InputManyPickList<VALUE_TYPE> extends AbstractInputManyChoice<VALUE_TYPE> implements org.cyk.ui.web.api.data.collector.control.WebInputManyPickList<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputManyPickList<VALUE_TYPE,DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = 1490472924426610838L;

	private DualListModel<VALUE_TYPE> dualListModel;
	private String sourceCaption,targetCaption;
	private Boolean onTransferDisabled=Boolean.TRUE;
	
	public InputManyPickList() {
		dualListModel = new DualListModel<>();
		sourceCaption = UIManager.getInstance().text("list");
		targetCaption = UIManager.getInstance().text("selecteditems");
	}

	@Override
	public void applyValueToField() throws IllegalAccessException {
		FieldUtils.writeField(field, object, dualListModel.getTarget(), Boolean.TRUE);
	}
	
	public void onTransfer(TransferEvent event) {}
	
	public void onSelect(SelectEvent event) {}
     
    public void onUnselect(UnselectEvent event) {}
     
    public void onReorder() {} 
	
}
