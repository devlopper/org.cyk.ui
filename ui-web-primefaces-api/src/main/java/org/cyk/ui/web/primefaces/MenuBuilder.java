package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.model.party.person.BloodGroup;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Medication;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRoleName;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.utility.common.userinterface.command.Menu;

public class MenuBuilder extends org.cyk.ui.web.primefaces.resources.MenuBuilder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Menu __execute__() {
		Menu menu = super.__execute__();
		
		if(Menu.Type.MAIN.equals(menu.getType())){
			//menu.addNode("person.list")._setPropertyUrl(Constant.Action.LIST, Person.class);
		/*
			menu.addNode("person.list")._setPropertyUrl(Constant.Action.LIST, Person.class);
			menu.addNode("phonenumbertype.list")._setPropertyUrl(Constant.Action.LIST, PhoneNumberType.class);
			menu.addNode("locationtype.list")._setPropertyUrl(Constant.Action.LIST, LocationType.class);
			menu.addNode("location.list")._setPropertyUrl(Constant.Action.LIST, Location.class);
			
			MenuNode menuNode = null;
			
			if(componentParent instanceof Session1MenusPage || componentParent instanceof Session2MenusPage || componentParent instanceof Session3MenusPage){
				menuNode = new MenuNode();
				menuNode.setLabelFromIdentifier("Session Item 1");
				menu.addOneChild(menuNode);
				
				menuNode = new MenuNode();
				menuNode.setLabelFromIdentifier("Session Item 2");
				menu.addOneChild(menuNode);
				
				if(componentParent instanceof Session1MenusPage){
					menuNode = new MenuNode();
					menuNode.setLabelFromIdentifier("Session I Item");
					menu.addOneChild(menuNode);
				}
				if(componentParent instanceof Session2MenusPage){
					menuNode = new MenuNode();
					menuNode.setLabelFromIdentifier("Session IIa Item");
					menu.addOneChild(menuNode);
					
					menuNode = new MenuNode();
					menuNode.setLabelFromIdentifier("Session IIb Item");
					menu.addOneChild(menuNode);
				}
				if(componentParent instanceof Session3MenusPage){
					menuNode = new MenuNode();
					menuNode.setLabelFromIdentifier("Session III Item");
					menu.addOneChild(menuNode);
				}
			}
			*/
		}else if(Menu.Type.CONTEXT.equals(menu.getType())){
			/*
			if(componentParent instanceof Session2MenusPage){
				MenuNode menuNode1 = new MenuNode();
				menuNode1.setLabelFromIdentifier("SCI A ");
				menu.addOneChild(menuNode1);
				
				MenuNode menuNode = new MenuNode();
				menuNode.setLabelFromIdentifier("A1");
				menuNode1.addOneChild(menuNode);
				
				menuNode = new MenuNode();
				menuNode.setLabelFromIdentifier("A2");
				menuNode1.addOneChild(menuNode);
			}
			*/
		}
		
		return menu;
	}
	
	@Override
	protected void addNodeIdentifiablesManage(Menu menu) {
		super.addNodeIdentifiablesManage(menu);
		menu.addNode("ui.menu.controlpanel.identifiables.manage.geography")
			.addNodeActionListMany(LocalityType.class,Locality.class,Country.class,LocationType.class,PhoneNumberType.class,PhoneNumber.class,ElectronicMailAddress.class
					,Location.class,Website.class,PostalBox.class,ContactCollection.class)
		;
	
		menu.addNode("ui.menu.controlpanel.identifiables.manage.person")
		.addNodeActionListMany(Allergy.class,Medication.class,BloodGroup.class,JobFunction.class,JobTitle.class,Sex.class,MaritalStatus.class,PersonTitle.class
				,PersonRelationshipTypeRoleName.class,PersonRelationshipTypeRole.class,PersonRelationshipType.class,PersonRelationshipTypeGroup.class,Person.class,
				PersonRelationship.class)
		;
		
		
	}
	
}