package org.cyk.ui.api;

import org.cyk.system.root.business.api.validation.ValidationPolicy;

public interface UIWindowPart extends UIPart {
	
	ValidationPolicy getValidationPolicy();
	
	void setValidationPolicy(ValidationPolicy aValidationPolicy);
	
}
