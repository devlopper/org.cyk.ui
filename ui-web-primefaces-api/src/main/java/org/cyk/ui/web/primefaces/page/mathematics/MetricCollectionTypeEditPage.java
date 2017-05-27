package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MetricCollectionTypeEditPage extends AbstractCrudOnePage<MetricCollectionType> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
		
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<MetricCollectionType> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		@Input @InputChoice @InputOneChoice @InputOneCombo  private ValueProperties collectionValueProperties;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo  private ValueProperties metricValueProperties;
		
		/**/
		
		public static final String FIELD_COLLECTION_VALUE_PROPERTIES = "collectionValueProperties";
		public static final String FIELD_METRIC_VALUE_PROPERTIES = "metricValueProperties";
		
	}

}
