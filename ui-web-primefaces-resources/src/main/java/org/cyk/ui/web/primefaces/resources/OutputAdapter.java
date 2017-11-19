package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Image;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputFile;
import org.primefaces.model.ByteArrayContent;

public class OutputAdapter extends Output.Listener.Adapter.Default implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Object getReadableValueFile(Object value, String name, String extension, String mime, byte[] bytes) {
		return new ByteArrayContent(bytes, mime);
	}
	
	@Override
	protected void setPropertyValue(Output output, Object value) {
		super.setPropertyValue(output, value);
		if(output instanceof OutputFile)
			((Image)output.getPropertiesMap().getThumbnail()).getPropertiesMap().setValue(value);
	}
}