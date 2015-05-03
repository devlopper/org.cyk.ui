package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.security.OpticalDecoderBusiness;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.page.security.OpticalBarCodeReader;
import org.primefaces.model.StreamedContent;

@Named @ViewScoped @Getter @Setter
public class CameraTestPage extends AbstractPrimefacesPage implements Serializable {
      
	private static final long serialVersionUID = 1659264103742702189L;
	
	@Inject private OpticalDecoderBusiness opticalDecoder;
	
	private OpticalBarCodeReader opticalBarCodeReader;
    
    private StreamedContent image;
	
    @Override
    protected void initialisation() {
    	super.initialisation();
    	opticalBarCodeReader = new OpticalBarCodeReader("obcr",opticalDecoder);
    	
    	/*
    	opticalBarCodeReader.getOpticalBarCodeReaderListeners().add(new OpticalBarCodeReaderAdapter<Timer>(){
			private static final long serialVersionUID = 2249331170611789871L;
			
			@Override
			public void nullString(AbstractOpticalBarCodeReader<Timer> reader,byte[] bytes, String value) {
				try {
					IOUtils.write(bytes, new FileOutputStream("H:/barcode/taken/null/NULL_"+System.currentTimeMillis()+".png"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void notNullString(AbstractOpticalBarCodeReader<Timer> reader,byte[] bytes, String value) {
				Ajax.oncomplete("beepBarCodeReaderReadNotNull();");
				try {
					IOUtils.write(bytes, new FileOutputStream("H:/barcode/taken/notnull/NULL_"+System.currentTimeMillis()+".png"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
    	});
    	*/
    }
    
    
    
}