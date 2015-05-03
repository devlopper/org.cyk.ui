package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import org.cyk.system.root.business.api.security.OpticalDecoderBusiness;
import org.cyk.ui.api.model.AbstractOpticalBarCodeReader;
import org.cyk.ui.api.model.OpticalBarCodeReaderAdapter;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.Timer;
import org.omnifaces.util.Ajax;
import org.primefaces.event.CaptureEvent;

public class OpticalBarCodeReader extends AbstractOpticalBarCodeReader<Timer> implements Serializable {

	private static final long serialVersionUID = 3380295863829005994L;

	private String identifier,readerWigetVar="camera";
	
	public OpticalBarCodeReader(String identifier,OpticalDecoderBusiness opticalDecoder) {
		super(opticalDecoder);
		this.identifier = identifier;
		timer.setRunOnce(Boolean.FALSE);
		((Commandable)timer.getStartCommandable()).getButton().setProcess("@this");
		((Commandable)timer.getStartCommandable()).getButton().setUpdate(":form:"+this.identifier+":runningPanel");
				
		opticalBarCodeReaderListeners.add(new OpticalBarCodeReaderAdapter<Timer>(){
			private static final long serialVersionUID = 1519936596082983556L;
			@Override
			public void notNullString(AbstractOpticalBarCodeReader<Timer> reader, byte[] bytes,String value) {
				Ajax.update("form:"+OpticalBarCodeReader.this.identifier+":runningPanel");
			}
		});
	}
	
	public void onCapture(CaptureEvent captureEvent) {
    	onImageRead(captureEvent.getData());
    }

	@Override
	protected void captureImage() {
		Ajax.oncomplete("PF('"+readerWigetVar+"').capture();");
	}
	
}
