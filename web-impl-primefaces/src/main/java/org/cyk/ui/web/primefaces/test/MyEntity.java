package org.cyk.ui.web.primefaces.test;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.annotation.UIField.OneRelationshipInputType;
import org.cyk.utility.common.validation.Client;
import org.cyk.utility.common.validation.System;

@Getter @Setter
public class MyEntity {
	
	public enum MyEnum{V1,V2,V3,v55}
	
	@UIField(groups=Client.class,required=true,description="Une chaine de caracteres")
	private String string="Jesus";
	
	@UIField(textArea=true,required=true,textRowCount=5,textColumnCount=60,description="Ceci est un texte multiligne")
	private String textArea="1";
	
	//@UIField(groups=System.class,required=true)
	private Boolean yesOrNoWrapper=false;
	
	//@UIField(groups=System.class)
	//private boolean yesOrNoPrimitive;
	
	//@UIField(required=true)
	private Integer myIntWrapper=1;
	
	//@UIField
	//private int myIntPrimitive=1;
	
	//@UIField
	private Float myFloatWrapper=1f;
	
	//@UIField
	//private float myFloatPrimitive=1f;
	
	//@UIField
	private BigDecimal myBigDecimal=new BigDecimal(1);
	
	//@UIField(groups=Client.class,required=true,description="Votre date de naissance par exemple : dd/mm/aaaa")
	private Date date = new Date();
	
	//@UIField(required=true)
	private MyEnum myEnum;
	
	@UIField(oneRelationshipInputType=OneRelationshipInputType.FORM)
	private MyDetails details1;// = new MyDetails();
	/*
	@UIField(compositionRelationshipInputType=CompositionRelationshipInputType.FORM)
	private MyDetails2 details2;
	*/
	/*
	@UIField
	private MyDetails details2;
	
	@UIField
	private MyDetails details3;
	
	@UIField
	private MyDetails details4;
	*/
	/**/
	@Setter @Getter
	public static class MyDetails{
		
		@UIField(required=true)
		private String detailsName;// = "Zouzoua Lingue";
		
		@UIField
		private Boolean detailsYesOrNo;
		
		@UIField
		private Integer detailsMyInt;
		
		@UIField(oneRelationshipInputType=OneRelationshipInputType.FORM)
		private MyDetails2 details2;
		
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
	}
	
	@Setter @Getter
	public static class MyDetails2{
		
		@UIField
		private Float detailsMyFloat;
		
		@UIField
		private BigDecimal detailsMyBigDecimal;
		
		@UIField
		private Date detailsDate;
		
		@UIField
		private MyEnum detailsMyEnum=MyEnum.V3;
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
	}

}
