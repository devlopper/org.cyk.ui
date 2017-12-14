package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.hierarchy.Hierarchy;

public class ComponentAdapter extends Component.Listener.Adapter.Default implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object build(Component component) {
		if(component instanceof Form.Detail)
			return Form.Detail.buildTarget((Form.Detail) component);
		if(component instanceof Menu)
			return new MenuBasedOnMenuModel().setInput((Menu) component).execute();
		if(component instanceof Hierarchy)
			return new HierarchyBasedOnTreeNode().setInput((Hierarchy) component).execute();
		return super.build(component);
	}
	
}
