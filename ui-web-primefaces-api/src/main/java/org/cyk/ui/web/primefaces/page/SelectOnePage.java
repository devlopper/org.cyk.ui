package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class SelectOnePage extends AbstractSelectOnePage<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = -7392513843271510254L;
	

}
