package org.cyk.ui.api.layout;

import java.io.Serializable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GridLayout extends AbstractLayout implements Serializable {

	private static final long serialVersionUID = -5272418768568215302L;

	public GridLayout(Integer columnsCount){
		super(columnsCount);
	}

}
