package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractApplicableValueQuestion<SELECT_ITEM> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 2369677859803157773L;

	protected Boolean rendered = Boolean.FALSE;
	protected String label = UIManager.getInstance().text("applicable");
	protected List<SELECT_ITEM> answers = new ArrayList<>();
	
}
