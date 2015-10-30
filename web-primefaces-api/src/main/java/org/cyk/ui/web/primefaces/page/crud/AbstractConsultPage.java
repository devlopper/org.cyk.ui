package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityPrimefacesPage;

@Getter @Setter
public abstract class AbstractConsultPage<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 9040359120893077422L;

	
	

}