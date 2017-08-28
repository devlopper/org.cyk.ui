package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.MethodHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractItemCollection<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable,SELECT_ITEM> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3543754685060813767L;

	protected String label,itemLabel;
	protected Collection<IDENTIFIABLE> initialIdentifiables = new ArrayList<>();
	protected Class<IDENTIFIABLE> identifiableClass;
	protected Class<COLLECTION> collectionClass;
	
	protected InputChoice<?, ?, ?, ?, ?, SELECT_ITEM> inputChoice;
	protected Class<TYPE> itemClass;
	protected List<TYPE> items = new ArrayList<>();
	protected Collection<Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM>> itemCollectionListeners = new ArrayList<>();
	protected Boolean autoWrite=Boolean.TRUE,autoApplyMasterFormFieldValues=Boolean.TRUE;
	protected AbstractApplicableValueQuestion<SELECT_ITEM> applicableValueQuestion;
	protected UICommandable addCommandable,deleteCommandable;
	protected Boolean showHeader=Boolean.TRUE,showFooter=Boolean.TRUE,showItemLabel=Boolean.FALSE,editable=Boolean.TRUE,showAddCommandableAtBottom=Boolean.TRUE
			,isInputChoiceUnique=Boolean.TRUE;
	
	protected FormOneData<?, ?, ?, ?, ?, ?> containerForm;
	protected COLLECTION collection;
	protected IDENTIFIABLE oneSelected;
	//protected AbstractIdentifiable oneMasterSelected;
	protected Crud crud;
	
	@SuppressWarnings("unchecked")
	public AbstractItemCollection(Class<TYPE> itemClass,Class<IDENTIFIABLE> identifiableClass,COLLECTION collection,Crud crud) {
		//itemClass = (Class<TYPE>) parameterizedClass(Object.class, 0); //(Class<TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		//itemClass = (Class<TYPE>) (Class<TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.itemClass = itemClass;
		this.identifiableClass = identifiableClass;
		this.collection = collection;
		this.crud = crud;
		if(this.collection!=null)
			this.collectionClass = (Class<COLLECTION>) this.collection.getClass();
		label = UIManager.getInstance().getLanguageBusiness().findClassLabelText(itemClass); //textOfClass(itemClass);
		itemLabel = UIManager.getInstance().getLanguageBusiness().findClassLabelText(identifiableClass);
		addCommandable = Builder.instanciateOne().setLabelFromId("command.add").setIcon(Icon.ACTION_ADD).create();
		addCommandable.setShowLabel(Boolean.FALSE);
		addCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4786916980017894274L;
			@Override
			public void serve(UICommand command, Object parameter) {
				if(inputChoice==null || inputChoice.getValue()!=null)
					add();
			}
		});
		deleteCommandable = Builder.instanciateOne().setLabelFromId("command.delete").setIcon(Icon.ACTION_REMOVE).create();
		deleteCommandable.setShowLabel(Boolean.FALSE);
		deleteCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4786916980017894274L;
			@Override
			public void serve(UICommand command, Object parameter) {
				delete((TYPE) parameter);
			}
		});
		
		applicableValueQuestion = createApplicableValueQuestion();
		
	}
	
	protected Boolean instanciatable(){
		return listenerUtils.getBoolean(itemCollectionListeners, new ListenerUtils.BooleanMethod<Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM>>() {
			@Override
			public Boolean execute(Listener<TYPE, IDENTIFIABLE,COLLECTION, SELECT_ITEM> listener) {
				return listener.instanciatable(AbstractItemCollection.this);
			}
			@Override
			public Boolean getNullValue() {
				return Boolean.TRUE;
			}
		});
	}
	
	public void add(IDENTIFIABLE identifiable,Object master){
		TYPE instance = newInstance(itemClass);
		instance.setIdentifiable(identifiable);
		instance.setMaster(master);
		for(Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> listener : itemCollectionListeners)
			listener.instanciated(this,instance); 
		setItemLabel(instance);
		
		if(items.add(instance)){
			if(Crud.isCreateOrUpdate(crud)){
				if(inputChoice!=null && (isInputChoiceUnique==null || Boolean.TRUE.equals(isInputChoiceUnique)))
					inputChoice.removeChoice(master);
			}
		}
		
		read(instance);
		
		if(instance.getForm()==null)
			;
		else{
			instance.getForm().setDynamic(Boolean.TRUE);
			instance.getForm().build();
		}
		for(Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> listener : itemCollectionListeners)
			listener.add(this,instance);
		updateTable();		
	}
	
	public void add(IDENTIFIABLE identifiable){
		add(identifiable,inputChoice==null ? null : inputChoice.getValue());
	}
	
	public void add(){
		if(instanciatable())
			add(instanciate());
	}
	
	protected IDENTIFIABLE instanciate(){
		IDENTIFIABLE instance = null;
		for(Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> listener : itemCollectionListeners){
			IDENTIFIABLE v = listener.instanciate(this);
			if(v!=null)
				instance = v;
		}
		if(instance==null)
			instance = newInstance(identifiableClass);
		return instance;
	}
	
	@Deprecated
	protected abstract SELECT_ITEM createSelectItem(AbstractIdentifiable identifiable);
	@Deprecated
	protected abstract AbstractIdentifiable getIdentifiableFromChoice(SELECT_ITEM choice);
	
	protected Object getMasterSelected(IDENTIFIABLE identifiable){
		Object instance = null;
		for(Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> listener : itemCollectionListeners){
			Object v = listener.getMasterSelected(this,identifiable);
			if(v!=null)
				instance = v;
		}
		return instance;
	}
	
	public void delete(TYPE item){
		if(items.remove(item)){
			if(inputChoice!=null)
				inputChoice.addChoiceIfAutomaticallyRemoveSelected(item.getMaster());
		}
		for(Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> listener : itemCollectionListeners)
			listener.delete(this,item);
		updateTable();
	}

	public void setItemLabel(TYPE item){
		for(Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> listener : itemCollectionListeners)
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
		for(Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> listener : itemCollectionListeners) {
			for(TYPE item : items){
				listener.write(item);
			}
			IdentifiableRuntimeCollection<IDENTIFIABLE> identifiableRuntimeCollection = listener.getRuntimeCollection();
			if(identifiableRuntimeCollection!=null && identifiableRuntimeCollection.isSynchonizationEnabled())
				identifiableRuntimeCollection.setCollection(getIdentifiables());
		}
		
		
	}
	
	public void read(){
		for(TYPE item : items)
			read(item);
	}

	public void read(TYPE item){
		for(Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> listener : itemCollectionListeners)
			listener.read(item);
	}
	
	protected AbstractApplicableValueQuestion<SELECT_ITEM> createApplicableValueQuestion(){
		return null;
	}
	
	/**/
	
	public static interface Listener<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable,SELECT_ITEM> {
		
		IdentifiableRuntimeCollection<IDENTIFIABLE> getRuntimeCollection();
		
		Collection<IDENTIFIABLE> create();
		
		Collection<IDENTIFIABLE> findByCollection(COLLECTION collection);
		
		Collection<IDENTIFIABLE> load();
		
		Crud getCrud();
		void setCrud(Crud crud);
		
		COLLECTION getCollection();
		void setCollection(COLLECTION collection);
		
		Boolean instanciatable(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> itemCollection);
		
		IDENTIFIABLE instanciate(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> itemCollection);
		
		void instanciated(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> itemCollection,TYPE item);
		
		void add(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> itemCollection,TYPE item);
		
		void delete(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> itemCollection,TYPE item);

		void setLabel(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> itemCollection,TYPE item);
		
		Object getMasterSelected(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> itemCollection,IDENTIFIABLE identifiable);
		
		/**
		 * Take value from item fields to identifiable fields
		 */
		void write(TYPE item);
		
		/**
		 * Take value from identifiable fields to item fields
		 */
		void read(TYPE item);
		
		Boolean isShowAddButton();
		Boolean getIsInputChoiceUnique();
		
		FormOneData<AbstractIdentifiable, ?, ?, ?, ?, ?> getForm();
		void setForm(FormOneData<AbstractIdentifiable, ?, ?, ?, ?, ?> form);
		
		InputChoice<AbstractIdentifiable, ?, ?, ?, ?, ?> getInputChoice();
		void setInputChoice(InputChoice<AbstractIdentifiable, ?, ?, ?, ?, ?> inputChoice);
		
		Class<IDENTIFIABLE> getIdentifiableClass();
		void setIdentifiableClass(Class<IDENTIFIABLE> identifiableClass);
		
		String getFieldOneItemMasterSelectedName();
		
		/**/
		
		@Getter @Setter
		public static class Adapter<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable,SELECT_ITEM> extends BeanAdapter implements Listener<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM>,Serializable{
			private static final long serialVersionUID = 5920340778121618178L;

			protected COLLECTION collection;
			protected Crud crud;
			protected FormOneData<AbstractIdentifiable, ?, ?, ?, ?, ?> form;
			protected InputChoice<AbstractIdentifiable, ?, ?, ?, ?, ?> inputChoice;
			protected Class<IDENTIFIABLE> identifiableClass;
			protected Boolean isInputChoiceUnique;
			
			public Adapter(COLLECTION collection, Crud crud,FormOneData<AbstractIdentifiable, ?, ?, ?, ?, ?> form
					,InputChoice<AbstractIdentifiable, ?, ?, ?, ?, ?> inputChoice,Class<IDENTIFIABLE> identifiableClass) {
				super();
				this.collection = collection;
				this.crud = crud;
				this.form = form;
				this.inputChoice = inputChoice;
				this.identifiableClass = identifiableClass;
			}
			
			@SuppressWarnings("unchecked")
			public Adapter(COLLECTION collection, Crud crud,FormOneData<AbstractIdentifiable, ?, ?, ?, ?, ?> form,Class<IDENTIFIABLE> identifiableClass) {
				super();
				this.collection = collection;
				this.crud = crud;
				this.form = form;
				this.identifiableClass = identifiableClass;
				if(StringUtils.isNotBlank(getFieldOneItemMasterSelectedName())){
					this.inputChoice = (InputChoice<AbstractIdentifiable, ?, ?, ?, ?, ?>) form.getInputByFieldName(getFieldOneItemMasterSelectedName());
					if(this.inputChoice != null){
						
					}
					//System.out.println(form.get getFormDatas().peek().getData().getClass());
					//for(Control control : form.getFormDatas().peek().getControlSets().iterator().next().getControls())
					//	if(control instanceof Input)
							//;//System.out.println( ((Input)control).getField() );
				}
			}
			
			@Override
			public Collection<IDENTIFIABLE> create() {
				return new ArrayList<>();
			}
			@Override
			public Collection<IDENTIFIABLE> load() {
				return null;
			}
			@Override
			public Boolean instanciatable(AbstractItemCollection<TYPE, IDENTIFIABLE,COLLECTION, SELECT_ITEM> itemCollection) {
				return null;
			}
			@Override
			public IDENTIFIABLE instanciate(AbstractItemCollection<TYPE, IDENTIFIABLE,COLLECTION, SELECT_ITEM> itemCollection) {
				return null;
			}
			
			@Override public void add(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> itemCollection,TYPE item) {}

			@Override public void delete(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> itemCollection, TYPE item) {}

			@Override public void instanciated(AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> itemCollection,TYPE item) {}
		
			@Override public void setLabel(AbstractItemCollection<TYPE, IDENTIFIABLE,COLLECTION, SELECT_ITEM> itemCollection,TYPE item) {}
			
			@Override public void write(TYPE item) {}
			
			@Override public void read(TYPE item) {}
			
			@Override
			public Object getMasterSelected(AbstractItemCollection<TYPE, IDENTIFIABLE, COLLECTION, SELECT_ITEM> itemCollection,IDENTIFIABLE identifiable) {
				return null;
			}
			
			@Override
			public Boolean isShowAddButton() {
				return null;
			}
			
			@Override
			public IdentifiableRuntimeCollection<IDENTIFIABLE> getRuntimeCollection() {
				return null;
			}
			
			@Override
			public Collection<IDENTIFIABLE> findByCollection(COLLECTION collection) {
				return null;
			}
			
			@Override
			public String getFieldOneItemMasterSelectedName() {
				return null;
			}
		}
		
		@Getter @Setter
		public static class Default<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable,SELECT_ITEM> extends Adapter<TYPE,IDENTIFIABLE,COLLECTION,SELECT_ITEM> implements Serializable{
			private static final long serialVersionUID = 5920340778121618178L;
			
			public Default(COLLECTION collection, Crud crud,FormOneData<AbstractIdentifiable, ?, ?, ?, ?, ?> form
					,InputChoice<AbstractIdentifiable, ?, ?, ?, ?, ?> inputChoice,Class<IDENTIFIABLE> identifiableClass) {
				super(collection,crud,form,inputChoice,identifiableClass);
			}
			
			public Default(COLLECTION collection, Crud crud,FormOneData<AbstractIdentifiable, ?, ?, ?, ?, ?> form,Class<IDENTIFIABLE> identifiableClass) {
				super(collection,crud,form,identifiableClass);
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public IdentifiableRuntimeCollection<IDENTIFIABLE> getRuntimeCollection() {
				String methodName = MethodHelper.getInstance().getFirstExist(getCollection().getClass(), new String[]{
						MethodHelper.getInstance().getName(MethodHelper.Method.Type.GET, ClassHelper.getInstance().getVariableName(getIdentifiableClass(),Boolean.TRUE))
						,getInputChoice() == null ? null : MethodHelper.getInstance().getName(MethodHelper.Method.Type.GET,ClassHelper.getInstance().getVariableName(getInputChoice().getField().getType(),Boolean.TRUE))});
				return MethodHelper.getInstance().call(getCollection(), IdentifiableRuntimeCollection.class, methodName ).setSynchonizationEnabled(Boolean.TRUE); 
			}
			
			@Override
			public Object getMasterSelected(AbstractItemCollection<TYPE, IDENTIFIABLE, COLLECTION, SELECT_ITEM> itemCollection,
					IDENTIFIABLE identifiable) {
				if(getInputChoice()==null)
					return null;
				return (AbstractIdentifiable) MethodHelper.getInstance().callGet(identifiable, inputChoice.getField().getType(), inputChoice.getField().getType().getSimpleName());
			}
			
			@Override
			public void setLabel(AbstractItemCollection<TYPE, IDENTIFIABLE,COLLECTION, SELECT_ITEM> itemCollection,TYPE item) {
				super.setLabel(itemCollection, item);
				if(getInputChoice()==null)
					return;
				item.setLabel(inject(FormatterBusiness.class).format( getMasterSelected(itemCollection, item.getIdentifiable()) ));
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public IDENTIFIABLE instanciate(AbstractItemCollection<TYPE, IDENTIFIABLE, COLLECTION, SELECT_ITEM> itemCollection) {
				if(getInputChoice()==null)
					return null;
				Class<AbstractIdentifiable> businessClass = (Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(getIdentifiableClass());
				TypedBusiness<AbstractIdentifiable> business = (TypedBusiness<AbstractIdentifiable>) inject(BusinessInterfaceLocator.class).injectTyped(businessClass);
				Object value = getInputChoice().getValue();
				return (IDENTIFIABLE) MethodHelper.getInstance().call(business,businessClass, "instanciateOne"
						, new MethodHelper.Method.Parameter(getCollection().getClass(),getCollection())
						, new MethodHelper.Method.Parameter(getInputChoice().getField().getType(),value) 
						); 
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Collection<IDENTIFIABLE> findByCollection(COLLECTION collection) {
				Class<AbstractIdentifiable> businessClass = (Class<AbstractIdentifiable>) ClassHelper.getInstance().getByName(getIdentifiableClass());
				TypedBusiness<AbstractIdentifiable> business = (TypedBusiness<AbstractIdentifiable>) inject(BusinessInterfaceLocator.class).injectTyped(businessClass);
				return MethodHelper.getInstance().call(business,Collection.class, "findBy"+collection.getClass().getSimpleName()
						, MethodHelper.Method.Parameter.buildArray(collection.getClass(),collection)); 
			}
			
			@Override
			public Collection<IDENTIFIABLE> create() {
				IdentifiableRuntimeCollection<IDENTIFIABLE> runtimeCollection = getRuntimeCollection();
				if(runtimeCollection==null)
					return super.create();
				Collection<IDENTIFIABLE> identifiables = runtimeCollection.getCollection();
				if(identifiables==null)
					return super.load();
				return identifiables;
			}
			
			@Override
			public Collection<IDENTIFIABLE> load() {
				IdentifiableRuntimeCollection<IDENTIFIABLE> runtimeCollection = getRuntimeCollection();
				if(runtimeCollection==null)
					return super.load();
				Collection<IDENTIFIABLE> identifiables = findByCollection(getCollection());
				if(identifiables==null)
					return super.load();
				runtimeCollection.setCollection(identifiables);
				return runtimeCollection.getCollection();
			}
			
			@Override
			public Boolean isShowAddButton() {
				IdentifiableRuntimeCollection<IDENTIFIABLE> runtimeCollection = getRuntimeCollection();
				if(runtimeCollection==null)
					return super.isShowAddButton();
				return Boolean.TRUE.equals(runtimeCollection.isSynchonizationEnabled());
			}
			
			@Override
			public void write(TYPE item) {
				item.write();
			}
			
		}
	}


}
