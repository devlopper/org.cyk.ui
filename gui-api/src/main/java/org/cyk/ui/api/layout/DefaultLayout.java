package org.cyk.ui.api.layout;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.ui.api.component.IComponent;


public class DefaultLayout extends AbstractLayout {

	private static final long serialVersionUID = -3972921029089528026L;

	protected Collection<ILayoutRow> rows = new ArrayList<>();
	protected ILayoutRow currentRow;
	
	@Override
	public void createRow() {
		rows.add(currentRow = new DefaultLayoutRow());
	}

	@Override
	public void add(IComponent<?> component) {
		currentRow.add(component);
	}

}
