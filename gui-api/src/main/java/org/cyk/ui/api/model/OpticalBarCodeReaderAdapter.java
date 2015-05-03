package org.cyk.ui.api.model;

import java.io.Serializable;

public class OpticalBarCodeReaderAdapter<TIMER extends AbstractTimer> implements OpticalBarCodeReaderListener<TIMER>,Serializable {

	private static final long serialVersionUID = 5264001146908869753L;
	
	@Override
	public byte[] read(AbstractOpticalBarCodeReader<TIMER> reader,byte[] bytes) {
		return bytes;
	}

	@Override
	public String decode(AbstractOpticalBarCodeReader<TIMER> reader,byte[] bytes) {
		return null;
	}

	@Override
	public void nullString(AbstractOpticalBarCodeReader<TIMER> reader,byte[] bytes,String value) {}

	@Override
	public void notNullString(AbstractOpticalBarCodeReader<TIMER> reader,byte[] bytes,String value) {}
	
	
}
