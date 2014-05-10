package org.cyk.ui.api.layout;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;


public abstract class AbstractLayout extends AbstractBean implements UILayout {

	private static final long serialVersionUID = -3972921029089528026L;
	
	@Getter @Setter protected Integer rowsCount=0,columnsCount = 2;
	private Integer _columnsCounter=0;
	@Getter @Setter private AbstractMethod<Object, Object> onAddRow;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		_columnsCounter = 0;
	}
	
	@Override
	public void addRow() {
		rowsCount++;
		_columnsCounter=0;
		if(onAddRow!=null)
			onAddRow.execute();
	}
	
	@Override
	public void addColumn(UIInputOutputComponent<?>...components) {
		for(UIInputOutputComponent<?> component : components)
			_columnsCounter += component.getWidth();
		if(_columnsCounter>=columnsCount)
			addRow();
	}

	
}
