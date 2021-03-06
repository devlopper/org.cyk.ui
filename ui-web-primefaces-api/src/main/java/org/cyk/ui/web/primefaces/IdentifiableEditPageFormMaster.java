package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Rud;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.GlobalPosition;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementAction;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.Store;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;
import org.cyk.system.root.model.value.LongValue;
import org.cyk.ui.web.primefaces.file.FileIdentifiableEditPageFormMaster;
import org.cyk.ui.web.primefaces.mathematics.MathematicsIdentifiablePages;
import org.cyk.ui.web.primefaces.mathematics.movement.MovementIdentifiablePages;
import org.cyk.ui.web.primefaces.store.StoreIdentifiableEditPageFormMaster;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.RequestHelper;
import org.cyk.utility.common.userinterface.container.form.Form;
import org.cyk.utility.common.userinterface.container.form.FormDetail;

public class IdentifiableEditPageFormMaster extends org.cyk.ui.web.primefaces.resources.page.controlpanel.IdentifiableEditPage.FormMaster implements Serializable {
	private static final long serialVersionUID = -6211058744595898478L;
	
	@Override
	protected void __setFromRequestParameter__() {
		super.__setFromRequestParameter__();
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		if(ClassHelper.getInstance().isInstanceOf(AbstractJoinGlobalIdentifier.class, actionOnClass)){
			Class<?> globalIdentifierIdentifiableClass = RequestHelper.getInstance().getParameterAsClass(UniformResourceLocatorHelper.QueryParameter.Name
					.CLASS_IDENTIFIABLE_GLOBAL_IDENTIFIER);
			AbstractIdentifiable identifiable = (AbstractIdentifiable) RequestHelper.getInstance().getParameterAsInstance(globalIdentifierIdentifiableClass);
			if(((AbstractJoinGlobalIdentifier)getObject()).getIdentifiableGlobalIdentifier() == null){
				((AbstractJoinGlobalIdentifier)getObject()).setIdentifiableGlobalIdentifier(identifiable.getGlobalIdentifier());
			}			
		}
	}
	
