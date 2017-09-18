package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;

import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.ui.api.command.Command;
import org.cyk.utility.common.cdi.AbstractBean;

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
		//addCommand = new Command();
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.utility.common.helper.CollectionHelper.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
}
