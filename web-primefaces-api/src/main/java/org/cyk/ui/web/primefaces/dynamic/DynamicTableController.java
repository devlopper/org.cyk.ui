package org.cyk.ui.web.primefaces.dynamic;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.PrimefacesTable;
import org.cyk.utility.common.AbstractMethod;

@Named
@ViewScoped
@Getter
@Setter
public class DynamicTableController extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private PrimefacesTable<AbstractIdentifiable> table;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		BusinessEntityInfos businessEntityInfos = uiManager.classFromKey(requestParameter(webManager.getRequestParameterClass()));
		
		table = (PrimefacesTable<AbstractIdentifiable>) tableInstance(businessEntityInfos.getClazz());
		table.addRow(genericBusiness.use((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz()).find().all());
		table.setEditable(true);
		
		table.getAddRowCommand().getCommand().setMessageManager(messageManager);
		table.getSaveRowCommand().setMessageManager(messageManager);
		table.getDeleteRowCommand().getCommand().setMessageManager(messageManager);
		table.getCancelRowCommand().setMessageManager(messageManager);
		
		table.getSaveRowCommand().setAfterFailureMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -4698491663673906259L;
			@Override
			protected Object __execute__(Object parameter) {
				messageDialogOkButtonOnClick="clickEditButtonRow('"+(table.getLastEditedRowIndex()+1)+"');";
				return null;
			}
		});
	}

}
