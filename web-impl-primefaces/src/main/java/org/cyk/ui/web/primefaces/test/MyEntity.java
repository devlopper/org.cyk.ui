package org.cyk.ui.web.primefaces.test;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.annotation.FormField;
import org.cyk.utility.common.annotation.FormField.OneRelationshipInputType;
import org.cyk.utility.common.validation.Client;
import org.cyk.utility.common.validation.System;

@Getter @Setter
public class MyEntity {
	
	public enum MyEnum{V1,V2,V3,v55}
	
	@FormField(groups=Client.class,required=true,description="Une chaine de caracteres")
	private String string="Jesus";
	
	@FormField(textArea=true,required=true,textRowCount=5,textColumnCount=60,description="Ceci est un texte multiligne")
	private String textArea="1";
	
	@FormField(groups=System.class,required=true)
	private Boolean yesOrNoWrapper=false;
	
	//@FormField(groups=System.class)
	//private boolean yesOrNoPrimitive;
	
	@FormField(required=true)
	private Integer myIntWrapper=1;
	
	//@FormField
	//private int myIntPrimitive=1;
	
	@FormField
	private Float myFloatWrapper=1f;
	
	//@FormField
	//private float myFloatPrimitive=1f;
	
	@FormField
	private BigDecimal myBigDecimal=new BigDecimal(1);
	
	@FormField(groups=Client.class,required=true,description="Votre date de naissance par exemple : dd/mm/aaaa")
	private Date date = new Date();
	
	@FormField(required=true)
	private MyEnum myEnum;
	
	@FormField(oneRelationshipInputType=OneRelationshipInputType.FORM)
	private MyDetails details1;// = new MyDetails();
	/*
	@FormField(compositionRelationshipInputType=CompositionRelationshipInputType.FORM)
	private MyDetails2 details2;
	*/
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
		
		@FormField(required=true)
		private String detailsName;// = "Zouzoua Lingue";
		
		@FormField
		private Boolean detailsYesOrNo;
		
		@FormField
		private Integer detailsMyInt;
		
		@FormField(oneRelationshipInputType=OneRelationshipInputType.FORM)
		private MyDetails2 details2;
		
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
	}
	
	@Setter @Getter
	public static class MyDetails2{
		
		@FormField
		private Float detailsMyFloat;
		
		@FormField
		private BigDecimal detailsMyBigDecimal;
		
		@FormField
		private Date detailsDate;
		
		@FormField
		private MyEnum detailsMyEnum=MyEnum.V3;
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
	}

}
