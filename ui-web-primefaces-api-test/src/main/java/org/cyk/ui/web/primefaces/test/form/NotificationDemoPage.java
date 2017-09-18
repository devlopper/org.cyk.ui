package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.helper.NotificationHelper;
import org.cyk.utility.common.helper.NotificationHelper.Notification;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class NotificationDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	public void viewNotifiactionInline(){
		Notification notification = NotificationHelper.getInstance().getNotification("notification.operation.executed.success");
		NotificationHelper.getInstance().getViewer().setInput(notification).setType(NotificationHelper.Notification.Viewer.Type.INLINE).execute();
	}
	
	public void viewNotifiactionDialog(){
		Notification notification = NotificationHelper.getInstance().getNotification("notification.operation.executed.success");
		NotificationHelper.getInstance().getViewer().setInput(notification).setType(NotificationHelper.Notification.Viewer.Type.DIALOG).execute();
	}
	
	public void viewNotifiactionGrowl(){
		Notification notification = NotificationHelper.getInstance().getNotification("notification.operation.executed.success");
		NotificationHelper.getInstance().getViewer().setInput(notification).setType(NotificationHelper.Notification.Viewer.Type.GROWL).execute();
	}
	
	public void viewNotifiactionDefault(){
		Notification notification = NotificationHelper.getInstance().getNotification("notification.operation.executed.success");
		NotificationHelper.getInstance().getViewer().setInput(notification).setType(NotificationHelper.Notification.Viewer.Type.DEFAULT).execute();
	}
}
