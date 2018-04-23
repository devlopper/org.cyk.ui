package org.cyk.ui.web.primefaces.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.utility.common.userinterface.container.Form;

public class MathematicsIdentifiableEditPageFormMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void prepareIntervalCollection(final Form.Detail detail,Class<?> aClass){
		detail.add(IntervalCollection.FIELD_LOWEST_VALUE).addBreak();
		detail.add(IntervalCollection.FIELD_HIGHEST_VALUE).addBreak();
		detail.add(IntervalCollection.FIELD_NUMBER_OF_DECIMAL_AFTER_DOT).addBreak();
	}
	
	public static void prepareInterval(final Form.Detail detail,Class<?> aClass){
		detail.setFieldsObjectFromMaster(Interval.FIELD_LOW);
		detail.addFieldName(IntervalExtremity.FIELD_VALUE,"lowest.value");
		detail.add(IntervalExtremity.FIELD_EXCLUDED).addBreak();
		detail.setFieldsObjectFromMaster(Interval.FIELD_HIGH);
		detail.addFieldName(IntervalExtremity.FIELD_VALUE,"highest.value");
		detail.add(IntervalExtremity.FIELD_EXCLUDED).addBreak();
		detail.setFieldsObjectFromMaster();
		detail.add(Interval.FIELD_VALUE).addBreak();
	}
	
	/**/
	
}