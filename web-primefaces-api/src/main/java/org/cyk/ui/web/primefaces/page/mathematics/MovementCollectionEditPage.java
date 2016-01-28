package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Named @ViewScoped @Getter @Setter
public class MovementCollectionEditPage extends AbstractCrudOnePage<MovementCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
		
	@Getter @Setter
	public static class Form extends AbstractFormModel<Movement> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private MovementCollection collection;
		@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo @NotNull private MovementAction action;
		@Input @InputNumber @NotNull private BigDecimal value;
		
		@Override
		public void write() {
			super.write();
			identifiable.setCollection(collection);
		}
		
		/**/
		
		public static final String FIELD_COLLECTION = "collection";
		public static final String FIELD_ACTION = "action";
		public static final String FIELD_VALUE = "value";
	}

}
