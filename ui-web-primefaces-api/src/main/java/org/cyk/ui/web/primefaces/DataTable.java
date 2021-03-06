package org.cyk.ui.web.primefaces;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
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
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.time.Period;
import org.cyk.ui.web.primefaces.file.FileIdentifiableEditPageFormMaster;
import org.cyk.ui.web.primefaces.mathematics.MathematicsIdentifiablePages;
import org.cyk.ui.web.primefaces.mathematics.movement.MovementIdentifiablePages;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;

public class DataTable {

	public static class Listener extends org.cyk.ui.web.primefaces.resources.component.DataTable.Listener {
		private static final long serialVersionUID = 1L;
		
		@Override
		public List<String> getColumnsFieldNamesOrder(org.cyk.utility.common.userinterface.collection.DataTable dataTable) {
			if(PhoneNumber.class.equals(dataTable.getPropertiesMap().getActionOnClass()))
				return Arrays.asList(PhoneNumber.FIELD_NUMBER,PhoneNumber.FIELD_TYPE,PhoneNumber.FIELD_LOCATION_TYPE,PhoneNumber.FIELD_COUNTRY,PhoneNumber.FIELD_COLLECTION);
			else if(Location.class.equals(dataTable.getPropertiesMap().getActionOnClass()))
				return Arrays.asList(Location.FIELD_LOCALITY,FieldHelper.getInstance().buildPath(Location.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_OTHER_DETAILS)
						,Location.FIELD_TYPE,Location.FIELD_COLLECTION);
			else if(ElectronicMailAddress.class.equals(dataTable.getPropertiesMap().getActionOnClass()))
				return Arrays.asList(ElectronicMailAddress.FIELD_ADDRESS,ElectronicMailAddress.FIELD_COLLECTION);
			else if(PostalBox.class.equals(dataTable.getPropertiesMap().getActionOnClass()))
				return Arrays.asList(PostalBox.FIELD_ADDRESS,PostalBox.FIELD_COLLECTION);
			else if(Website.class.equals(dataTable.getPropertiesMap().getActionOnClass()))
				return Arrays.asList(Website.FIELD_UNIFORM_RESOURCE_LOCATOR,Website.FIELD_COLLECTION);
			return super.getColumnsFieldNamesOrder(dataTable);
		}
		
