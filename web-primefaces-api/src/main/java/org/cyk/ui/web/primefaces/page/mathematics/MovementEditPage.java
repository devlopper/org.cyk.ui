package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;

@Named @ViewScoped @Getter @Setter
public class MovementEditPage extends AbstractMovementEditPage<Movement> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected Movement getMovement() {
		return identifiable;
	}
	
	@Override
	protected Movement instanciateIdentifiable() {
		Long collectionIdentifier = requestParameterLong(MovementCollection.class);
		if(collectionIdentifier==null)
			return super.instanciateIdentifiable();
		return RootBusinessLayer.getInstance().getMovementBusiness().instanciateOne(RootBusinessLayer.getInstance().getMovementCollectionBusiness().find(collectionIdentifier), Boolean.TRUE);
	}
	
	@Getter @Setter
	public static class Form extends AbstractMovementForm<Movement> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Override
		public Movement getMovement() {
			return identifiable;
		}
	}
	
	public static class Adapter extends AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.Adapter.Default<Movement> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(Movement.class);
			FormConfiguration configuration = createFormConfiguration(Crud.CREATE, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addRequiredFieldNames(Form.FIELD_COLLECTION,/*Form.FIELD_ACTION,*/Form.FIELD_VALUE);
			//configuration.addFieldNames(Form.FIELD_CURRENT_TOTAL,Form.FIELD_NEXT_TOTAL);
			
			configuration = createFormConfiguration(Crud.UPDATE, UIManager.getInstance().businessEntityInfos(entityTypeClass).getUserInterface().getLabelId());
			configuration.addRequiredFieldNames(Form.FIELD_VALUE);
			
			configuration = createFormConfiguration(Crud.DELETE);
			configuration.addFieldNames(Form.FIELD_VALUE);
			
		}
		
	}
	

}
