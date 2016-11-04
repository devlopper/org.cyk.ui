package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValueInputted;
import org.cyk.system.root.model.mathematics.MetricValueType;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneRadio;

@Getter @Setter @Named @ViewScoped
public class MetricCollectionEditPage extends AbstractCollectionEditPage<MetricCollection,Metric> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter
	public static class Form extends AbstractForm<MetricCollection,Metric> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private IntervalCollection valueIntervalCollection;
		@Input @InputChoice @InputOneRadio @NotNull private MetricValueType valueType = MetricValueType.NUMBER;
		@Input @InputChoice @InputOneRadio @NotNull private MetricValueInputted valueInputted = MetricValueInputted.VALUE_INTERVAL_VALUE;
		
		@Override
		protected AbstractCollection<?> getCollection() {
			return identifiable;
		}
		
		public static final String FIELD_VALUE_INTERVAL_COLLECTION = "valueIntervalCollection";
		public static final String FIELD_VALUE_TYPE = "valueType";
		public static final String FIELD_VALUE_INPUTTED = "valueInputted";
	}

	@Override
	protected AbstractCollection<?> getCollection() {
		return identifiable;
	}

}
