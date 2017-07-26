package org.cyk.ui.web.primefaces.page.time;

import java.io.Serializable;

import org.cyk.system.root.model.time.ScheduleIdentifiableGlobalIdentifier;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScheduleIdentifiableGlobalIdentifierEditPage extends AbstractCrudOnePage<ScheduleIdentifiableGlobalIdentifier> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<ScheduleIdentifiableGlobalIdentifier> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		
		
	}

}
