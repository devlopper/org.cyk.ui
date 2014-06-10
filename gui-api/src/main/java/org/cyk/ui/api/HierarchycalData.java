package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.component.UIInputFieldDiscoverer;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public class HierarchycalData<DATA> extends AbstractBean implements UIWindowPart,Serializable {

	private static final long serialVersionUID = -9086692457789120721L;
	
	protected UIMenu menu = new DefaultMenu();
	protected UIWindow<?, ?, ?, ?,?> window;
	protected String title;
	protected Boolean editable=Boolean.FALSE;
	protected UIInputFieldDiscoverer discoverer = new UIInputFieldDiscoverer();
	
	protected DATA dataAdding;
	protected Integer lastEditedNodeIndex;
	
	protected List<DATA> data = new ArrayList<>();
	protected UICommandable addNodeCommand,saveNodeCommand,cancelNodeCommand,deleteNodeCommand;
	
	protected Class<DATA> nodeDataClass;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		addNodeCommand = new DefaultCommandable();
		addNodeCommand.setCommand(new DefaultCommand());
		addNodeCommand.setLabel(text("command.add"));
		addNodeCommand.setShowLabel(Boolean.FALSE);
		//addNodeCommand.setIcon("ui-icon-plus");
		addNodeCommand.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object parent) {
				if(dataAdding==null)
					try {
						data.add(dataAdding = nodeDataClass.newInstance());
						link((DATA) parent, dataAdding);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				else
					addNodeCommand.getCommand().getMessageManager().message(SeverityType.WARNING, "warning.table.canaddoneatatime", true).showDialog();
				return null;
			}
		});
	}
	
	protected void link(DATA parent,DATA child){
		LINK.execute(new Object[]{parent,child});
	}
	
	@Override
	public void targetDependentInitialisation() {} 
	
	private String text(String id){
		return getWindow().getUiManager().getLanguageBusiness().findText(id);
	}
	
	public static AbstractMethod<Object, Object> LINK = new AbstractMethod<Object, Object>() {
		private static final long serialVersionUID = 6739817055207710222L;
		@Override
		protected Object __execute__(Object parameter) {
			Object[] arrays = (Object[]) parameter;
			if(arrays[0] instanceof DataTreeType){
				if(((DataTreeType)arrays[0]).getChildren()==null)
					((DataTreeType)arrays[0]).setChildren(new ArrayList<DataTreeType>());
				return ((DataTreeType)arrays[0]).getChildren().add((DataTreeType)arrays[1]);
			}
			return null;
		}
	};

}
