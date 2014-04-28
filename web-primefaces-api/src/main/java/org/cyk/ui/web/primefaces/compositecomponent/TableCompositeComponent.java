package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.api.model.table.Table;

@FacesComponent(value="org.cyk.ui.primefaces.Table")
public class TableCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public Table<?> getValue() {
        return (Table<?>) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(Table<?> table) {
        getStateHelper().put(PropertyKeys.value, table);
    } 

}