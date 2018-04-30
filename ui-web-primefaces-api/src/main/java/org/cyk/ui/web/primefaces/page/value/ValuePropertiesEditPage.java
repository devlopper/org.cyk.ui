package org.cyk.ui.web.primefaces.page.value;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.language.programming.Script;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.model.value.NullString;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.system.root.model.value.ValueSet;
import org.cyk.system.root.model.value.ValueType;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ValuePropertiesEditPage extends AbstractCrudOnePage<ValueProperties> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
		
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<ValueProperties> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private Measure measure;
		@Input @InputChoice @InputOneChoice @InputOneCombo private IntervalCollection intervalCollection;
		@Input @InputChoice @InputOneChoice @InputOneCombo private ValueType type;
		@Input @InputChoice @InputOneChoice @InputOneCombo private ValueSet set;
		
		@Input @InputBooleanButton private Boolean derived;
		@Input @InputChoice @InputOneChoice @InputOneCombo private Script derivationScript;
		
		@Input @InputBooleanButton private Boolean nullable;
		@Input @InputChoice @InputOneChoice @InputOneCombo private NullString nullString;
		
		/**/
		
		public static final String FIELD_INTERVAL_COLLECTION = "intervalCollection";
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_SET = "set";
		public static final String FIELD_NULLABLE = "nullable";
		public static final String FIELD_NULL_STRING = "nullString";
		public static final String FIELD_MEASURE = "measure";
		public static final String FIELD_DERIVED = "derived";
		public static final String FIELD_DERIVATION_SCRIPT = "derivationScript";
	}
	

}
