package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.DetailsClassLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.helper.IconHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.JavaScriptHelper;
import org.cyk.utility.common.helper.MarkupLanguageHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputCollection<T> extends AbstractCollection<T,Object>  implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	protected Boolean isLazyLoaded = Boolean.TRUE;
	protected Object lazyDataModel;
	
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<T> elementClass,Class<?> elementObjectClass
			,Class<IDENTIFIABLE> identifiableClass,String[] elementObjectClassFieldNames,Collection<IDENTIFIABLE> identifiables) {
		super(elementClass,elementObjectClass,null,null);
		
		MarkupLanguageHelper.Attributes.set(getRemoveCommand(), MarkupLanguageHelper.Attributes.FIELD_RENDERED, Boolean.FALSE);
		this.identifiableClass = identifiableClass;
		if(this.identifiableClass!=null){
			getCollection().setFieldsContainerClass(getCollection().getElementObjectClass());
			getCollection().setIsUpdatable(Boolean.FALSE);
			if(identifiables==null){
				if(isLazyLoaded==null)
					isLazyLoaded = Boolean.TRUE;
			}else{
				add(identifiables);	
			}
			
		}
		
		if(ArrayHelper.getInstance().isNotEmpty(elementObjectClassFieldNames)){
			addElementObjectFieldsAsColumns(elementObjectClassFieldNames);
			get__nameColumn__().setIsShowable(Boolean.FALSE);
		}
		
		((MarkupLanguageHelper.Attributes) (Object)getPropertiesMap()).setLazy(String.valueOf(Boolean.TRUE.equals(isLazyLoaded)));
		((MarkupLanguageHelper.Attributes) (Object)getPropertiesMap()).setPaginator(String.valueOf(Boolean.TRUE.equals(isLazyLoaded)));
		
		((MarkupLanguageHelper.Attributes) (Object)(((AbstractBean)getAddCommand()).getPropertiesMap())).setOnClick(new JavaScriptHelper.Script.Window.Navigate.Adapter
				.Default().setUniformResourceLocatorStringifier(Constant.Action.CREATE, identifiableClass).execute());
		
		((MarkupLanguageHelper.Attributes) (Object)(((AbstractBean)getAddCommand()).getPropertiesMap())).setType("button");
		getAddCommand().setNameRendered(Boolean.TRUE);
		
		get__commandsColumn__().setWidth("200");
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<T> elementClass,Class<IDENTIFIABLE> identifiableClass,String[] elementObjectClassFieldNames,Collection<IDENTIFIABLE> identifiables) {
		this(elementClass,inject(DetailsClassLocator.class).locate(identifiableClass),identifiableClass,elementObjectClassFieldNames,identifiables);
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection(Class<T> elementClass,String[] elementObjectClassFieldNames,Class<IDENTIFIABLE> identifiableClass) {
		this(elementClass,identifiableClass,elementObjectClassFieldNames,null);
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> OutputCollection<T> add(Collection<IDENTIFIABLE> identifiables){
		if(CollectionHelper.getInstance().isNotEmpty(identifiables)){
			Collection<Object> details = new ArrayList<>();
			for(AbstractIdentifiable identifiable : identifiables){
				details.add( ClassHelper.getInstance().instanciate(getCollection().getElementObjectClass(), new Object[]{identifiableClass,identifiable}) );
			}
			getCollection().setMany(details);
		}
		return this;
	}
	
	public Object getElements(){
		if(Boolean.TRUE.equals(getIsLazyLoaded())){
			if(lazyDataModel == null)
				lazyDataModel = instanciateLazyDataModel();
			return lazyDataModel;
		}
		return getCollection().getElements();
	}
	
	protected Object instanciateLazyDataModel(){
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
	/**/
	
	/**/
	
	public static class Element<T> extends AbstractCollection.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
	
		public Element() {
			readCommand = CommandHelper.getInstance().getCommand().setName(StringHelper.getInstance().get("grid.command.read", (Object[])null))
					.setIcon(IconHelper.Icon.ACTION_OPEN);
			getReadCommand().setIsImplemented(Boolean.TRUE);
			MarkupLanguageHelper.Attributes.set(getReadCommand(), MarkupLanguageHelper.Attributes.FIELD_RENDERED, Boolean.TRUE);
			((MarkupLanguageHelper.Attributes) (Object)(((AbstractBean)getReadCommand()).getPropertiesMap())).setType("button");
			getReadCommand().setNameRendered(Boolean.FALSE);
			
			updateCommand = CommandHelper.getInstance().getCommand().setName(StringHelper.getInstance().get("grid.command.update", (Object[])null))
					.setIcon(IconHelper.Icon.ACTION_EDIT);
			getUpdateCommand().setIsImplemented(Boolean.TRUE);
			MarkupLanguageHelper.Attributes.set(getUpdateCommand(), MarkupLanguageHelper.Attributes.FIELD_RENDERED, Boolean.TRUE);
			((MarkupLanguageHelper.Attributes) (Object)(((AbstractBean)getUpdateCommand()).getPropertiesMap())).setType("button");
			getUpdateCommand().setNameRendered(Boolean.FALSE);
			
			removeCommand = CommandHelper.getInstance().getCommand().setName(StringHelper.getInstance().get("grid.command.delete", (Object[])null))
					.setIcon(IconHelper.Icon.ACTION_DELETE);
			getRemoveCommand().setIsImplemented(Boolean.TRUE);
			MarkupLanguageHelper.Attributes.set(getRemoveCommand(), MarkupLanguageHelper.Attributes.FIELD_RENDERED, Boolean.TRUE);
			((MarkupLanguageHelper.Attributes) (Object)(((AbstractBean)getRemoveCommand()).getPropertiesMap())).setType("button");
			getRemoveCommand().setNameRendered(Boolean.FALSE);
		}
		
		@Override
		public org.cyk.utility.common.helper.CollectionHelper.Element<T> setObject(T object) {
			if(object!=null){
				((MarkupLanguageHelper.Attributes) (Object)(((AbstractBean)getReadCommand()).getPropertiesMap())).setOnClick(new JavaScriptHelper.Script.Window.Navigate.Adapter
						.Default().setUniformResourceLocatorStringifier(Constant.Action.CONSULT, ((AbstractOutputDetails<?>)object).getMaster() ).execute());
				
				((MarkupLanguageHelper.Attributes) (Object)(((AbstractBean)getUpdateCommand()).getPropertiesMap())).setOnClick(new JavaScriptHelper.Script.Window.Navigate.Adapter
						.Default().setUniformResourceLocatorStringifier(Constant.Action.UPDATE, ((AbstractOutputDetails<?>)object).getMaster() ).execute());
				
				((MarkupLanguageHelper.Attributes) (Object)(((AbstractBean)getRemoveCommand()).getPropertiesMap())).setOnClick(new JavaScriptHelper.Script.Window.Navigate.Adapter
						.Default().setUniformResourceLocatorStringifier(Constant.Action.DELETE, ((AbstractOutputDetails<?>)object).getMaster() ).execute());
			}
			return super.setObject(object);
		}
		
		@Override
		public String toString() {
			System.out.println("OutputCollection.Element.toString() : "+InstanceHelper.getInstance().getLabel(((AbstractOutputDetails<?>)getObject()).getMaster()));
			//if(getObject()!=null)
				return InstanceHelper.getInstance().getLabel(((AbstractOutputDetails<?>)getObject()).getMaster());
			//return super.toString();
		}
	}
	
	/**/
	
	public static interface Listener<T> {
		
		public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;

		}
		
	}
}
