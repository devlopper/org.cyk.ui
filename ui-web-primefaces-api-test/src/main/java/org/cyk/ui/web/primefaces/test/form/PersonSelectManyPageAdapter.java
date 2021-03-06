package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.test.business.MyWebManager;
import org.cyk.ui.api.model.AbstractQueryManyFormModel;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.AbstractSelectManyPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonSelectManyPageAdapter extends AbstractSelectManyPage.Listener.Adapter.Default<Person,String> implements Serializable {

	private static final long serialVersionUID = -7392513843271510254L;
	
	public PersonSelectManyPageAdapter() {
		super(Person.class);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void serve(AbstractSelectManyPage<?> selectManyPage, Object data, String actionIdentifier) {
		if(MyWebManager.getInstance().getEditManyPersons().equals(actionIdentifier))
			WebNavigationManager.getInstance().redirectToEditManyPage("editmanyperson",Person.class,((AbstractQueryManyFormModel)data).getIdentifiables());
		super.serve(selectManyPage, data, actionIdentifier);
	}
	
	@Override
	public Collection<Person> getIdentifiables(AbstractSelectManyPage<?> selectManyPage) {
		return inject(PersonBusiness.class).findAll();
	}

}
