package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.web.primefaces.PrimefacesEventCalendar;

@FacesComponent(value="org.cyk.ui.primefaces.EventCalendar")
public class EventCalendarCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public PrimefacesEventCalendar getValue() {
        return (PrimefacesEventCalendar) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(PrimefacesEventCalendar eventCalendar) {
        getStateHelper().put(PropertyKeys.value, eventCalendar);
    } 

}