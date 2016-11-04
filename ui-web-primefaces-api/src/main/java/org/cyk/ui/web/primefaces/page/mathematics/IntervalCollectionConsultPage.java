package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.mathematics.IntervalDetails;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.ui.web.primefaces.page.AbstractCollectionConsultPage;

@Named @ViewScoped @Getter @Setter
public class IntervalCollectionConsultPage extends AbstractCollectionConsultPage.Extends<IntervalCollection,Interval,IntervalDetails> implements Serializable {
	
	private static final long serialVersionUID = 3274187086682750183L;
	
}
