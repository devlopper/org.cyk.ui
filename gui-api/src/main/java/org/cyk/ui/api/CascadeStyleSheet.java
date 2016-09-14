package org.cyk.ui.api;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindTextResult;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;

@Getter @Setter
public class CascadeStyleSheet implements Serializable {

	private static final long serialVersionUID = 738142431416512052L;

	private static final String CLAZZ_SEPARATOR = Constant.CHARACTER_SPACE.toString();
	private static final String INLINE_SEPARATOR = Constant.CHARACTER_SEMI_COLON.toString();
	
	private String clazz=Constant.EMPTY_STRING,inline=Constant.EMPTY_STRING,uniqueClass=Constant.EMPTY_STRING;//TODO look for uniqueClass declare elsewhere and removed them to use this one instead
	
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
	
	public void setUniqueClass(String uniqueClass){
		removeClass(uniqueClass);
		this.uniqueClass = uniqueClass;
		addClass(this.uniqueClass);
	}
	
	/**/
	
	public static String generateClassFrom(String prefix,String label){
		return StringUtils.join(new String[]{StringUtils.lowerCase(StringUtils.defaultIfBlank(prefix, null)),normalise(label)},Constant.CHARACTER_UNDESCORE.toString());
	}
	
	public static String generateClassFrom(Class<? extends AbstractIdentifiable> identifiableClass,Object identifier){
		return generateClassFrom(generateClassFrom(IDENTIFIABLE_CLASS_PREFIX, identifiableClass.getSimpleName())
				, identifier == null ? Constant.EMPTY_STRING : identifier.toString());
	}
	public static String generateClassFrom(AbstractIdentifiable identifiable){
		return generateClassFrom(identifiable.getClass(), StringUtils.isBlank(identifiable.getCode()) ? identifiable.getIdentifier() : identifiable.getCode());
	}
	
	public static String generateUniqueClassFrom(AbstractIdentifiable identifiable){
		return StringUtils.join(new String[]{generateClassFrom(identifiable),String.valueOf(System.currentTimeMillis()),RandomStringUtils.randomAlphabetic(2)},Constant.CHARACTER_UNDESCORE.toString());
	}
	public static String generateUniqueClassFrom(String prefix,String label){
		return StringUtils.join(new String[]{generateClassFrom(prefix, label),String.valueOf(System.currentTimeMillis()),RandomStringUtils.randomAlphabetic(2)},Constant.CHARACTER_UNDESCORE.toString());
	}
	public static String generateUniqueClassFrom(UICommandable commandable,FindTextResult findTextResult){
		return generateUniqueClassFrom(COMMANDABLE_CLASS_PREFIX, findTextResult.getIdentifier());
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
	
	/**/
	@Override
	public String toString() {
		return "Class="+clazz+" , style="+inline;
	}
	/**/
	
	public static final String COMMANDABLE_CLASS_PREFIX = "commandable";
	public static final String COMMANDABLE_USER_ACCOUNT_MODULE_CLASS_PREFIX = "commandable_user_account";
	
	public static final String IDENTIFIABLE_CLASS_PREFIX = "identifiable";
	
	public static final String RESULTS_CONTAINER_CLASS_PREFIX = "results_container_";
	public static final String NOTIFICATION_DIALOG_OK_COMMANDABLE_CLASS = "messageDialogStyleClassOkButton";
	public static final String CONFIRMATION_DIALOG_YES_COMMANDABLE_CLASS = "ui-confirmdialog-yes";
	
	public static final String CONTEXTUAL_MENU_CLASS = "contextual_menu";
	public static final String GLOBAL_MENU_CLASS = "global_menu";
}
