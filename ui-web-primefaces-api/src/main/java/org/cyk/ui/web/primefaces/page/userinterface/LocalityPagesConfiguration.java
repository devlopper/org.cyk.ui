package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.ui.api.model.geography.GlobalPositionFormModel;
import org.cyk.ui.web.primefaces.page.geography.LocalityEditPage;
import org.cyk.ui.web.primefaces.page.pattern.tree.AbstractDataTreePagesConfiguration;
import org.cyk.utility.common.Constant.Action;

public class LocalityPagesConfiguration extends AbstractDataTreePagesConfiguration<Locality> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String[] getFieldNames(Action action) {
		return ArrayUtils.addAll(super.getFieldNames(action),LocalityEditPage.Form.FIELD_GLOBAL_POSITION,GlobalPositionFormModel.FIELD_LONGITUDE
				,GlobalPositionFormModel.FIELD_LATITUDE,GlobalPositionFormModel.FIELD_ALTITUDE);
	}
}
