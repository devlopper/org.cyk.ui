package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.ui.api.model.mathematics.MovementDetails;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class MovementConsultPage extends AbstractConsultPage<Movement> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private FormOneData<MovementDetails> details;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		details = createDetailsForm(MovementDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<Movement,MovementDetails>(Movement.class, MovementDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});
		
	}

}
