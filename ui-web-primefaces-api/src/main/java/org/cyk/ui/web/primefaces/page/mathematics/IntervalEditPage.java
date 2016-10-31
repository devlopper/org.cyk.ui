package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.ui.api.model.mathematics.IntervalExtremityFormModel;
import org.cyk.ui.web.primefaces.page.AbstractCollectionItemEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Named @ViewScoped @Getter @Setter
public class IntervalEditPage extends AbstractCollectionItemEditPage.AbstractDefault<Interval,IntervalCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter @FieldOverride(name=AbstractForm.FIELD_COLLECTION,type=IntervalCollection.class)
	public static class Form extends AbstractForm.AbstractDefault<Interval,IntervalCollection> implements Serializable{
		
		private static final long serialVersionUID = -4741435164709063863L;

		@IncludeInputs(label=@Text(value="min")) private IntervalExtremityFormModel low = new IntervalExtremityFormModel();
		
		@IncludeInputs(label=@Text(value="max")) private IntervalExtremityFormModel high = new IntervalExtremityFormModel();
		
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
		
		public static final String FIELD_LOW = "low";
		public static final String FIELD_HIGH = "high";
	}

}
