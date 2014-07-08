package org.cyk.ui.desktop.swing.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.RemoteConnectivityChecker;

public class SimpleJndi {

	public static void main(String[] args) {
		System.out.println("SimpleJndi.main()");
		try{
			/*
			Properties props = new Properties();
			/*
			java.naming.factory.initial=com.sun.enterprise.naming.SerialInitContextFactory
					java.naming.factory.url.pkgs=com.sun.enterprise.naming
					java.naming.factory.state=com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl
			*/
			/*
	        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
	        props.setProperty(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
	        props.setProperty(Context.STATE_FACTORIES,"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
	        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
	        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
	        */
	
	        InitialContext ctx = new InitialContext();
	        RemoteConnectivityChecker remoteConnectivityChecker = (RemoteConnectivityChecker) ctx.lookup("RemoteConnectivityChecker");
	        remoteConnectivityChecker.echo("I am online");
	        System.out.println(remoteConnectivityChecker.getDate());
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}

}
