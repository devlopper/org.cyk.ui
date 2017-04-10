package org.cyk.ui.api.model.geography;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeTypeForm;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;

@Getter @Setter 
@FieldOverrides(value = {
		@FieldOverride(name=AbstractDataTreeForm.FIELD_NEW_PARENT,type=LocalityType.class)
		})
public class LocalityTypeForm extends AbstractDataTreeTypeForm<LocalityType> {

	private static final long serialVersionUID = -3927257570208213271L;

}
