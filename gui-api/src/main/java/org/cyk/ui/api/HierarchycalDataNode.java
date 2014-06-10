package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.component.UIInputFieldDiscoverer;
import org.cyk.ui.api.editor.input.UIInputComponent;

@Getter @Setter
public class HierarchycalDataNode implements Serializable {

	private static final long serialVersionUID = 8042093058414618870L;
	
	private Object data;
	private Collection<UIInputComponent<?>> inputComponents = new ArrayList<>();
	
	public HierarchycalDataNode(Object data) {
		super();
		this.data = data;
		UIInputFieldDiscoverer fieldDiscoverer = new UIInputFieldDiscoverer();
		fieldDiscoverer.setObjectModel(data);
		for(UIInputComponent<?> input : fieldDiscoverer.run().getInputComponents()){
			//input.setReadOnly(true);
			inputComponents.add(UIManager.getInstance().getComponentCreateMethod().execute(input));
		}
	}
	
	@Override
	public String toString() {
		return data.toString();
	}

}
