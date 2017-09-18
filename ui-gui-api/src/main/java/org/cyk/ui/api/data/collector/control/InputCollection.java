package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;

import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.ui.api.CommandHelper.Command;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputCollection<T> extends AbstractBean implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	private IdentifiableRuntimeCollection<T> collection = new IdentifiableRuntimeCollection<>();
	
	private Command addCommand,deleteCommand;
	private Boolean isShowElementNameColumn = Boolean.TRUE;
	private String elementNameColumnName="THE N";
	
	/**/
	
	public InputCollection() {
		addCommand = (Command) CommandHelper.getInstance().getCommand();
		addCommand = new Command(){
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__() {
				System.out.println("InputCollection.InputCollection().new Command() {...}.__execute__()");
				return null;
			}
		};
		addCommand.setProperty(Command.COMMAND_PROPERTY_NAME_LABEL, "Add");
		addCommand.setProperty(Command.COMMAND_PROPERTY_NAME_ICON, "ui-icon-plus");
		
		deleteCommand = (Command) CommandHelper.getInstance().getCommand();
		deleteCommand = new Command(){
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__() {
				ThrowableHelper.getInstance().throw_("Sorry!!!!");
				return null;
			}
		};
		deleteCommand.setProperty(Command.COMMAND_PROPERTY_NAME_LABEL, "Delete");
		deleteCommand.setProperty(Command.COMMAND_PROPERTY_NAME_ICON, "ui-icon-trash");
	
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.utility.common.helper.CollectionHelper.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
}
