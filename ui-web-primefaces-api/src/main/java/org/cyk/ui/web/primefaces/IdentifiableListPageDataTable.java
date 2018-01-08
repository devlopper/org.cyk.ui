package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.ui.web.primefaces.resources.page.controlpanel.IdentifiableListPage;

public class IdentifiableListPageDataTable extends IdentifiableListPage.DataTable implements Serializable {
	private static final long serialVersionUID = -6211058744595898478L;
	
	/*
	@Override
	protected void __prepare__() {
		super.__prepare__();
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		//columns
		if(ClassHelper.getInstance().isInstanceOf(AbstractCollection.class, actionOnClass)){
			if(IntervalCollection.class.equals(actionOnClass)){
				addColumnsByFieldNames(IntervalCollection.FIELD_LOWEST_VALUE);
				addColumnsByFieldNames(IntervalCollection.FIELD_HIGHEST_VALUE);
				addColumnsByFieldNames(IntervalCollection.FIELD_NUMBER_OF_DECIMAL_AFTER_DOT);
			}else if(MovementCollection.class.equals(actionOnClass)){
				addColumnsByFieldNames(MovementCollection.FIELD_VALUE);		
			}
		}else if(ClassHelper.getInstance().isInstanceOf(AbstractCollectionItem.class, actionOnClass)){
			addColumnsByFieldNames(AbstractCollectionItem.FIELD_COLLECTION);
			if(Interval.class.equals(actionOnClass)){
				addColumnsByFieldNames(Interval.FIELD_LOW);
				addColumnsByFieldNames(Interval.FIELD_HIGH);
				addColumnsByFieldNames(Interval.FIELD_VALUE);
			}else if(Movement.class.equals(actionOnClass)){
				addColumnsByFieldNames(Movement.FIELD_ACTION);	
				addColumnsByFieldNames(Movement.FIELD_VALUE);
				addColumnsByFieldNames(Movement.FIELD_CUMUL);
				addColumnsByFieldNames(FieldHelper.getInstance().buildPath(Movement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE));
			}
		}
	}
	*/
}
