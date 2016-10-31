package org.cyk.system.test.model.actor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.test.model.actor.Actor;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.ReportColumn;

@Getter @Setter
public class ActorReportTableRow implements Serializable {

	private static final long serialVersionUID = 3301670352102417326L;

	@Input @InputText @ReportColumn private String name,lastnames,contacts;
	
	public ActorReportTableRow(Actor actor) {
		name = actor.getPerson().getName();
		lastnames = actor.getPerson().getNames();
		//contacts = actor.getPerson().getName();
	}
	
}
