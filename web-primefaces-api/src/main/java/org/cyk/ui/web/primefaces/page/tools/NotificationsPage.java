package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.event.Notification;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Named @ViewScoped
public class NotificationsPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 1003520116610569085L;

	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Inject private UserSession userSession;
	
	@Getter private List<Notification> notifications = new ArrayList<>();
	@Getter private UICommandable deleteFromSessionCommandable;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		loadNotifications();
		deleteFromSessionCommandable = instanciateCommandableBuilder().setLabelFromId("command.yesigotnotification").create();
		deleteFromSessionCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){

			private static final long serialVersionUID = 7665276507747433567L;

			@Override
			public void serve(UICommand command, Object parameter) {
				userAccountBusiness.deleteSessionNotification(userSession.getUserAccount(),(Notification) parameter);
				loadNotifications();
			}
		});
		if(uiManager.isMobileDevice(userDeviceType))
			((Commandable)deleteFromSessionCommandable).setUpdate(":main:form:notificationdatalist");
		else
			((Commandable)deleteFromSessionCommandable).setUpdate(":"+WebManager.getInstance().getFormId()+":notificationdatalist");
	}
	
	public void loadNotifications(){
		notifications = new ArrayList<>(userSession.getUserAccount().getSessionNotifications()); 
	}
	
	
}
