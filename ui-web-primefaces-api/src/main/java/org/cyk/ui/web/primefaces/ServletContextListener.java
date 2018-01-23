package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.helper.NotificationHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.ViewHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.hierarchy.Hierarchy;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.output.Output;

public class ServletContextListener extends org.cyk.ui.web.primefaces.resources.ServletContextListener implements Serializable { 
	private static final long serialVersionUID = 592943227142026384L;
	
	@SuppressWarnings("unchecked")
	@Override 
	public void __contextInitialized__(ServletContextEvent event) {
		super.__contextInitialized__(event);
		ClassHelper.getInstance().map(ViewHelper.Listener.Adapter.Default.class,org.cyk.ui.web.primefaces.ViewHelper.Listener.class);
		ClassHelper.getInstance().map(Input.Listener.class, InputAdapter.class);
		ClassHelper.getInstance().map(Output.Listener.class,OutputAdapter.class);
		ClassHelper.getInstance().map(Component.Listener.class,ComponentAdapter.class);
		
		ClassHelper.getInstance().map(org.cyk.ui.web.primefaces.resources.page.controlpanel.IdentifiableListPage.DataTable.class, IdentifiableListPageDataTable.class);
		ClassHelper.getInstance().map(org.cyk.ui.web.primefaces.resources.page.controlpanel.IdentifiableEditPage.FormMaster.class, IdentifiableEditPageFormMaster.class);
		ClassHelper.getInstance().map(org.cyk.ui.web.primefaces.resources.page.controlpanel.IdentifiableConsultPage.FormMaster.class, IdentifiableConsultPageFormMaster.class);
		
		ClassHelper.getInstance().map(DataTable.Listener.class, org.cyk.ui.web.primefaces.DataTable.Listener.class);
		ClassHelper.getInstance().map(Hierarchy.Listener.class, org.cyk.ui.web.primefaces.Hierarchy.Listener.class);
		
		ClassHelper.getInstance().map(Menu.Builder.Adapter.Default.class,MenuBuilder.class);
		
		//IconHelper.Icon.Mapping.Adapter.Default.DEFAULT_CLASS = (Class<IconHelper.Icon.Mapping>) ClassHelper.getInstance().getByName(org.cyk.ui.web.primefaces.IconHelper.Mapping.FontAwesome.class);
		NotificationHelper.Notification.Viewer.Adapter.Default.DEFAULT_CLASS = (Class<NotificationHelper.Notification.Viewer>) ClassHelper.getInstance().getByName(org.cyk.ui.web.primefaces.NotificationHelper.Viewer.class);
		CommandHelper.Command.Adapter.Default.DEFAULT_CLASS = (Class<CommandHelper.Command>) ClassHelper.getInstance().getByName(org.cyk.ui.web.primefaces.CommandHelper.Command.class);
		CommandHelper.Commands.DEFAULT_CLASS = (Class<CommandHelper.Commands>) ClassHelper.getInstance().getByName(org.cyk.ui.web.primefaces.CommandHelper.Commands.class);
		//Form.Detail.Builder.Target.Adapter.Default.DEFAULT_CLASS = (Class<? extends Target<?, ?, ?, ?>>) ClassHelper.getInstance().getByName(PrimefacesManager.FormBuilderBasedOnDynamicForm.class);
		
	}  
	
}
