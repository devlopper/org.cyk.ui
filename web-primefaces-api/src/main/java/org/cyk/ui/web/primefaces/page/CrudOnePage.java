package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.UICommand;

@Named
@ViewScoped
@Getter
@Setter
public class CrudOnePage extends AbstractBusinessEntityFormOnePage<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() {
		super.initialisation();
		form.setDynamic(Boolean.TRUE);
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		switch(crud){
		case CREATE:getGenericBusiness().create(identifiable);break;
		case READ:break;
		case UPDATE:getGenericBusiness().update(identifiable);break;
		case DELETE:getGenericBusiness().delete(identifiable);break;
		}
	}

	
}
