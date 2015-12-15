package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractCrudOnePage<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBusinessEntityFormOnePage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() {
		super.initialisation();
		form.setDynamic(Boolean.TRUE);
		contentTitle = text("page.crud.one."+crud.name().toLowerCase())+" "+contentTitle;
		title = contentTitle;
		if(Crud.DELETE.equals(crud)){
			form.getSubmitCommandable().setLabel(text("command.delete"));
		}
		
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			switch(crud){
			case CREATE:create();break;
			case READ:break;
			case UPDATE:update();break;
			case DELETE:delete();break;
			}
		}
	}
	
	protected void create(){
		getGenericBusiness().create(identifiable);
	}
	
	protected void update(){
		getGenericBusiness().update(identifiable);
	}
	protected void delete(){
		getGenericBusiness().delete(identifiable);
	}

	@Override
	public Object succeed(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			switch(crud){
			case CREATE:case UPDATE:
				if(Boolean.TRUE.equals(consultOnSuccess())){
					messageDialogOkButtonOnClick=javaScriptHelper.windowHref(previousUrl = navigationManager.getConsultUrl(identifiable));
				}else
					messageDialogOkButtonOnClick=javaScriptHelper.windowHref(url); //WebNavigationManager.getInstance().redirectToUrl(url);
				break;
			default:
				if(StringUtils.isNotEmpty(previousUrl))
					messageDialogOkButtonOnClick=javaScriptHelper.windowHref(previousUrl);
				else
					messageDialogOkButtonOnClick=javaScriptHelper.windowHref(WebNavigationManager.getInstance().createManyUrl(businessEntityInfos, Boolean.FALSE, Boolean.FALSE));
				break;
			}
		}
		return super.succeed(command, parameter);
	}
	
	protected Boolean consultOnSuccess(){
		return Boolean.FALSE;
	}
}
