package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.event.Event;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;

@Getter @Setter
public class EventSearchResultFormModel extends AbstractFormModel<Event> implements Serializable {

	private static final long serialVersionUID = -392868128587378419L;

	@Input private String type,object,comments,date/*,contacts*/;
	
	@Override
	public void read() {
		super.read();
		type = identifiable.getType().getName();
		object = identifiable.getObject();
		comments = identifiable.getComments();
		date = UIManager.getInstance().getTimeBusiness().formatDateTime(identifiable.getPeriod().getFromDate());
		//contacts = identifiable.getType().getName();
		
	}
	
}
