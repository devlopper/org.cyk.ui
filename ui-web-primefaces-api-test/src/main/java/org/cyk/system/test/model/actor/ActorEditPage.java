package org.cyk.system.test.model.actor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ActorEditPage extends AbstractCrudOnePage<Actor> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private Person father,mother;
	
	
	@Override
	protected void initialisation() {
		super.initialisation();
		if(Crud.isCreateOrUpdate(crud)){
			if(Crud.CREATE.equals(crud)){
				
			}else{
				father = inject(PersonBusiness.class).findOneByPersonByRelationshipType(identifiable.getPerson(), PersonRelationshipType.FAMILY_FATHER);
				mother = inject(PersonBusiness.class).findOneByPersonByRelationshipType(identifiable.getPerson(), PersonRelationshipType.FAMILY_MOTHER);
				inject(ContactCollectionBusiness.class).load(father.getContactCollection());
				inject(ContactCollectionBusiness.class).load(mother.getContactCollection());
			}
			
			if(father==null)
				father = inject(PersonBusiness.class).addRelationship(identifiable.getPerson(), PersonRelationshipType.FAMILY_FATHER).getPerson1();
			if(mother==null)
				mother = inject(PersonBusiness.class).addRelationship(identifiable.getPerson(), PersonRelationshipType.FAMILY_MOTHER).getPerson1();
		}
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		setFieldValue(Form.FIELD_FATHER_ELECTRONIC_MAIL, inject(ElectronicMailBusiness.class).findAddress(father));
		setFieldValue(Form.FIELD_MOTHER_ELECTRONIC_MAIL, inject(ElectronicMailBusiness.class).findAddress(mother));
	}
	
	@Override
	protected void create() {
		inject(ElectronicMailBusiness.class).setAddress(father, ((Form)form.getData()).getFatherElectronicMail());
    	inject(ElectronicMailBusiness.class).setAddress(mother, ((Form)form.getData()).getMotherElectronicMail());
		super.create();
	}
	
	@Override
	protected void update() {
		inject(ElectronicMailBusiness.class).setAddress(father, ((Form)form.getData()).getFatherElectronicMail());
    	inject(ElectronicMailBusiness.class).setAddress(mother, ((Form)form.getData()).getMotherElectronicMail());
		super.update();
	}
	
	@Override
	protected Collection<? extends AbstractIdentifiable> getIdentifiables() {
		if(Crud.UPDATE.equals(crud)){
			Collection<AbstractIdentifiable> collection = new ArrayList<>();
			collection.add(father);
			collection.add(mother);
			return collection;
		}
		return super.getIdentifiables();
	}
	
	public static class Form extends AbstractActorEditFormModel.AbstractDefault.Default<Actor> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
	}

}
