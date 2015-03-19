package org.cyk.ui.api;

import java.util.Collection;

public interface SelectItemBuildListener<TYPE> {

	String label(TYPE object);
	Boolean nullable();
	String nullLabel();
	Collection<TYPE> collection(Class<TYPE> aClass);
}
