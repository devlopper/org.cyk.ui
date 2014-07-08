package org.cyk.ui.web.primefaces;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.test.model.Details;
import org.cyk.utility.common.CommonUtils;

@Getter @Setter
public class Test {
	
	private Collection<Details> details;

	public static void main(String[] args) {
		Field field = CommonUtils.getInstance().getAllFields(Test.class).iterator().next();
		//System.out.println(field.getType() instanceof ParameterizedType);
		Type type = field.getGenericType();
		if(type instanceof ParameterizedType){
			System.out.println(((ParameterizedType)type).getActualTypeArguments()[0]);
		}
		
	}

}
