package org.cyk.ui.web.primefaces.page.time;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.ui.api.model.time.InstantFormModel;
import org.cyk.ui.api.model.time.InstantIntervalFormModel;
import org.cyk.ui.web.primefaces.page.AbstractIdentifiablePagesConfiguration;
import org.cyk.utility.common.Constant.Action;

public class ScheduleItemPagesConfiguration extends AbstractIdentifiablePagesConfiguration<ScheduleItem> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getRequiredFieldNames(Action action) {
		return ArrayUtils.addAll(super.getRequiredFieldNames(action), new String[]{ScheduleItemEditPage.Form.FIELD_COLLECTION});
	}
	
	@Override
	protected String[] getFieldNames(Action action) {
		return ArrayUtils.addAll(super.getFieldNames(action), new String[]{ScheduleItemEditPage.Form.FIELD_INSTANT_INTERVAL,InstantIntervalFormModel.FIELD_FROM
			,InstantIntervalFormModel.FIELD_TO,InstantFormModel.FIELD_YEAR,InstantFormModel.FIELD_MONTH_OF_YEAR,InstantFormModel.FIELD_DAY_OF_MONTH
			,InstantFormModel.FIELD_DAY_OF_WEEK,InstantFormModel.FIELD_HOUR_OF_DAY,InstantFormModel.FIELD_MINUTE_OF_HOUR,InstantFormModel.FIELD_SECOND_OF_MINUTE
			,InstantFormModel.FIELD_MILLISECOND_OF_SECOND,InstantIntervalFormModel.FIELD_PORTION_IN_MILLISECOND});
	}
	
}