		@Override
		public void processColumnsFieldNames(org.cyk.utility.common.userinterface.collection.DataTable dataTable,Collection<String> fieldNames) {
			super.processColumnsFieldNames(dataTable, fieldNames);
			Class<?> actionOnClass = (Class<?>) dataTable.getPropertiesMap().getActionOnClass();
			if(ClassHelper.getInstance().isInstanceOf(AbstractCollection.class, actionOnClass)){
				if(IntervalCollection.class.equals(actionOnClass))
					MathematicsIdentifiablePages.processIntervalCollectionColumnsFieldNames(dataTable, fieldNames);
				else if(MovementCollection.class.equals(actionOnClass))
					MovementIdentifiablePages.processMovementCollectionColumnsFieldNames(dataTable, fieldNames);
				else if(MovementCollectionInventory.class.equals(actionOnClass))
					MovementIdentifiablePages.processMovementCollectionInventoryColumnsFieldNames(dataTable, fieldNames);
			}else if(ClassHelper.getInstance().isInstanceOf(AbstractCollectionItem.class, actionOnClass)){
				if(!(dataTable.getPropertiesMap().getMaster() instanceof AbstractCollection))
					fieldNames.add(AbstractCollectionItem.FIELD_COLLECTION);
				if(Interval.class.equals(actionOnClass)){
					MathematicsIdentifiablePages.processIntervalColumnsFieldNames(dataTable, fieldNames);
				}else if(Movement.class.equals(actionOnClass))
					MovementIdentifiablePages.processMovementColumnsFieldNames(dataTable, fieldNames);
				else if(MovementCollectionValuesTransferItemCollectionItem.class.equals(actionOnClass))
					MovementIdentifiablePages.processMovementCollectionValuesTransferItemCollectionItemColumnsFieldNames(dataTable, fieldNames);
				else if(MovementCollectionInventoryItem.class.equals(actionOnClass))
					MovementIdentifiablePages.processMovementCollectionInventoryItemColumnsFieldNames(dataTable, fieldNames);
				else if(MovementGroupItem.class.equals(actionOnClass))
					MovementIdentifiablePages.processMovementGroupItemColumnsFieldNames(dataTable, fieldNames);
				else if(ClassHelper.getInstance().isInstanceOf(Contact.class, actionOnClass)){
					if(PhoneNumber.class.equals(actionOnClass)){
						fieldNames.add(PhoneNumber.FIELD_COUNTRY);
						fieldNames.add(PhoneNumber.FIELD_LOCATION_TYPE);
						fieldNames.add(PhoneNumber.FIELD_NUMBER);
					}else if(Location.class.equals(actionOnClass)){
						fieldNames.add(Location.FIELD_LOCALITY);
						fieldNames.add(FieldHelper.getInstance().buildPath(Location.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_OTHER_DETAILS));
					}else if(ElectronicMailAddress.class.equals(actionOnClass)){
						fieldNames.add(ElectronicMailAddress.FIELD_ADDRESS);
					}else if(PostalBox.class.equals(actionOnClass)){
						fieldNames.add(PostalBox.FIELD_ADDRESS);
					}else if(Website.class.equals(actionOnClass)){
						fieldNames.add(Website.FIELD_UNIFORM_RESOURCE_LOCATOR);
					}
					
				}else if(IdentifiablePeriod.class.equals(actionOnClass)){
					addExistencePeriodFromDate(dataTable, fieldNames);
					addExistencePeriodToDate(dataTable, fieldNames);
					addClosed(dataTable, fieldNames);
				}
			}else if(Locality.class.equals(actionOnClass)){
				
			}else if(Country.class.equals(actionOnClass)){
				fieldNames.add(Country.FIELD_PHONE_NUMBER_CODE);
				fieldNames.add(Country.FIELD_CONTINENT);
			}else if(ClassHelper.getInstance().isInstanceOf(AbstractJoinGlobalIdentifier.class, actionOnClass)){
				fieldNames.remove(FieldHelper.getInstance().buildPath(AbstractJoinGlobalIdentifier.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE));
				fieldNames.remove(FieldHelper.getInstance().buildPath(AbstractJoinGlobalIdentifier.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
				if(dataTable.getPropertiesMap().getMaster()==null)
					fieldNames.add(AbstractJoinGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER);
				if(PartyIdentifiableGlobalIdentifier.class.equals(actionOnClass)){
					fieldNames.add(PartyIdentifiableGlobalIdentifier.FIELD_BUSINESS_ROLE);
					fieldNames.add(PartyIdentifiableGlobalIdentifier.FIELD_PARTY);
				}else if(IdentifiablePeriodCollectionIdentifiableGlobalIdentifier.class.equals(actionOnClass)){
					fieldNames.add(IdentifiablePeriodCollectionIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_PERIOD_COLLECTION);
				}
			}else if(Person.class.equals(actionOnClass)){
				processColumnsFieldNamesPerson(dataTable, fieldNames);
			}else if(ClassHelper.getInstance().isInstanceOf(AbstractActor.class, actionOnClass)){
				processColumnsFieldNamesActor(dataTable, fieldNames);
			}else if(File.class.equals(actionOnClass)){
				FileIdentifiableEditPageFormMaster.processColumnsFieldNamesFile(dataTable, fieldNames);
			}else if(Script.class.equals(actionOnClass)){
				FileIdentifiableEditPageFormMaster.processColumnsFieldNamesScript(dataTable, fieldNames);
			}
		}
		
		/**/
		
		protected void __processColumnsFieldNamesPerson__(org.cyk.utility.common.userinterface.collection.DataTable dataTable,Collection<String> fieldNames,String fieldName){
			fieldNames.add(FieldHelper.getInstance().buildPath(fieldName,Person.FIELD_LASTNAMES));
		}
		
		protected void processColumnsFieldNamesPerson(org.cyk.utility.common.userinterface.collection.DataTable dataTable,Collection<String> fieldNames){
			__processColumnsFieldNamesPerson__(dataTable, fieldNames, null);
		}
		
		protected void processColumnsFieldNamesActor(org.cyk.utility.common.userinterface.collection.DataTable dataTable,Collection<String> fieldNames){
			__processColumnsFieldNamesPerson__(dataTable, fieldNames, AbstractActor.FIELD_PERSON);
		}
	
		/**/
		
		public static void addExistencePeriodFromDate(org.cyk.utility.common.userinterface.collection.DataTable dataTable,Collection<String> fieldNames){
			fieldNames.add(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE));
		}
		
		public static void addExistencePeriodToDate(org.cyk.utility.common.userinterface.collection.DataTable dataTable,Collection<String> fieldNames){
			fieldNames.add(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_TO_DATE));
		}
		
		public static void addClosed(org.cyk.utility.common.userinterface.collection.DataTable dataTable,Collection<String> fieldNames){
			fieldNames.add(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CLOSED));
		}
	}
	
	/**/
	
	
	
}
