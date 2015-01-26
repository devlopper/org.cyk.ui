package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import org.cyk.ui.web.primefaces.PrimefacesEventCalendar;

@FacesComponent(value="org.cyk.ui.primefaces.EventCalendar")
public class EventCalendarCompositeComponent extends AbstractCompositeComponent<PrimefacesEventCalendar> {

    enum PropertyKeys {
        value
    }

}