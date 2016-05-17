package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.model.AbstractQueryManyFormModel;
import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.AbstractSelectManyPage;
import org.cyk.ui.web.primefaces.test.business.GuiBusinessLayer;
import org.cyk.ui.web.primefaces.test.business.MyWebManager;

@Getter @Setter
public class ActorSelectManyPageAdapter extends AbstractSelectManyPage.Listener.Adapter.Default<Actor,String> implements Serializable {

	private static final long serialVersionUID = -7392513843271510254L;
	
	public ActorSelectManyPageAdapter() {
		super(Actor.class);
	}
	
	@Override
	public void serve(AbstractSelectManyPage<?> selectManyPage, Object data, String actionIdentifier) {
		if(MyWebManager.getInstance().getEditManyActors().equals(actionIdentifier))
			WebNavigationManager.getInstance().redirectToEditManyPage("editmanyperson",Actor.class,((AbstractQueryManyFormModel)data).getIdentifiables());
		super.serve(selectManyPage, data, actionIdentifier);
	}
	
	@Override
	public Collection<Actor> getIdentifiables(AbstractSelectManyPage<?> selectManyPage) {
		return GuiBusinessLayer.getInstance().getActorBusiness().findAll();
	}

}
