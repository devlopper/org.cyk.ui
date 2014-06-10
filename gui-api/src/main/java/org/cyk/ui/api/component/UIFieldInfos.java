package org.cyk.ui.api.component;

import java.io.Serializable;

import org.cyk.utility.common.annotation.UIField;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class UIFieldInfos implements Serializable {

	private static final long serialVersionUID = 4778829958574328116L;

	private UIField annotation;
	private Class<?> fieldType;
	
}
