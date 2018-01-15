package org.cyk.ui.web.primefaces;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;

public class DataTable {

	public static class Listener extends org.cyk.ui.web.primefaces.resources.component.DataTable.Listener {
		private static final long serialVersionUID = 1L;
		
		@Override
		public List<String> getColumnsFieldNamesOrder(org.cyk.utility.common.userinterface.collection.DataTable dataTable) {
			if(PhoneNumber.class.equals(dataTable.getPropertiesMap().getActionOnClass()))
				return Arrays.asList(PhoneNumber.FIELD_NUMBER,PhoneNumber.FIELD_TYPE,PhoneNumber.FIELD_LOCATION_TYPE,PhoneNumber.FIELD_COUNTRY,PhoneNumber.FIELD_COLLECTION);
			return super.getColumnsFieldNamesOrder(dataTable);
		}
		
		@Override
		public void processColumnsFieldNames(org.cyk.utility.common.userinterface.collection.DataTable dataTable,Collection<String> fieldNames) {
			super.processColumnsFieldNames(dataTable, fieldNames);
			Class<?> actionOnClass = (Class<?>) dataTable.getPropertiesMap().getActionOnClass();
			if(ClassHelper.getInstance().isInstanceOf(AbstractCollection.class, actionOnClass)){
				if(IntervalCollection.class.equals(actionOnClass)){
					fieldNames.add(IntervalCollection.FIELD_LOWEST_VALUE);
					fieldNames.add(IntervalCollection.FIELD_HIGHEST_VALUE);
					fieldNames.add(IntervalCollection.FIELD_NUMBER_OF_DECIMAL_AFTER_DOT);
				}else if(MovementCollection.class.equals(actionOnClass)){
					fieldNames.add(MovementCollection.FIELD_VALUE);		
				}
			}else if(ClassHelper.getInstance().isInstanceOf(AbstractCollectionItem.class, actionOnClass)){
				if(!(dataTable.getPropertiesMap().getMaster() instanceof AbstractCollection))
					fieldNames.add(AbstractCollectionItem.FIELD_COLLECTION);
				if(Interval.class.equals(actionOnClass)){
					fieldNames.add(Interval.FIELD_LOW);
					fieldNames.add(Interval.FIELD_HIGH);
					fieldNames.add(Interval.FIELD_VALUE);
				}else if(Movement.class.equals(actionOnClass)){
					fieldNames.add(Movement.FIELD_ACTION);	
					fieldNames.add(Movement.FIELD_VALUE);
					fieldNames.add(Movement.FIELD_CUMUL);
					fieldNames.add(FieldHelper.getInstance().buildPath(Movement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE));
				}else if(ClassHelper.getInstance().isInstanceOf(Contact.class, actionOnClass)){
					if(PhoneNumber.class.equals(actionOnClass)){
						fieldNames.add(PhoneNumber.FIELD_COUNTRY);
						fieldNames.add(PhoneNumber.FIELD_LOCATION_TYPE);
						fieldNames.add(PhoneNumber.FIELD_NUMBER);
					}
				}
			}
		}
	}
	
}
