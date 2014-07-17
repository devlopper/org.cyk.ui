package org.cyk.ui.desktop.swing.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.cyk.system.root.business.api.RemoteConnectivityChecker;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.desktop.swing.SwingMessageManager;

public class SimpleJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1340015151159380154L;

	//private MyEntity myEntity = new MyEntity();
	
	//@Inject private SwingMessageManager swingMessageManager;
	
	public SimpleJFrame() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void postConstruct() {
		/*
		try {
			InitialContext ctx = new InitialContext();
			RemoteConnectivityChecker remoteConnectivityChecker = (RemoteConnectivityChecker) ctx.lookup("RemoteConnectivityChecker");
			setTitle("My JFrame Title : "+remoteConnectivityChecker.getDate());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton b = new JButton("Dialog");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("SimpleJFrame.SimpleJFrame().new ActionListener() {...}.actionPerformed() : "+swingMessageManager);
				//swingMessageManager.message(SeverityType.INFO, "This is a message", false).showDialog();
				//System.out.println(MessageManager.INSTANCE);
			}
		});
		getContentPane().add(b);
	}
	
	/*
	public static void main(String[] args) throws IOException {
		System.out.println("SimpleJFrame.main() : MAIN");
		   Weld weld = new Weld();
		   WeldContainer container = weld.initialize();
		   System.out.println("SimpleJFrame.main() : "+container);
		   new SimpleJFrame().setVisible(true);
		   weld.shutdown();

		  }
	*/
}
