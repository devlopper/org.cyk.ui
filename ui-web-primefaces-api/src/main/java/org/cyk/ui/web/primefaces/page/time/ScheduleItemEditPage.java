package org.cyk.ui.web.primefaces.page.time;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.ui.web.primefaces.page.AbstractCollectionItemEditPage;
import org.cyk.utility.common.annotation.FieldOverride;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ScheduleItemEditPage extends AbstractCollectionItemEditPage.Extends<ScheduleItem,Schedule> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter @FieldOverride(name=AbstractForm.FIELD_COLLECTION,type=Schedule.class)
	public static class Form extends AbstractForm.AbstractDefault<ScheduleItem,Schedule> implements Serializable{
		
		private static final long serialVersionUID = -4741435164709063863L;
		/*
		@IncludeInputs(label=@Text(value="min")) private ScheduleItemExtremityFormModel low = new ScheduleItemExtremityFormModel();
		
		@IncludeInputs(label=@Text(value="max")) private ScheduleItemExtremityFormModel high = new ScheduleItemExtremityFormModel();
		
		@Override
		public void read() {
			super.read();
			low.set(identifiable.getLow());
			high.set(identifiable.getHigh());
		}
		
		@Override
		public void write() {
			super.write();
			low.write(identifiable.getLow());
			high.write(identifiable.getHigh());
		}
		*/
		public static final String FIELD_LOW = "low";
		public static final String FIELD_HIGH = "high";
	}

}
