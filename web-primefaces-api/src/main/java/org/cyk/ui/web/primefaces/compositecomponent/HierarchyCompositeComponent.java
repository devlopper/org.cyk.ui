package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.web.primefaces.PrimefacesHierarchycalData;

@FacesComponent(value="org.cyk.ui.primefaces.Hierarchy")
public class HierarchyCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public PrimefacesHierarchycalData<?> getValue() {
        return (PrimefacesHierarchycalData<?>) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(PrimefacesHierarchycalData<?> hierarchycalData) {
        getStateHelper().put(PropertyKeys.value, hierarchycalData);
    } 

}