	public Form setFromRequestParameter(Class<?> aClass,String fieldName){
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		if(MovementCollection.class.equals(actionOnClass)){
			
		}else if(Movement.class.equals(actionOnClass)){
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
	protected void ____add____() {
		if(Person.class.equals(getPropertiesMap().getActionOnClass())){
			
		}else
			super.____add____();
	}
	
	@Override
	protected void ____addCode____() {
		if(ArrayUtils.contains(new Class<?>[]{GlobalIdentifier.class,MovementCollectionInventory.class,MovementGroup.class,MovementCollectionValuesTransfer.class
			,MovementCollectionValuesTransferAcknowledgement.class}, getPropertiesMap().getActionOnClass())){
			
		}else if(ClassHelper.getInstance().isInstanceOf(AbstractJoinGlobalIdentifier.class, (Class<?>)getPropertiesMap().getActionOnClass())){
			
		}else
			super.____addCode____();
	}
	
	@Override
	protected void ____addName____() {
		if(ClassHelper.getInstance().isInstanceOf(AbstractJoinGlobalIdentifier.class, (Class<?>)getPropertiesMap().getActionOnClass())){
			
		}else if(ArrayUtils.contains(new Class<?>[]{MovementCollectionInventory.class,MovementGroup.class,MovementCollectionValuesTransfer.class
			,MovementCollectionValuesTransferAcknowledgement.class}, getPropertiesMap().getActionOnClass())){
			
		}else
			super.____addName____();
	}
	
	@Override
	protected void ____addType____() {
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		if(ArrayUtils.contains(new Class<?>[]{PhoneNumber.class,Location.class,MovementCollectionInventory.class}, actionOnClass)){
			return;
		}
		super.____addType____();
		
	}
	
	@Override
	protected void __prepare__() {
		super.__prepare__();
		final FormDetail detail = getDetail();
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		detail.setFieldsObjectFromMaster();
		
		if(ClassHelper.getInstance().isInstanceOf(AbstractCollection.class, actionOnClass)){
			//detail.add(AbstractCollection.FIELD_ITEM_CODE_SEPARATOR).addBreak();
			//detail.add(AbstractCollection.FIELD_ITEM_AGGREGATION_APPLIED).addBreak();
			
			if(MovementCollection.class.equals(actionOnClass)){
				MovementIdentifiablePages.prepareMovementCollectionEditFormMaster(detail, actionOnClass);
			}else if(MovementCollectionValuesTransferItemCollection.class.equals(actionOnClass)){
				MovementIdentifiablePages.prepareMovementCollectionValuesTransferItemCollectionEditFormMaster(detail, null);
			}else if(MovementCollectionInventory.class.equals(actionOnClass)){
				MovementIdentifiablePages.prepareMovementCollectionInventoryEditFormMaster(detail);
			}else if(MovementGroup.class.equals(actionOnClass)){
				MovementIdentifiablePages.prepareMovementGroupEditFormMaster(detail);
			}else if(IntervalCollection.class.equals(actionOnClass)){
				MathematicsIdentifiablePages.prepareIntervalCollectionEditFormMaster(detail, actionOnClass);
			}
		}else if(ClassHelper.getInstance().isInstanceOf(AbstractCollectionItem.class, actionOnClass)){
			detail.add(AbstractCollectionItem.FIELD_COLLECTION).addBreak();
			
			if(Movement.class.equals(actionOnClass)){
				MovementIdentifiablePages.prepareMovementEditFormMaster(detail, actionOnClass);
			}else if(MovementCollectionValuesTransferItemCollectionItem.class.equals(actionOnClass)){
				
			}else if(Interval.class.equals(actionOnClass)){ 
				MathematicsIdentifiablePages.prepareIntervalEditFormMaster(detail, actionOnClass);
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
			}else if(IdentifiablePeriod.class.equals(actionOnClass)){
				addExistencePeriodFromDate();
				addExistencePeriodToDate();
				addClosed();
			}
		}else if(ClassHelper.getInstance().isHierarchy(actionOnClass)){
			if(Locality.class.equals(actionOnClass)){
				detail.add(Locality.FIELD_RESIDENT_NAME).addBreak();
				detail.setFieldsObjectFromMaster(Locality.FIELD_GLOBAL_POSITION);
				detail.add(GlobalPosition.FIELD_LATITUDE).addBreak();
				detail.add(GlobalPosition.FIELD_LONGITUDE).addBreak();
				detail.add(GlobalPosition.FIELD_ALTITUDE).addBreak();
				detail.setFieldsObjectFromMaster(Locality.FIELD_GLOBAL_IDENTIFIER);
				detail.add(GlobalIdentifier.FIELD_IMAGE).addBreak();
			}else if(Store.class.equals(actionOnClass)){
				StoreIdentifiableEditPageFormMaster.prepareStore(detail);
			}
		}else if(MovementCollectionType.class.equals(actionOnClass)){
			detail.add(MovementCollectionType.FIELD_INTERVAL).addBreak();
			detail.add(MovementCollectionType.FIELD_INCREMENT_ACTION).addBreak();
			detail.add(MovementCollectionType.FIELD_DECREMENT_ACTION).addBreak();
			detail.add(MovementCollectionType.FIELD_IDENTIFIABLE_PERIOD_COLLECTION_TYPE).addBreak(); 
			detail.add(MovementCollectionType.FIELD_SUPPORT_DOCUMENT_IDENTIFIER).addBreak();
			detail.add(MovementCollectionType.FIELD_DOCUMENT_IDENTIFIER_COUNT_INTERVAL).addBreak();
		}else if(MovementCollectionValuesTransfer.class.equals(actionOnClass)){
			MovementIdentifiablePages.prepareMovementCollectionValuesTransferEditFormMaster(detail, actionOnClass);
		}else if(MovementCollectionValuesTransferAcknowledgement.class.equals(actionOnClass)){
			MovementIdentifiablePages.prepareMovementCollectionValuesTransferAcknowledgementEditFormMaster(detail, actionOnClass);
		}else if(Country.class.equals(actionOnClass)){
			if(!Constant.Action.CREATE.equals(getPropertiesMap().getAction()))
				inject(CountryBusiness.class).setContinent((Country) getObject());
			detail.add(Country.FIELD_LOCALITY).addBreak();
			detail.add(Country.FIELD_CONTINENT).addBreak();
			detail.add(Country.FIELD_PHONE_NUMBER_CODE).addBreak();
			detail.add(Country.FIELD_PHONE_NUMBER_FORMAT).addBreak();
		}else if(ClassHelper.getInstance().isInstanceOf(AbstractJoinGlobalIdentifier.class, actionOnClass)){
			if(PartyIdentifiableGlobalIdentifier.class.equals(actionOnClass)){
				detail.add(PartyIdentifiableGlobalIdentifier.FIELD_PARTY).addBreak();
				detail.add(PartyIdentifiableGlobalIdentifier.FIELD_BUSINESS_ROLE).addBreak();
				detail.add(PartyIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER).addBreak();	
			}else if(IdentifiablePeriodCollectionIdentifiableGlobalIdentifier.class.equals(actionOnClass)){
				detail.add(IdentifiablePeriodCollectionIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_PERIOD_COLLECTION).addBreak();
				detail.add(IdentifiablePeriodCollectionIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER).addBreak();	
			}else if(MovementCollectionIdentifiableGlobalIdentifier.class.equals(actionOnClass)){
				detail.add(MovementCollectionIdentifiableGlobalIdentifier.FIELD_MOVEMENT_COLLECTION).addBreak();
				detail.add(MovementCollectionIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER).addBreak();	
			}
		}else if(GlobalIdentifier.class.equals(actionOnClass)){
			detail.add(GlobalIdentifier.FIELD_IMAGE).addBreak();
			detail.add(GlobalIdentifier.FIELD_ABBREVIATION).addBreak();
			detail.add(GlobalIdentifier.FIELD_ACTIVATED).addBreak();
			detail.add(GlobalIdentifier.FIELD_CLOSED).addBreak();
			detail.add(GlobalIdentifier.FIELD_CONSTANT).addBreak();
			detail.add(GlobalIdentifier.FIELD_CREATION_DATE).addBreak();
			detail.add(GlobalIdentifier.FIELD_DEFAULTED).addBreak();
			detail.add(GlobalIdentifier.FIELD_DERIVED).addBreak();
			detail.add(GlobalIdentifier.FIELD_DESCRIPTION).addBreak();
			detail.add(GlobalIdentifier.FIELD_EXTERNAL_IDENTIFIER).addBreak();
			detail.add(GlobalIdentifier.FIELD_INITIALIZED).addBreak();
			detail.add(GlobalIdentifier.FIELD_MALE).addBreak();
			detail.add(GlobalIdentifier.FIELD_ORDER_NUMBER).addBreak();
			detail.add(GlobalIdentifier.FIELD_OTHER_DETAILS).addBreak();
			detail.add(GlobalIdentifier.FIELD_REQUIRED).addBreak();
			detail.add(GlobalIdentifier.FIELD_USABLE).addBreak();
			detail.add(GlobalIdentifier.FIELD_WEIGHT).addBreak();
			
			detail.setFieldsObjectFromMaster(GlobalIdentifier.FIELD_EXISTENCE_PERIOD);
			detail.add(Period.FIELD_FROM_DATE).addBreak();
			detail.add(Period.FIELD_TO_DATE).addBreak();
			detail.setFieldsObjectFromMaster(GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_NUMBER_OF_MILLISECOND);
			detail.add(LongValue.FIELD_PREFERRED_PROPERTY).addBreak();
			detail.add(LongValue.FIELD_USER).addBreak();
			detail.add(LongValue.FIELD_SYSTEM).addBreak();
			detail.add(LongValue.FIELD_GAP).addBreak();
			
			detail.setFieldsObjectFromMaster(GlobalIdentifier.FIELD_CASCADE_STYLE_SHEET);
			detail.add(CascadeStyleSheet.FIELD_INLINE).addBreak();
			detail.add(CascadeStyleSheet.FIELD_CLASS).addBreak();
			detail.add(CascadeStyleSheet.FIELD_UNIQUE_CLASS).addBreak();
			
			detail.setFieldsObjectFromMaster(GlobalIdentifier.FIELD_RUD);
			detail.add(Rud.FIELD_READABLE).addBreak();
			detail.add(Rud.FIELD_UPDATABLE).addBreak();
			detail.add(Rud.FIELD_DELETABLE).addBreak();
			
			/*detail.add(GlobalIdentifier.FIELD_).addBreak();
			detail.add(GlobalIdentifier.FIELD_).addBreak();
			*/
		}else if(Person.class.equals(actionOnClass)){
			preparePerson(detail);
		}else if(ClassHelper.getInstance().isInstanceOf(AbstractActor.class, actionOnClass)){
			prepareActor(detail);
		}else if(File.class.equals(actionOnClass)){
			FileIdentifiableEditPageFormMaster.prepareFile(detail, actionOnClass);
		}else if(Script.class.equals(actionOnClass)){
			FileIdentifiableEditPageFormMaster.prepareScript(detail, actionOnClass);
		}
		
	}
	
	protected void addExistencePeriodFromDate(){
		addExistencePeriodFromDate(getDetail());
	}
	
	protected void addExistencePeriodToDate(){
		addExistencePeriodToDate(getDetail());
	}
	
	protected void addOwner(){
		addOwner(getDetail());
	}
	
	protected void addClosed(){
		addClosed(getDetail());
	}
	
	protected void addImage(){
		addImage(getDetail());
	}
	
	protected void addDescription(){
		addDescription(getDetail());
	}
	
	/**/
	
	private static void __preparePerson__(FormDetail detail,String fieldName){
		detail.setFieldsObjectFromMaster(fieldName,Person.FIELD_GLOBAL_IDENTIFIER);
		detail.add(GlobalIdentifier.FIELD_CODE);
		detail.add(GlobalIdentifier.FIELD_IMAGE,1,3).addBreak();
		detail.add(GlobalIdentifier.FIELD_NAME).addBreak();
		detail.setFieldsObjectFromMaster(fieldName);
		detail.add(Person.FIELD_LASTNAMES).addBreak();
		detail.add(Person.FIELD_NATIONALITY);
		detail.add(Person.FIELD_SEX).addBreak();
	}
	
	public static void preparePerson(FormDetail detail){
		__preparePerson__(detail, null);
	}
	
	public static void prepareActor(FormDetail detail){
		__preparePerson__(detail, AbstractActor.FIELD_PERSON);
	}

	/**/
	
	public static void addExistencePeriodFromDate(FormDetail detail){
		detail.setFieldsObjectFromMaster(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD);
		detail.add(FieldHelper.getInstance().buildPath(Period.FIELD_FROM_DATE)).addBreak();
	}
	
	public static void addExistencePeriodToDate(FormDetail detail){
		detail.setFieldsObjectFromMaster(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD);
		detail.add(FieldHelper.getInstance().buildPath(Period.FIELD_TO_DATE)).addBreak();
	}
	
	public static void addOwner(FormDetail detail){
		detail.setFieldsObjectFromMaster(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER);
		detail.add(GlobalIdentifier.FIELD_OWNER).addBreak();
	}
	
	public static void addClosed(FormDetail detail){
		detail.setFieldsObjectFromMaster(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER);
		detail.add(GlobalIdentifier.FIELD_CLOSED).addBreak();
	}
	
	public static void addImage(FormDetail detail){
		detail.setFieldsObjectFromMaster(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER);
		detail.add(GlobalIdentifier.FIELD_IMAGE).addBreak();
	}
	
	public static void addDescription(FormDetail detail){
		detail.setFieldsObjectFromMaster(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER);
		detail.add(GlobalIdentifier.FIELD_DESCRIPTION).addBreak();
	}
}
