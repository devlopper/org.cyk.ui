package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.UIMessageManager.Text;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractView extends AbstractBean implements View,Serializable {

	private static final long serialVersionUID = 898216365028456783L;
	
	/**/
	protected UIManager uiManager = UIManager.getInstance();
	
	@Getter @Setter protected String title,templateId;
	@Getter protected Collection<View> children = new ArrayList<>();
	@Getter protected Collection<ViewListener> viewListeners = new ArrayList<>();
	
	protected UIProvider uiProvider = UIProvider.getInstance();
	
	/**/ 
	
	public final void build(){
		//parent first
		__build__();
		
		for(ViewListener listener : viewListeners)
			listener.build(this);
		//then children
		for(View child : children)
			child.build();
	}
	
	public void __build__(){
		
	}
	
	protected String text(String code){
		return UIManager.getInstance().text(code);
	}
	
	public UICommandable createCommandable(CommandListener commandListener, String labelId, IconType iconType, EventListener anExecutionPhase, ProcessGroup aProcessGroup) {
		return uiProvider.createCommandable(commandListener, labelId, iconType, anExecutionPhase, aProcessGroup);
	}
	
	public UICommandable createCommandable(CommandListener commandListener, String labelId, IconType iconType) {
		return createCommandable(commandListener, labelId, iconType, null, null);
	}
	
	/* Messages */
	
	protected void showMessage(SeverityType severityType,String summaryId,String detailsId) {
		MessageManager.INSTANCE.message(severityType, new Text(summaryId), new Text(detailsId));
	}
	
	protected void showMessage(SeverityType severityType,String id) {
		showMessage(severityType,id+".summary", id+".details");
	}
	
	protected void showInfoMessage(String id) {
		showMessage(SeverityType.INFO, id);
	}
	
	protected void showErrorMessage(String id) {
		showMessage(SeverityType.ERROR, id);
	}
	
	@Override
	public String toString() {
		return title;
	}

	
}
