package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.mathematics.MovementDetails;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

@Named @ViewScoped @Getter @Setter
public class MovementListPage extends AbstractCrudManyPage<Movement> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static class Adapter extends AbstractBusinessEntityFormManyPage.BusinessEntityFormManyPageListener.Adapter.Default<Movement> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(Movement.class);
			FormConfiguration configuration = createFormConfiguration(Crud.READ, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addFieldNames(MovementDetails.FIELD_DATE,MovementDetails.FIELD_VALUE,MovementDetails.FIELD_COLLECTION);
		}
		
	}
	
}
