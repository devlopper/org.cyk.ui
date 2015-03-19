package org.cyk.ui.desktop.swing;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.ui.api.MessageManager;
import org.cyk.ui.desktop.api.DesktopUIMessageManager;

@Singleton
public class SwingMessageManager extends DesktopUIMessageManager implements Serializable {

	private static final long serialVersionUID = -2135903644205681102L;
	
	@Override
	protected void initialisation() {
		MessageManager.INSTANCE = this;
		super.initialisation();
	}
	
	@Override
	protected void __showDialog__() {
        //JOptionPane.showMessageDialog(null, builtMessage.getDetails());
	}

}