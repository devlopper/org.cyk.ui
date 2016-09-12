package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractInputChoice extends AbstractInput<Integer> implements Serializable {

	private static final long serialVersionUID = -3394592929247102811L;

	private String filter;
	
	public AbstractInputChoice(String fieldName,String filter, Integer value) {
		super(fieldName, value);
		this.filter = filter;
	}
	
	public void select(){
		
	}
	
	/**/
	
	public static class Default extends AbstractInputChoice implements Serializable {

		private static final long serialVersionUID = 5519338294670669750L;

		public Default(String fieldName, String filter,Integer value) {
			super(fieldName, filter,value);
		}
		
	}
}
