package org.cyk.ui.desktop.swing.test;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.naming.InitialContext;

import org.cyk.system.root.business.api.RemoteConnectivityChecker;
import org.jboss.weld.environment.se.events.ContainerInitialized;

public class MainCdi {

	@Inject private SimpleJFrame simpleJFrame;
	
	public void main(@Observes ContainerInitialized containerInitialized) {
		try {
			InitialContext ctx = new InitialContext();
			RemoteConnectivityChecker remoteConnectivityChecker = (RemoteConnectivityChecker) ctx.lookup("RemoteConnectivityChecker");
			System.out.println("MainCdi.main() : "+remoteConnectivityChecker);
		} catch (Exception e) {
			e.printStackTrace();
		}
		simpleJFrame.setVisible(true);
	}
	
}
