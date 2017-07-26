package org.cyk.ui.web.primefaces.page.time;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.ui.api.model.time.InstantIntervalFormModel;
import org.cyk.ui.web.primefaces.page.AbstractCollectionItemEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;

@Named @ViewScoped @Getter @Setter
public class ScheduleItemEditPage extends AbstractCollectionItemEditPage.Extends<ScheduleItem,Schedule> implements Serializable {
	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter @FieldOverride(name=AbstractForm.FIELD_COLLECTION,type=Schedule.class)
	public static class Form extends AbstractForm.AbstractDefault<ScheduleItem,Schedule> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@IncludeInputs protected InstantIntervalFormModel instantInterval=new InstantIntervalFormModel();
		
		@Override
		public void read() {
			super.read();
			instantInterval.set(identifiable.getInstantInterval());
		}
		
		@Override
		public void write() {
			super.write();
			instantInterval.write(identifiable.getInstantInterval());
		}
		
		public static final String FIELD_INSTANT_INTERVAL = "instantInterval";
	
	}

}
