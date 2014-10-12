package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.web.primefaces.Tree;

@FacesComponent(value="org.cyk.ui.primefaces.Tree")
public class TreeCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public Tree getValue() {
        return (Tree) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(Tree tree) {
        getStateHelper().put(PropertyKeys.value, tree);
    } 

}