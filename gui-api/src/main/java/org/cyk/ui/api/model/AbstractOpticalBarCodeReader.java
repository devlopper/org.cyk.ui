package org.cyk.ui.api.model;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.security.OpticalDecoderBusiness;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractOpticalBarCodeReader<TIMER extends AbstractTimer> extends AbstractBean implements TimerListener, Serializable {

	private static final long serialVersionUID = 9151837742103885528L;

	protected Collection<OpticalBarCodeReaderListener<TIMER>> opticalBarCodeReaderListeners = new ArrayList<>();
	
	protected Class<TIMER> timerClass;
	protected byte[] bytes;
	protected String string;
	protected String buttonLabel;
	protected Boolean found = Boolean.FALSE,blankStringIsNull = Boolean.TRUE,emptyStringIsNull=Boolean.TRUE;
	protected TIMER timer;
	protected Set<String> nullStrings = new HashSet<>();
	protected OpticalDecoderBusiness opticalDecoderBusiness;
	
	public AbstractOpticalBarCodeReader(OpticalDecoderBusiness opticalDecoderBusiness) {
		timerClass = timerClass();
		timer = newInstance(timerClass);
		timer.getTimerListeners().add(this);
		this.opticalDecoderBusiness = opticalDecoderBusiness;
		opticalBarCodeReaderListeners.add(new OpticalBarCodeReaderAdapter<TIMER>(){
			private static final long serialVersionUID = 1519936596082983556L;
			@Override
			public String decode(AbstractOpticalBarCodeReader<TIMER> reader,byte[] bytes) {
				return AbstractOpticalBarCodeReader.this.opticalDecoderBusiness.execute(bytes);
			}
			@Override
			public void notNullString(AbstractOpticalBarCodeReader<TIMER> reader, byte[] bytes,String value) {
				timer.getStopCommandable().getCommand().execute();
			}
		});
	}
	
	protected abstract void captureImage();
	
	@SuppressWarnings("unchecked")
	protected Class<TIMER> timerClass(){
	    return (Class<TIMER>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void onImageRead(byte[] bytes) {
		this.bytes = bytes;
	
		for(OpticalBarCodeReaderListener<TIMER> listener : opticalBarCodeReaderListeners){
			byte[] v = listener.read(this,this.bytes);
			if(v!=null)
				this.bytes = v;
		}
		
		for(OpticalBarCodeReaderListener<TIMER> listener : opticalBarCodeReaderListeners){
			String v = listener.decode(this,this.bytes);
			if(v!=null)
				string = v;
		}
		
		if( string == null
			|| (Boolean.TRUE.equals(emptyStringIsNull) && StringUtils.isEmpty(string)) 
			|| (Boolean.TRUE.equals(blankStringIsNull) && StringUtils.isBlank(string))
			|| nullStrings.contains(string)
			)
			for(OpticalBarCodeReaderListener<TIMER> listener : opticalBarCodeReaderListeners)
				listener.nullString(this,bytes,string);
		else
			for(OpticalBarCodeReaderListener<TIMER> listener : opticalBarCodeReaderListeners)
				listener.notNullString(this,bytes,string);
		
    }

	@Override
	public void started(AbstractTimer timer, Long timestamp) {
		string = null;
		bytes = null;
	}

	@Override
	public void timeout(AbstractTimer timer, Long timestamp) {
		if(Boolean.TRUE.equals(timer.getRunning()))
			captureImage();
	}

	@Override
	public void suspended(AbstractTimer timer, Long timestamp) {
		
	}

	@Override
	public void stopped(AbstractTimer timer, Long timestamp) {
		
	}

	@Override
	public void restarted(AbstractTimer timer, Long timestamp) {
		
	}
}
