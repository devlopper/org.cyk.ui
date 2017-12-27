package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.time.Period;
import org.cyk.ui.web.primefaces.resources.page.controlpanel.IdentifiableEditPage;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.event.Event;

public class FormMaster extends IdentifiableEditPage.FormMaster implements Serializable {
	private static final long serialVersionUID = -6211058744595898478L;
	
	@Override
	protected void __prepare__() {
		super.__prepare__();
		Form.Detail detail = getDetail();
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		detail.setFieldsObjectFromMaster();
		
		if(ClassHelper.getInstance().isInstanceOf(AbstractCollection.class, actionOnClass)){
			detail.add(AbstractCollection.FIELD_ITEM_CODE_SEPARATOR).addBreak();
			detail.add(AbstractCollection.FIELD_ITEM_AGGREGATION_APPLIED).addBreak();
			
			if(MovementCollection.class.equals(actionOnClass)){
				detail.add(MovementCollection.FIELD_INTERVAL).addBreak();
				detail.add(MovementCollection.FIELD_VALUE).addBreak();
				detail.add(MovementCollection.FIELD_INCREMENT_ACTION).addBreak();
				detail.add(MovementCollection.FIELD_DECREMENT_ACTION).addBreak();
				detail.add(MovementCollection.FIELD_SUPPORT_DOCUMENT_IDENTIFIER).addBreak();
				detail.add(MovementCollection.FIELD_DOCUMENT_IDENTIFIER_COUNT_INTERVAL).addBreak();
			}else if(IntervalCollection.class.equals(actionOnClass)){
				detail.add(IntervalCollection.FIELD_LOWEST_VALUE).addBreak();
				detail.add(IntervalCollection.FIELD_HIGHEST_VALUE).addBreak();
				detail.add(IntervalCollection.FIELD_NUMBER_OF_DECIMAL_AFTER_DOT).addBreak();
			}
		}else if(ClassHelper.getInstance().isInstanceOf(AbstractCollectionItem.class, actionOnClass)){
			detail.add(AbstractCollectionItem.FIELD_COLLECTION).addBreak();
			
			if(Movement.class.equals(actionOnClass)){
				detail.addReadOnly(Movement.FIELD_PREVIOUS_CUMUL).addBreak();
				detail.add(Movement.FIELD_ACTION).addBreak();
				detail.add(Movement.FIELD_VALUE).addBreak();
				detail.addReadOnly(Movement.FIELD_CUMUL).addBreak();
				detail.add(Movement.FIELD_SENDER_OR_RECEIVER_PERSON).addBreak();
				/* events */
				Event.instanciateOne(detail, AbstractCollectionItem.FIELD_COLLECTION, new String[]{Movement.FIELD_PREVIOUS_CUMUL,Movement.FIELD_CUMUL}, new Event.CommandAdapter(){
					private static final long serialVersionUID = 1L;
					protected void ____execute____() {
						inject(MovementBusiness.class).computeChanges((Movement) getEventPropertyFormMasterObject());
					}
				});
				
				Event.instanciateOne(detail, Movement.FIELD_ACTION, new String[]{Movement.FIELD_CUMUL}, new Event.CommandAdapter(){
					private static final long serialVersionUID = 1L;
					protected void ____execute____() {
						inject(MovementBusiness.class).computeChanges((Movement) getEventPropertyFormMasterObject());
					}
				});
				
				Event.instanciateOne(detail, Movement.FIELD_VALUE, new String[]{Movement.FIELD_CUMUL}, new Event.CommandAdapter(){
					private static final long serialVersionUID = 1L;
					protected void ____execute____() {
						inject(MovementBusiness.class).computeChanges((Movement) getEventPropertyFormMasterObject());
					}
				});
				
			}else if(Interval.class.equals(actionOnClass)){
				detail.setFieldsObjectFromMaster(Interval.FIELD_LOW);
				detail.addFieldName(IntervalExtremity.FIELD_VALUE,"lowest.value");
				detail.add(IntervalExtremity.FIELD_EXCLUDED).addBreak();
				detail.setFieldsObjectFromMaster(Interval.FIELD_HIGH);
				detail.addFieldName(IntervalExtremity.FIELD_VALUE,"highest.value");
				detail.add(IntervalExtremity.FIELD_EXCLUDED).addBreak();
				detail.setFieldsObjectFromMaster();
				detail.add(Interval.FIELD_VALUE).addBreak();
				
			}
		}
		detail.setFieldsObjectFromMaster(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD);
		detail.add(FieldHelper.getInstance().buildPath(Period.FIELD_FROM_DATE)).addBreak();
	}
	
}
