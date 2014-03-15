package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.ui.api.component.output.UIOutputText;
import org.cyk.ui.web.api.form.input.WebUIOutputText;

public class OutputText extends AbstractWebOutputComponent<String> implements WebUIOutputText,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	public OutputText(UIOutputText anIOutput) {
		super(anIOutput);
	}
	
}
