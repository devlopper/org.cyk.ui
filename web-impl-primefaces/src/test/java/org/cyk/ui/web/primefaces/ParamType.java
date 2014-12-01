package org.cyk.ui.web.primefaces;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.ui.web.primefaces.test.form.PersonSearchPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ParamType {
	
	
	public static void main(String[] args) {
		System.out.println("ParamType.main()");
		ToStringBuilder.reflectionToString(new PersonSearchPage(), ToStringStyle.MULTI_LINE_STYLE);
	}

}
