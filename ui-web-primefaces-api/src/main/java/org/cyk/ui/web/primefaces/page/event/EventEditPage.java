package org.cyk.ui.web.primefaces.page.event;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.event.Event;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.api.model.geography.ContactCollectionFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class EventEditPage extends AbstractCrudOnePage<Event> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	/*@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		List<?> parties = form.findInputByClassByFieldName(InputChoice.class, "parties").getList();
		for(int i = 0;i<parties.size();){
			Party party = (Party) parties.get(i);
			if(party instanceof Person){
				//Person person = (Person) party;
				i++;
			}else
				parties.remove(i);
		}
	}*/
	
	/*@Override
	protected void create() {
		EventFormModel model = (EventFormModel) form.getData();
		if(model.getEventReminder()==null)
			eventBusiness.create(identifiable);
		else
			eventBusiness.create(identifiable,Arrays.asList(model.getEventReminder()));
	}*/
	
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<Event> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@IncludeInputs(layout=IncludeInputs.Layout.VERTICAL) ContactCollectionFormModel contactCollection = new ContactCollectionFormModel(null);
		
		@Override
		public void read() {
			super.read();
			contactCollection.setIdentifiable(identifiable.getContactCollection());
			contactCollection.read();
		}
		
		public static final String FIELD_CONTACT_COLLECTION = "contactCollection";
	}
	
}
