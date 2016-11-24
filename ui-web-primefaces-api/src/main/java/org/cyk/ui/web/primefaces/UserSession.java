package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.web.api.AbstractWebUserSession;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.MenuModel;

@SessionScoped @Named
public class UserSession extends AbstractWebUserSession<TreeNode,HierarchyNode> implements Serializable {

	private static final long serialVersionUID = -4310383407889787288L;

	//private static final EventBus BUS = EventBusFactory.getDefault().eventBus();
	
	@Getter private MenuModel contextualMenuModel;
	
	@Override
	public void init(UserAccount userAccount) {
		super.init(userAccount);
		this.contextualMenuModel = CommandBuilder.getInstance().menuModel(contextualMenu, getClass(), "contextualMenuModel");
	}
	
	@Override
	protected void __notificationFired__(Notification notification,FacesMessage facesMessage) {
		super.__notificationFired__(notification, facesMessage);
		PrimefacesManager.getInstance().getEventBus().publish(notificationChannel, facesMessage);
	}

}