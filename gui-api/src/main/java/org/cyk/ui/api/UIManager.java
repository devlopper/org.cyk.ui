package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.api.editor.input.UIInputText;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.AbstractStartupBean;

@Singleton @Getter @Setter @Named(value="uiManager") @Deployment(initialisationType=InitialisationType.EAGER)
public class UIManager extends AbstractStartupBean implements Serializable {

	private static final long serialVersionUID = -9062523105492591265L;

	private static UIManager INSTANCE;
	
	public static UIManager getInstance() {
		return INSTANCE;
	}
	
	private CollectionLoadMethod collectionLoadMethod;
	private ComponentCreateMethod componentCreateMethod;
	private ToStringMethod toStringMethod;
	
	@Inject protected LanguageBusiness languageBusiness;
	@Inject protected BusinessManager businessManager;
	@Inject protected GenericBusiness genericBusiness;
	
	/* constants */
	private final String windowParameter="windowParam";
	private final Map<String,BusinessEntityInfos> entitiesRequestParameterIdMap = new HashMap<>();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		INSTANCE = this;
		languageBusiness.registerResourceBundle("org.cyk.ui.api.resources.message",getClass().getClassLoader());
		for(BusinessEntityInfos infos : businessManager.findEntitiesInfos(CrudStrategy.ENUMERATION)){
			registerClassKey(infos);
		}
				
		collectionLoadMethod = new CollectionLoadMethod() {
			private static final long serialVersionUID = -4679710339375267115L;
			@SuppressWarnings("unchecked")
			@Override
			protected Collection<Object> __execute__(Class<Object> parameter) {
				Class<AbstractIdentifiable> c = null;
				try {
					c = (Class<AbstractIdentifiable>) Class.forName(parameter.getName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				Collection<AbstractIdentifiable> r = genericBusiness.use(c).find().all();
				Collection<Object> l = new ArrayList<>();
				for(AbstractIdentifiable i : r)
					l.add(i);
				return l;
			}
		};
		
		toStringMethod = new ToStringMethod() {
			private static final long serialVersionUID = 7479681304478557922L;
			@Override
			protected String __execute__(Object parameter) {
				return parameter.toString();
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public <T> Collection<T> collection(Class<T> aClass){
		if(collectionLoadMethod==null){
			System.out.println("Data collection for Type <"+aClass.getSimpleName()+"> cannot be loaded");
			return null;
		}
		return (Collection<T>) collectionLoadMethod.execute((Class<Object>) aClass);
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
	
	public String text(Class<?> aClass){
		BusinessEntityInfos businessEntityInfos = businessEntityInfos(aClass);
		if(businessEntityInfos==null)
			return "###???###";
		return text(businessEntityInfos.getUiLabelId());
	}
	
	public void registerClassKey(BusinessEntityInfos...theClasses){
		for(BusinessEntityInfos infos : theClasses){
			BusinessEntityInfos clazz = entitiesRequestParameterIdMap.get(infos.getIdentifier());
			if(clazz==null)
				entitiesRequestParameterIdMap.put(infos.getIdentifier(),infos);
		}
	}
	
	public BusinessEntityInfos classFromKey(String key){
		return entitiesRequestParameterIdMap.get(key);
	}
	
	public String keyFromClass(BusinessEntityInfos aBusinessEntityInfos){
		for(Entry<String, BusinessEntityInfos> entry : entitiesRequestParameterIdMap.entrySet())
			if(entry.getValue().equals(aBusinessEntityInfos))
				return entry.getKey();
		return null;
	}
	
	public String keyFromClass(Class<?> aClass){
		for(Entry<String, BusinessEntityInfos> entry : entitiesRequestParameterIdMap.entrySet())
			if(entry.getValue().getClazz().equals(aClass))
				return entry.getKey();
		return null;
	}
	
	/**/
	
	private BusinessEntityInfos businessEntityInfos(Class<?> aClass){
		for(Entry<String, BusinessEntityInfos> entry : entitiesRequestParameterIdMap.entrySet())
			if(entry.getValue().getClazz().equals(aClass))
				return entry.getValue();
		//We can call the service for more
		return null;
	}
	
	public Boolean isInputText(UIInputComponent<?> inputComponent){
		return inputComponent instanceof UIInputText;
	}
	
	public Boolean isInputSelectOne(UIInputComponent<?> inputComponent){
		return inputComponent instanceof UIInputSelectOne;
	}
	
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
