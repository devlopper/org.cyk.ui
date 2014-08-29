package org.cyk.ui.web.vaadin;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class VaadinUIManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -2936474937419525218L;

}
