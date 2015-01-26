package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import org.cyk.ui.web.primefaces.PrimefacesHierarchycalData;

@FacesComponent(value="org.cyk.ui.primefaces.Hierarchy")
public class HierarchyCompositeComponent extends AbstractCompositeComponent<PrimefacesHierarchycalData<?>> {

    enum PropertyKeys {
        value
    }

}