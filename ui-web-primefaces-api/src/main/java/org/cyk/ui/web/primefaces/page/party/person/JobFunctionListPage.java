package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.ui.web.primefaces.page.AbstractListPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class JobFunctionListPage extends AbstractListPage<JobFunction> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
