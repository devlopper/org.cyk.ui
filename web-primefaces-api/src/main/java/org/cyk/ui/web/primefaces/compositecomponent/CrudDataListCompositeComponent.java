package org.cyk.ui.web.primefaces.compositecomponent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.cyk.ui.api.model.table.CrudDataTable;

@FacesComponent(value="org.cyk.ui.primefaces.CrudDataTable")
public class CrudDataListCompositeComponent extends UINamingContainer {

    enum PropertyKeys {
        value
    }

    public CrudDataTable<?> getValue() {
        return (CrudDataTable<?>) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(CrudDataTable<?> table) {
        getStateHelper().put(PropertyKeys.value, table);
    } 

}