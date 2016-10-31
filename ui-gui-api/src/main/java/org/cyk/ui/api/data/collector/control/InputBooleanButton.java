package org.cyk.ui.api.data.collector.control;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Komenan.Christian
 *
 */
public interface InputBooleanButton<MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<Boolean,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM>{

	State getOnState();
	void setOnState(State state);
	
	State getOffState();
	void setOffState(State state);
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class State{
		private String label,icon;
	}
	
}
