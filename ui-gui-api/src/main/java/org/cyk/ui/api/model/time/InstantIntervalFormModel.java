package org.cyk.ui.api.model.time;

import java.io.Serializable;

import org.cyk.system.root.model.time.InstantInterval;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InstantIntervalFormModel implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	private InstantFormModel from = new InstantFormModel();
	private InstantFormModel to = new InstantFormModel();
	
	public void set(InstantInterval instantInterval){
		if(instantInterval==null)
			return;
		from.set(instantInterval.getFrom());
		to.set(instantInterval.getTo());
	}
	
	public void write(InstantInterval instantInterval){
		from.write(instantInterval.getFrom());
		to.write(instantInterval.getTo());
	}
	
	public static final String FIELD_FROM = "from";
	public static final String FIELD_TO = "to";
	
}
