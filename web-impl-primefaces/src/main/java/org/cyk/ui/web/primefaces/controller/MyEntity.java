package org.cyk.ui.web.primefaces.controller;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.annotation.FormField;
import org.cyk.ui.api.annotation.FormField.CompositionRelationshipInputType;
import org.cyk.utility.common.validation.Client;
import org.cyk.utility.common.validation.System;

@Getter @Setter
public class MyEntity {
	
	public enum MyEnum{V1,V2,V3,v55}
	
	@FormField(groups=Client.class)
	private String name="Jesus";
	
	@FormField(groups=System.class)
	private Boolean yesOrNo;
	
	@FormField
	private Integer myInt=5;
	
	@FormField
	private Float myFloat;
	
	@FormField
	private BigDecimal myBigDecimal;
	
	@FormField(groups=Client.class)
	private Date date=new Date();
	
	@FormField
	private MyEnum myEnum;
	
	@FormField(compositionRelationshipInputType=CompositionRelationshipInputType.FORM)
	private MyDetails details1;
	/*
	@FormField
	private MyDetails details2;
	
	@FormField
	private MyDetails details3;
	
	@FormField
	private MyDetails details4;
	*/
	/**/
	@Setter @Getter
	public static class MyDetails{
		
		@FormField
		private String detailsName = "Zouzoua Lingue";
		
		@FormField
		private Boolean detailsYesOrNo;
		
		@FormField
		private Integer detailsMyInt;
		
		@FormField
		private Float detailsMyFloat=123456f;
		
		@FormField
		private BigDecimal detailsMyBigDecimal;
		
		@FormField
		private Date detailsDate;
		
		@FormField
		private MyEnum detailsMyEnum=MyEnum.V3;
		
	}

}
