package org.cyk.ui.web.api.resources;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.utility.common.helper.SelectItemHelper.Builder.One;

public class SelectItemHelper {

	public static class OneBuilder extends org.cyk.ui.api.resources.SelectItemHelper.OneBuilder<SelectItem> implements Serializable{
		private static final long serialVersionUID = 1L;

		public OneBuilder() {
			super(SelectItem.class);
		}
		
		@Override
		public One<SelectItem> setFieldValue(SelectItem item, String fieldName, Object value) {
			super.setFieldValue(item, fieldName, value);
			if(FIELD_NAME_VALUE.equals(fieldName))
				item.setValue(value);
			else if(FIELD_NAME_LABEL.equals(fieldName))
				item.setLabel(value.toString());
			//else if(FIELD_NAME_DESCRIPTION.equals(fieldName))
			//	item.setLabel(value.toString());
			return this;
		}
		
	}
	
}