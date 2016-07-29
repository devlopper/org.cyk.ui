package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.mathematics.MovementDetails;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class MovementCollectionConsultPage extends AbstractConsultPage<MovementCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private Table<MovementDetails> movementTable;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		movementTable = (Table<MovementDetails>) createDetailsTable(MovementDetails.class, new DetailsConfigurationListener.Table.Adapter<Movement,MovementDetails>(Movement.class, MovementDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<Movement> getIdentifiables() {
				return RootBusinessLayer.getInstance().getMovementBusiness().findByCollection(identifiable);
			}
			@Override
			public Crud[] getCruds() {
				return new Crud[]{Crud.CREATE,Crud.READ,Crud.UPDATE};
			}
		});
	}

}
