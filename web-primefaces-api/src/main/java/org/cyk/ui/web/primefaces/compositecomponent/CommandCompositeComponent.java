package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.web.primefaces.Command;

@FacesComponent(value="org.cyk.ui.primefaces.Command")
public class CommandCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public Command getValue() {
        return (Command) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(Command command) {
        getStateHelper().put(PropertyKeys.value, command);
    } 

}