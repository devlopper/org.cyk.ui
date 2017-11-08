package org.cyk.ui.web.api.resources.converter;
import java.io.Serializable;

import org.cyk.utility.common.helper.InstanceHelper;

public class ObjectLabelConverter extends AbstractBasedMapConverter implements Serializable {
	private static final long serialVersionUID = -1615078449226502960L;

	@Override
	protected String getString(Object object) {
		return InstanceHelper.getInstance().getLabel(object);
	}
	
}