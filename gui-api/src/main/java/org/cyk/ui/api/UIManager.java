package org.cyk.ui.api;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Getter @Setter @Named(value="uiManager")
public class UIManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -9062523105492591265L;

	protected LoadDataMethod loadDataMethod;
	
	protected ToStringMethod toStringMethod;
	 
	@Inject protected LanguageBusiness languageBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		languageBusiness.registerResourceBundle("org.cyk.ui.api.resources.message",getClass().getClassLoader());
	}
	
	@SuppressWarnings("unchecked")
	public <T> Collection<T> collection(Class<T> aClass) {
		if(loadDataMethod==null)
			throw new IllegalArgumentException("No load data method has been provided.");
		return (Collection<T>) loadDataMethod.execute((Class<Object>) aClass);
	}
	
	public String toString(Object object){
		if(object==null)
			return "";
		if(toStringMethod==null)
			return ToStringBuilder.reflectionToString(object,ToStringStyle.SHORT_PREFIX_STYLE);
		return toStringMethod.execute(object);
	}
	
	public String text(String code){
		return languageBusiness.findText(code);
	}
	
	/**/
	
	
	
	/**/
	public static abstract class AbstractLoadDataMethod<T> extends AbstractMethod<Collection<T>, Class<T>> {
		private static final long serialVersionUID = 1175379361365502915L;
		
	}
	
	public static abstract class LoadDataMethod extends AbstractLoadDataMethod<Object>{
		private static final long serialVersionUID = 7640865186916095212L;
		
	}
	
	public static abstract class ToStringMethod extends AbstractMethod<String, Object> {
		private static final long serialVersionUID = 1175379361365502915L;
		
	}
	
}
