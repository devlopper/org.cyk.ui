package org.cyk.ui.web.api.resources.converter;
import java.io.Serializable;

import javax.faces.convert.Converter;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractConverter extends AbstractBean implements Converter,Serializable {
	private static final long serialVersionUID = -1L;
	
	protected static final String NULL_STRING_VALUE = Constant.EMPTY_STRING;
	
}