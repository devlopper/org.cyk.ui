package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.model.AbstractApplicableValueQuestion;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractWebApplicableValueQuestion extends AbstractApplicableValueQuestion<SelectItem> implements Serializable {

	private static final long serialVersionUID = -1202692719243184369L;

	protected String update;
	
}
