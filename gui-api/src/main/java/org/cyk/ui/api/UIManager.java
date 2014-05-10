package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.inject.spi.Bean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.Model;
import org.cyk.utility.common.annotation.Startup;
import org.cyk.utility.common.cdi.AbstractStartupBean;

@Singleton @Getter @Setter @Named(value="uiManager") @Startup
public class UIManager extends AbstractStartupBean implements Serializable {

	private static final long serialVersionUID = -9062523105492591265L;

	public static CollectionLoadMethod COLLECTION_LOAD_METHOD;
	public static ComponentCreateMethod COMPONENT_CREATE_METHOD ;
	public static ToStringMethod TO_STRING_METHOD = new ToStringMethod(){
		private static final long serialVersionUID = 5808352526362726091L;
		@Override
		protected String __execute__(Object object) {
			if(object==null)
				return "";
			return ToStringBuilder.reflectionToString(object,ToStringStyle.SHORT_PREFIX_STYLE);
		}
	};
	
	private Map<String,Class<?>> classKeyMap = new HashMap<>();
	//TODO move it to Business - Only business knows its parameters class
	private Collection<Class<?>> parametersClasses = new ArrayList<>(); 
	@Inject protected LanguageBusiness languageBusiness;
	private UIMenu menu = new DefaultMenu();
	private String windowParameter="windowParam";
	
	@Override
	protected void initialisation() {
		super.initialisation();
		languageBusiness.registerResourceBundle("org.cyk.ui.api.resources.message",getClass().getClassLoader());
		for(Bean<?> bean : startupBeanExtension.beans(Model.class)){
			registerClassKey(bean.getBeanClass());
			parametersClasses.add(bean.getBeanClass());
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> collection(Class<T> aClass){
		if(COLLECTION_LOAD_METHOD==null){
			System.out.println("No collection provider found");
			return null;
		}
		return (Collection<T>) COLLECTION_LOAD_METHOD.execute((Class<Object>) aClass);
	}
	
	public String toString(Object object){
		if(object==null)
			return "";
		if(TO_STRING_METHOD==null)
			return ToStringBuilder.reflectionToString(object,ToStringStyle.SHORT_PREFIX_STYLE);
		return TO_STRING_METHOD.execute(object);
	}
	
	public String text(String code){
		return languageBusiness.findText(code);
	}
	
	public void registerClassKey(Class<?>...theClasses){
		for(Class<?> aClass : theClasses){
			String key = aClass.getSimpleName();
			Class<?> clazz = classKeyMap.get(key);
			if(clazz==null)
				classKeyMap.put(key,aClass);
		}
	}
	
	public Class<?> classFromKey(String key){
		return classKeyMap.get(key);
	}
	
	public String keyFromClass(Class<?> aClass){
		for(Entry<String, Class<?>> entry : classKeyMap.entrySet())
			if(entry.getValue().equals(aClass))
				return entry.getKey();
		return null;
	}
	
	/**/
	
	
	
	/**/
	public static abstract class AbstractLoadDataMethod<T> extends AbstractMethod<Collection<T>, Class<T>> {
		private static final long serialVersionUID = 1175379361365502915L;
		
	}
	
	public static abstract class CollectionLoadMethod extends AbstractLoadDataMethod<Object>{
		private static final long serialVersionUID = 7640865186916095212L;
		
	}
	
	public static abstract class ToStringMethod extends AbstractMethod<String, Object> {
		private static final long serialVersionUID = 1175379361365502915L;
		
	}
	
	public static abstract class ComponentCreateMethod extends AbstractMethod<UIInputComponent<?>, UIInputComponent<?>> {

		private static final long serialVersionUID = 4855972832374849032L;
		
	}
	
}
