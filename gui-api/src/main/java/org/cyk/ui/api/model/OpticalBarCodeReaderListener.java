package org.cyk.ui.api.model;

public interface OpticalBarCodeReaderListener<TIMER extends AbstractTimer> {

	byte[] read(AbstractOpticalBarCodeReader<TIMER> reader,byte[] bytes);
	
	String decode(AbstractOpticalBarCodeReader<TIMER> reader,byte[] bytes);
	
	void nullString(AbstractOpticalBarCodeReader<TIMER> reader,byte[] bytes,String value);
	
	void notNullString(AbstractOpticalBarCodeReader<TIMER> reader,byte[] bytes,String value);
}
