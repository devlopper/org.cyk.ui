package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

@Getter @Setter
public abstract class AbstractItemCollection<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,SELECT_ITEM> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3543754685060813767L;

	protected String label,itemLabel;
	protected Collection<IDENTIFIABLE> initialIdentifiables = new ArrayList<>();
	protected Class<IDENTIFIABLE> identifiableClass;
	protected Class<TYPE> itemClass;
	protected List<TYPE> items = new ArrayList<>();
	protected Collection<Listener<TYPE,IDENTIFIABLE,SELECT_ITEM>> itemCollectionListeners = new ArrayList<>();
	protected Boolean autoWrite=Boolean.TRUE,autoApplyMasterFormFieldValues=Boolean.TRUE;
	protected AbstractApplicableValueQuestion<SELECT_ITEM> applicableValueQuestion;
	protected UICommandable addCommandable,deleteCommandable;
	protected Boolean showHeader=Boolean.TRUE,showFooter=Boolean.TRUE,showItemLabel=Boolean.FALSE,editable=Boolean.TRUE,showAddCommandableAtBottom=Boolean.TRUE;

	public AbstractItemCollection(Class<TYPE> itemClass,Class<IDENTIFIABLE> identifiableClass) {
		//itemClass = (Class<TYPE>) parameterizedClass(Object.class, 0); //(Class<TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		//itemClass = (Class<TYPE>) (Class<TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.itemClass = itemClass;
		this.identifiableClass = identifiableClass;
		label = UIManager.getInstance().getLanguageBusiness().findClassLabelText(itemClass); //textOfClass(itemClass);
		itemLabel = UIManager.getInstance().getLanguageBusiness().findClassLabelText(identifiableClass);
		addCommandable = Builder.instanciateOne().setLabelFromId("command.add").setIcon(Icon.ACTION_ADD).create();
		addCommandable.setShowLabel(Boolean.FALSE);
		addCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4786916980017894274L;
			@Override
			public void serve(UICommand command, Object parameter) {
				add();
			}
		});
		deleteCommandable = Builder.instanciateOne().setLabelFromId("command.delete").setIcon(Icon.ACTION_REMOVE).create();
		deleteCommandable.setShowLabel(Boolean.FALSE);
		deleteCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4786916980017894274L;
			@SuppressWarnings("unchecked")
			@Override
			public void serve(UICommand command, Object parameter) {
				delete((TYPE) parameter);
			}
		});
		
		applicableValueQuestion = createApplicableValueQuestion();
		
	}
	
	protected Boolean instanciatable(){
		return listenerUtils.getBoolean(itemCollectionListeners, new ListenerUtils.BooleanMethod<Listener<TYPE,IDENTIFIABLE,SELECT_ITEM>>() {
			@Override
			public Boolean execute(Listener<TYPE, IDENTIFIABLE, SELECT_ITEM> listener) {
				return listener.instanciatable(AbstractItemCollection.this);
			}
			@Override
			public Boolean getNullValue() {
				return Boolean.TRUE;
			}
		});
	}
	
	public void add(IDENTIFIABLE identifiable){
		TYPE instance = newInstance(itemClass);
		instance.setIdentifiable(identifiable);
		for(Listener<TYPE,IDENTIFIABLE,SELECT_ITEM> listener : itemCollectionListeners)
			listener.instanciated(this,instance); 
		setItemLabel(instance);
		
		items.add(instance);
		
		read(instance);
		
		if(instance.getForm()==null)
			;
		else{
			instance.getForm().setDynamic(Boolean.TRUE);
			instance.getForm().build();
		}
		for(Listener<TYPE,IDENTIFIABLE,SELECT_ITEM> listener : itemCollectionListeners)
			listener.add(this,instance);
		updateTable();		
	}
	
	public void add(){
		if(instanciatable())
			add(instanciate());
	}
	
	protected IDENTIFIABLE instanciate(){
		IDENTIFIABLE instance = null;
		for(Listener<TYPE,IDENTIFIABLE,SELECT_ITEM> listener : itemCollectionListeners){
			IDENTIFIABLE v = listener.instanciate(this);
			if(v!=null)
				instance = v;
		}
		if(instance==null)
			instance = newInstance(identifiableClass);
		return instance;
	}
	
	public void delete(TYPE item){
		items.remove(item);
		for(Listener<TYPE,IDENTIFIABLE,SELECT_ITEM> listener : itemCollectionListeners)
			listener.delete(this,item);
		updateTable();
	}

	public void setItemLabel(TYPE item){
		for(Listener<TYPE,IDENTIFIABLE,SELECT_ITEM> listener : itemCollectionListeners)
			listener.setLabel(this,item);
	}
	
	protected abstract void updateTable();
	
	public Collection<IDENTIFIABLE> getIdentifiables(){
		Collection<IDENTIFIABLE> collection = new ArrayList<>();
		for(AbstractItemCollectionItem<IDENTIFIABLE> item : items){
			if(Boolean.TRUE.equals(item.getApplicable()))
				collection.add(item.getIdentifiable());
		}
		return collection;
	}
	
	public void write(){
		for(Listener<TYPE,IDENTIFIABLE,SELECT_ITEM> listener : itemCollectionListeners)
			for(TYPE item : items)
				listener.write(item);
	}
	
	public void read(){
		for(TYPE item : items)
			read(item);
	}

	public void read(TYPE item){
		for(Listener<TYPE,IDENTIFIABLE,SELECT_ITEM> listener : itemCollectionListeners)
			listener.read(item);
	}
	
	protected AbstractApplicableValueQuestion<SELECT_ITEM> createApplicableValueQuestion(){
		return null;
	}
	
	/**/
	
	public static interface Listener<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,SELECT_ITEM> {
		
		Collection<IDENTIFIABLE> create();
		
		Collection<IDENTIFIABLE> load();
		
		Crud getCrud();
		
		void setCrud(Crud crud);
		
		Boolean instanciatable(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection);
		
		IDENTIFIABLE instanciate(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection);
		
		void instanciated(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item);
		
		void add(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item);
		
		void delete(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item);

		void setLabel(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item);
		
		/**
		 * Take value from item fields to identifiable fields
		 */
		void write(TYPE item);
		
		/**
		 * Take value from identifiable fields to item fields
		 */
		void read(TYPE item);
		
		Boolean isShowAddButton();
		
		/**/
		
		@Getter @Setter
		public static class Adapter<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,SELECT_ITEM> extends BeanAdapter implements Listener<TYPE,IDENTIFIABLE,SELECT_ITEM>,Serializable{
			private static final long serialVersionUID = 5920340778121618178L;

			private Crud crud;
			
			@Override
			public Collection<IDENTIFIABLE> create() {
				return new ArrayList<>();
			}
			@Override
			public Collection<IDENTIFIABLE> load() {
				return null;
			}
			@Override
			public Boolean instanciatable(AbstractItemCollection<TYPE, IDENTIFIABLE, SELECT_ITEM> itemCollection) {
				return null;
			}
			@Override
			public IDENTIFIABLE instanciate(AbstractItemCollection<TYPE, IDENTIFIABLE, SELECT_ITEM> itemCollection) {
				return null;
			}
			
			@Override public void add(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item) {}

			@Override public void delete(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection, TYPE item) {}

			@Override public void instanciated(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item) {}
		
			@Override public void setLabel(AbstractItemCollection<TYPE, IDENTIFIABLE, SELECT_ITEM> itemCollection,TYPE item) {}
			
			@Override public void write(TYPE item) {}
			
			@Override public void read(TYPE item) {}
			
			@Override
			public Boolean isShowAddButton() {
				return null;
			}
		}
		
		@Getter @Setter
		public static class Default<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,SELECT_ITEM> extends Adapter<TYPE,IDENTIFIABLE,SELECT_ITEM> implements Serializable{
			private static final long serialVersionUID = 5920340778121618178L;
			
			@Override
			public void setLabel(AbstractItemCollection<TYPE, IDENTIFIABLE, SELECT_ITEM> itemCollection,TYPE item) {
				super.setLabel(itemCollection, item);
				item.setLabel(inject(FormatterBusiness.class).format(item.getIdentifiable()));
			}
			
		}
	}


}