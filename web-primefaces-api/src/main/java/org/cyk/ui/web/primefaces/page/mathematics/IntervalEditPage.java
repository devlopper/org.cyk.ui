package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.ui.api.model.mathematics.IntervalExtremityFormModel;
import org.cyk.ui.web.primefaces.page.AbstractCollectionItemEditPage;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Named @ViewScoped @Getter @Setter
public class IntervalEditPage extends AbstractCollectionItemEditPage<Interval,IntervalCollection,Interval> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected AbstractCollectionItem<?> getItem() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends AbstractForm.AbstractDefault<Interval,IntervalCollection> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		@IncludeInputs(label=@Text(value="min")) private IntervalExtremityFormModel low = new IntervalExtremityFormModel();
		
		@IncludeInputs(label=@Text(value="max")) private IntervalExtremityFormModel high = new IntervalExtremityFormModel();
		
		public static final String FIELD_LOW = "low";
		public static final String FIELD_HIGH = "high";
	}

}
