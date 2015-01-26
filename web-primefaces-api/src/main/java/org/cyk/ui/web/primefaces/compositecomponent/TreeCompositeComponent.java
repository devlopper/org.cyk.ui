package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;

import org.cyk.ui.web.primefaces.Tree;

@FacesComponent(value="org.cyk.ui.primefaces.Tree")
public class TreeCompositeComponent extends AbstractCompositeComponent<Tree> {

    enum PropertyKeys {
        value
    }

}