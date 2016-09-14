package org.cyk.ui.web.primefaces.api.integration;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.test.automation.Form;

public abstract class AbstractEntityWebIT extends AbstractWebIT {

	private static final long serialVersionUID = 1L;
	
	protected void list(){
		clickGlobalMenu(getListMenuItemPath());
	}
	protected abstract String[] getListMenuItemPath();
	
	protected void create(){
		clickTableCreate(getIdentifiableClass());    
        getForm(Crud.CREATE).sendKeys().submit();
	}
	protected abstract Form getForm(Crud crud);
	
	protected void read(){
		clickTableRead(getIdentifiableClass(),getIdentifier(Crud.READ));
	}
	
	protected void update(){
		clickContextualMenuEdit();
		getForm(Crud.UPDATE).sendKeys().submit();
	}
	
	protected void delete(){
		clickContextualMenuDelete();
		getForm(Crud.DELETE).setSubmitCommandableConfirmed(Boolean.TRUE).sendKeys().submit();
	}
	
    @Override
	protected void __execute__() {
       super.__execute__();
       list();
       create();
       read();
       update();
       delete();
	}
    
    protected abstract Class<? extends AbstractIdentifiable> getIdentifiableClass();
    protected abstract String getIdentifier(Crud crud);
       
}
