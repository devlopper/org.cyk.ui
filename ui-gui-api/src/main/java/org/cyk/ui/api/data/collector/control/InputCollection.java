package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.ui.api.CommandHelper.Command;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CommandHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputCollection<T> extends AbstractBean implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	private IdentifiableRuntimeCollection<T> collection = new IdentifiableRuntimeCollection<>();
	
	private Command addCommand,deleteCommand;
	private Boolean isShowElementNameColumn = Boolean.TRUE;
	private String elementNameColumnName="THE N";
	private Collection<Listener<T>> listeners;
	
	/**/
	
	public InputCollection(Class<T> elementClass) {
		collection.setElementClass(elementClass);
		addCommand = (Command) CommandHelper.getInstance().getCommand();
		addCommand = new Command(){
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__() {
				add();
				return null;
			}
		};
		addCommand.setName("Add").setIcon("ui-icon-plus");
		
		deleteCommand = (Command) CommandHelper.getInstance().getCommand();
		deleteCommand = new Command(){
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__() {
				delete();
				return null;
			}
		};
		deleteCommand.setName("Delete").setIcon("ui-icon-trash");
	
	}
	
	protected void add(){
		collection.addOne();
	}
	
	protected void delete(){
		collection.removeItem(deleteCommand.getInput());
	}
	
	public InputCollection<T> addListener(Listener<T> listener){
		if(this.listeners == null)
			this.listeners = new ArrayList<>();
		this.listeners.add(listener);
		return this;
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.utility.common.helper.CollectionHelper.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	/**/
	
	public static interface Listener<T> {
		
		public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;

		}
		
		String METHOD_NAME_INSTANCIATE = "instanciate";
	}
}
