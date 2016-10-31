package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.ui.web.primefaces.page.AbstractCollectionItemEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;

@Named @ViewScoped @Getter @Setter
public class MetricEditPage extends AbstractCollectionItemEditPage.AbstractDefault<Metric,MetricCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
		
	@Getter @Setter @FieldOverride(name=AbstractForm.FIELD_COLLECTION,type=MetricCollection.class)
	public static class Form extends AbstractForm.AbstractDefault<Metric,MetricCollection> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private IntervalCollection valueIntervalCollection;
		
		public static final String FIELD_VALUE_INTERVAL_COLLECTION = "valueIntervalCollection";
		
	}

}
