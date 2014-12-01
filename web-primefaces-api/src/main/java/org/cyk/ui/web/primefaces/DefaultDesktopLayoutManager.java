package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Named @Getter @Setter
public class DefaultDesktopLayoutManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 776758304463284101L;

	private String northInclude = "/org.cyk.ui.web.primefaces/include/layout/default/north.xhtml";
	private String westInclude = "/org.cyk.ui.web.primefaces/include/layout/default/west.xhtml";
	
}
