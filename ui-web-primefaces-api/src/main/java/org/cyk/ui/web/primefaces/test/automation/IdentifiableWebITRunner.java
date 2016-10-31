package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.page.event.EventEditPage;
import org.cyk.utility.common.ListenerUtils;

public class IdentifiableWebITRunner<IDENTIFIABLE extends AbstractIdentifiable> extends org.cyk.utility.common.test.Runnable.Adapter<IdentifiableWebITRunner.Listener<IDENTIFIABLE>> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SeleniumHelper helper = SeleniumHelper.getInstance();
	private Class<IDENTIFIABLE> clazz;
	
	private void constructor(Class<IDENTIFIABLE> clazz) {
		this.clazz = clazz;
	}
	
	public IdentifiableWebITRunner(Class<IDENTIFIABLE> clazz) {
		constructor(clazz);
	}
	@SuppressWarnings("unchecked")
	public IdentifiableWebITRunner() {
		constructor((Class<IDENTIFIABLE>) commonUtils.getClassParameterAt(this.getClass(), 0));
	}
	
	protected String[] getListMenuItemPath(){
		return listenerUtils.getValue(String[].class, listeners, new ListenerUtils.ResultMethod<Listener<IDENTIFIABLE>, String[]>(){
			@Override
			public String[] execute(Listener<IDENTIFIABLE> listener) {
				return listener.getListMenuItemPath();
			}

			@Override
			public String[] getNullValue() {
				return null;
			}
		});
	}
	
	protected void list(){
		helper.clickGlobalMenu(getListMenuItemPath());
	}
	
	protected Form createForm(final Crud crud){
		Form form = listenerUtils.getValue(Form.class, listeners, new ListenerUtils.ResultMethod<Listener<IDENTIFIABLE>, Form>(){
			@Override
			public Form execute(Listener<IDENTIFIABLE> listener) {
				return listener.createForm(Crud.CREATE);
			}
			@Override
			public Form getNullValue() {
				return new Form();
			}
		});
		if(Crud.isCreateOrUpdate(crud)){
			String code = getCode(crud);
			if(code!=null)
				form.addInputText(EventEditPage.Form.FIELD_CODE, code);
		}
		return form;
	}
	protected void fillForm(final Form form,final Crud crud){
		listenerUtils.execute(listeners, new ListenerUtils.VoidMethod<Listener<IDENTIFIABLE>>(){
			@Override
			public void execute(Listener<IDENTIFIABLE> listener) {
				listener.fillForm(form, crud);
			}
		});
	}
	
	protected String getCode(final Crud crud){
		return listenerUtils.getString(listeners, new ListenerUtils.StringMethod<Listener<IDENTIFIABLE>>(){

			@Override
			public String execute(Listener<IDENTIFIABLE> listener) {
				return listener.getCode(Crud.CREATE);
			}
			
		});
	}
	
	protected void create(){
		helper.clickTableCreate(clazz);
		Form form = createForm(Crud.CREATE);
		fillForm(form, Crud.CREATE);
		form.sendKeys().submit();
	}
	
	protected void read(){
		helper.clickTableRead(clazz,getCode(Crud.READ));
	}
	
	protected void update(){
		helper.clickContextualMenuEdit();
		Form form = createForm(Crud.UPDATE);
		fillForm(form, Crud.UPDATE);
		form.sendKeys().submit();
	}
	
	protected void delete(){
		helper.clickContextualMenuDelete();
		createForm(Crud.DELETE).setSubmitCommandableConfirmed(Boolean.TRUE).sendKeys().submit();
	}
	
    public void run() {
    	list();
    	create();
    	read();
    	update();
    	delete();
	}
    
    public static interface Listener<IDENTIFIABLE extends AbstractIdentifiable> extends org.cyk.utility.common.test.Runnable.Listener{
    	
    	String[] getListMenuItemPath();
    	Form createForm(Crud crud);
    	void fillForm(Form form,Crud crud);
    	String getCode(Crud crud);
    	
    	/**/
    	
    	public static class Adapter<IDENTIFIABLE extends AbstractIdentifiable> extends org.cyk.utility.common.test.Runnable.Listener.Adapter implements Listener<IDENTIFIABLE>,Serializable{
			private static final long serialVersionUID = -8100384307117008109L;

			@Override
			public String[] getListMenuItemPath() {
				return null;
			}

			@Override
			public Form createForm(Crud crud) {
				return null;
			}
			
			@Override
			public void fillForm(Form form, Crud crud) {
					
			}

			@Override
			public String getCode(Crud crud) {
				return null;
			}

			
    	}
    }
}
