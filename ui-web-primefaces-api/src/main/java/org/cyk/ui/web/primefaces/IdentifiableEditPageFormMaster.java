package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.GlobalPosition;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.time.Period;
import org.cyk.ui.web.primefaces.resources.page.controlpanel.IdentifiableEditPage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.event.Event;

public class IdentifiableEditPageFormMaster extends IdentifiableEditPage.FormMaster implements Serializable {
	private static final long serialVersionUID = -6211058744595898478L;
	
	public Master setFromRequestParameter(Class<?> aClass,String fieldName){
		if(MovementCollection.class.equals(getPropertiesMap().getActionOnClass())){
			
		}else if(Movement.class.equals(getPropertiesMap().getActionOnClass())){
			if(MovementCollection.class.equals(aClass))
				fieldName = Movement.FIELD_COLLECTION;
			else if(MovementAction.class.equals(aClass))
				fieldName = Movement.FIELD_ACTION;
		}
		//if(fieldName.equals("movementAction"))
		//	fieldName = "incrementAction";
		//if(fieldName.equals("movementCollection"))
		//	fieldName = "collection";
		//if(fieldName.equals("incrementAction") || fieldName.equals("person"))
		//	return this;
		
		return super.setFromRequestParameter(aClass, fieldName);
	}
	
	@Override
	protected void ____addType____() {
		//Form.Detail detail = getDetail();
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		if(ArrayUtils.contains(new Class<?>[]{PhoneNumber.class,Location.class}, actionOnClass)){
			return;
		}
		super.____addType____();
		
	}
	
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
				Movement movement = (Movement)getObject();
				movement.setValueSettableFromAbsolute(Boolean.TRUE);
				if(movement.getValue()!=null)
					movement.setValueAbsolute(movement.getValue().abs());
				
				detail.addReadOnly(Movement.FIELD_PREVIOUS_CUMUL).addBreak();
				detail.add(Movement.FIELD_ACTION).addBreak();
				detail.add(Movement.FIELD_VALUE_ABSOLUTE).addBreak();
				detail.addReadOnly(Movement.FIELD_CUMUL).addBreak();
				detail.add(Movement.FIELD_SENDER_OR_RECEIVER_PERSON).addBreak();
				
				addExistencePeriodFromDate();
				
				//detail.getInputByFieldName(Movement.FIELD_ACTION).getPropertiesMap().setDisabled( ((Movement)getObject()).getAction() != null );
				
				if(Constant.Action.isCreateOrUpdate(_getPropertyAction())){
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
					
					Event.instanciateOne(detail, Movement.FIELD_VALUE_ABSOLUTE, new String[]{Movement.FIELD_CUMUL}, new Event.CommandAdapter(){
						private static final long serialVersionUID = 1L;
						protected void ____execute____() {
							inject(MovementBusiness.class).computeChanges((Movement) getEventPropertyFormMasterObject());
						}
					});	
				}
				
				
			}else if(Interval.class.equals(actionOnClass)){
				detail.setFieldsObjectFromMaster(Interval.FIELD_LOW);
				detail.addFieldName(IntervalExtremity.FIELD_VALUE,"lowest.value");
				detail.add(IntervalExtremity.FIELD_EXCLUDED).addBreak();
				detail.setFieldsObjectFromMaster(Interval.FIELD_HIGH);
				detail.addFieldName(IntervalExtremity.FIELD_VALUE,"highest.value");
				detail.add(IntervalExtremity.FIELD_EXCLUDED).addBreak();
				detail.setFieldsObjectFromMaster();
				detail.add(Interval.FIELD_VALUE).addBreak();
				
			}else if(ClassHelper.getInstance().isInstanceOf(Contact.class, actionOnClass)){
				if(PhoneNumber.class.equals(actionOnClass)){
					detail.add(PhoneNumber.FIELD_COUNTRY).addBreak();
					detail.add(PhoneNumber.FIELD_TYPE).addBreak();
					detail.add(PhoneNumber.FIELD_NUMBER).addBreak();
					detail.add(PhoneNumber.FIELD_LOCATION_TYPE).addBreak();
				}else if(Location.class.equals(actionOnClass)){
					detail.add(Location.FIELD_LOCALITY).addBreak();
					detail.setFieldsObjectFromMaster(Location.FIELD_GLOBAL_IDENTIFIER);
					detail.add(GlobalIdentifier.FIELD_OTHER_DETAILS).addBreak();
					detail.setFieldsObjectFromMaster();
					detail.add(Location.FIELD_TYPE).addBreak();
				}else if(ElectronicMailAddress.class.equals(actionOnClass)){
					detail.add(ElectronicMailAddress.FIELD_ADDRESS).addBreak();
				}else if(PostalBox.class.equals(actionOnClass)){
					detail.add(PostalBox.FIELD_ADDRESS).addBreak();
				}else if(Website.class.equals(actionOnClass)){
					detail.add(Website.FIELD_UNIFORM_RESOURCE_LOCATOR).addBreak();
				}
			}
		}else if(Locality.class.equals(actionOnClass)){
			detail.add(Locality.FIELD_RESIDENT_NAME).addBreak();
			detail.setFieldsObjectFromMaster(Locality.FIELD_GLOBAL_POSITION);
			detail.add(GlobalPosition.FIELD_LATITUDE).addBreak();
			detail.add(GlobalPosition.FIELD_LONGITUDE).addBreak();
			detail.add(GlobalPosition.FIELD_ALTITUDE).addBreak();
			detail.setFieldsObjectFromMaster(Locality.FIELD_GLOBAL_IDENTIFIER);
			detail.add(GlobalIdentifier.FIELD_IMAGE).addBreak();
		}else if(Country.class.equals(actionOnClass)){
			if(!Constant.Action.CREATE.equals(getPropertiesMap().getAction()))
				inject(CountryBusiness.class).setContinent((Country) getObject());
			detail.add(Country.FIELD_LOCALITY).addBreak();
			detail.add(Country.FIELD_CONTINENT).addBreak();
			detail.add(Country.FIELD_PHONE_NUMBER_CODE).addBreak();
			detail.add(Country.FIELD_PHONE_NUMBER_FORMAT).addBreak();
		}
		
	}
	
	private void addExistencePeriodFromDate(){
		getDetail().setFieldsObjectFromMaster(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD);
		getDetail().add(FieldHelper.getInstance().buildPath(Period.FIELD_FROM_DATE)).addBreak();
	}
	
}
