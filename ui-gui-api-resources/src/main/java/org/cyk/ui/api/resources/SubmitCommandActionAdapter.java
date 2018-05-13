package org.cyk.ui.api.resources;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.container.form.Form;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class SubmitCommandActionAdapter extends Form.SubmitCommandActionAdapter implements Serializable{
	private static final long serialVersionUID = 1L;

}