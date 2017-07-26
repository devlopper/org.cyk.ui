package org.cyk.ui.web.primefaces.page.time;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.time.ScheduleItemDetails;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.web.primefaces.page.AbstractCollectionConsultPage;

@Named @ViewScoped @Getter @Setter
public class ScheduleConsultPage extends AbstractCollectionConsultPage.Extends<Schedule,ScheduleItem,ScheduleItemDetails> implements Serializable {
	
	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		itemTable.setTitle(inject(LanguageBusiness.class).findClassLabelText(ScheduleItem.class));
		itemTable.getAddRowCommandable().addParameter(identifiable);
		itemTable.setShowToolBar(Boolean.TRUE);
		itemTable.setShowActionsColumn(Boolean.TRUE);
	}
	
	@Override
	protected Boolean getEnableItemTableInDefaultTab() {
		return Boolean.TRUE;
	}
	
	@Override
	protected String getItemTableTabId() {
		return IdentifierProvider.Adapter.getTabOf(Schedule.class);
	}
	/*
	@Override
	protected Crud[] getItemTableCruds() {
		return new Crud[]{};
	}*/
}
