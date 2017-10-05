package org.cyk.ui.api.model.geography;

import java.io.Serializable;
import java.math.BigDecimal;

import org.cyk.system.root.model.geography.GlobalPosition;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GlobalPositionFormModel implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	@Input @InputNumber private BigDecimal longitude;
	@Input @InputNumber private BigDecimal latitude;
	@Input @InputNumber private BigDecimal altitude;
	
	private SetListener setListener;
	private WriteListener writeListener;
	
	public void set(GlobalPosition globalPosition,SetListener listener){
		if(globalPosition==null)
			return;
		longitude = globalPosition.getLongitude();
		latitude = globalPosition.getLatitude();
		altitude = globalPosition.getAltitude();
	}
	
	public void set(GlobalPosition globalPosition){
		set(globalPosition,setListener);
	}
	
	public void write(GlobalPosition globalPosition,WriteListener listener){
		if(globalPosition==null)
			return;
		globalPosition.setLongitude(longitude);
		globalPosition.setLatitude(latitude);
		globalPosition.setAltitude(altitude);
	}
	
	public void write(GlobalPosition globalPosition){
		write(globalPosition, writeListener);
	}
	
	/**/
	
	public static interface SetListener {
		
	}
	
	public static interface WriteListener {
		
	}
	
	/**/
	
	public static final String FIELD_LONGITUDE = "longitude";
	public static final String FIELD_LATITUDE = "latitude";
	public static final String FIELD_ALTITUDE = "altitude";
	
}
