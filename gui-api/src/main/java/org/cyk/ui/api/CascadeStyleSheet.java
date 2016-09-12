package org.cyk.ui.api;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindTextResult;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;

@Getter @Setter
public class CascadeStyleSheet implements Serializable {

	private static final long serialVersionUID = 738142431416512052L;

	private static final String CLAZZ_SEPARATOR = Constant.CHARACTER_SPACE.toString();
	private static final String INLINE_SEPARATOR = Constant.CHARACTER_SEMI_COLON.toString();
	
	private String clazz=Constant.EMPTY_STRING,inline=Constant.EMPTY_STRING;
	
	public CascadeStyleSheet addClasses(String...classes){
		clazz = CommonUtils.getInstance().concatenate(clazz, classes, CLAZZ_SEPARATOR);//TODO avoid same class add
		return this;
	}
	
	public CascadeStyleSheet addClass(String aClass){
		return addClasses(aClass);
	}
	
	public CascadeStyleSheet removeClasses(String...classes){
		//TODO remove class
		return this;
	}
	
	public CascadeStyleSheet removeClass(String aClass){
		return removeClasses(aClass);
	}
	
	public CascadeStyleSheet addInlines(String...inlines){
		inline = CommonUtils.getInstance().concatenate(inline, inlines, INLINE_SEPARATOR);//TODO avoid same instructions add
		return this;
	}
	
	public CascadeStyleSheet addInline(String inline){
		return addInlines(inline);
	}
	
	/**/
	
	public static String generateClassFrom(String prefix,String label){
		return StringUtils.join(new String[]{StringUtils.lowerCase(prefix),normalise(label)},Constant.CHARACTER_UNDESCORE.toString());
	}
	public static String generateUniqueClassFrom(String prefix,String label){
		return StringUtils.join(new String[]{generateClassFrom(prefix, label),String.valueOf(System.currentTimeMillis()),RandomStringUtils.randomAlphabetic(2)},Constant.CHARACTER_UNDESCORE.toString());
	}
	public static String generateUniqueClassFrom(UICommandable commandable,FindTextResult findTextResult){
		return generateUniqueClassFrom("commandable", findTextResult.getIdentifier());
	}
	public static String generateUniqueClassFrom(Input<?, ?, ?, ?, ?, ?> input,FindTextResult findTextResult){
		return generateUniqueClassFrom(input.getType(), findTextResult.getIdentifier());
	}
	public static String generateUniqueClassFrom(String prefix,FindTextResult findTextResult){
		return generateUniqueClassFrom(prefix, findTextResult.getIdentifier());
	}
	
	/**/
	
	public static String normalise(String value){
		value = StringUtils.replaceChars(value, "àéèôùç", "aeeouc");
		String separators = "-`' &#()[]{}|.";
		value = StringUtils.replaceChars(value, separators, StringUtils.repeat(Constant.CHARACTER_UNDESCORE.toString(), separators.length()));
		String charactersToDeleted = Constant.CHARACTER_SPACE.toString();
		value = StringUtils.lowerCase(StringUtils.replaceChars(value, charactersToDeleted, null));
		return value;
	}
}
