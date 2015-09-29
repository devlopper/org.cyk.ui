package org.cyk.ui.api.config;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Deprecated
public class ClassConfiguration<CLASS> implements Serializable {

	private static final long serialVersionUID = 524804677149804204L;

	protected Class<? extends CLASS> clazz;
	protected String name;

	public ClassConfiguration(Class<? extends CLASS> clazz) {
		super();
		this.clazz = clazz;
	}
	 
}
