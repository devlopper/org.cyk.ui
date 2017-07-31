package org.cyk.ui.api.model.time;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.time.InstantInterval;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Getter @Setter
public class InstantIntervalFormModel implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	@OutputSeperator(label=@Text(value="field.instant.from"))
	@IncludeInputs private InstantFormModel from = new InstantFormModel();
	
	@OutputSeperator(label=@Text(value="field.instant.to"))
	@IncludeInputs(label=@Text(value="field.instant.to")) private InstantFormModel to = new InstantFormModel();
	
	@Input @InputNumber	@Size(min=-1,max=Integer.MAX_VALUE) private Long distanceInMillisecond;
	
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
	public static final String FIELD_DISTANCE_IN_MILLISECOND = "distanceInMillisecond";
	
}
