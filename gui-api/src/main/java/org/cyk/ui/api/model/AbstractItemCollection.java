package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractItemCollection<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3543754685060813767L;

	protected String label;
	protected Collection<IDENTIFIABLE> initialIdentifiables = new ArrayList<>();
	protected Class<IDENTIFIABLE> identifiableClass;
	protected Class<TYPE> itemClass;
	protected List<TYPE> items = new ArrayList<>();
	protected Collection<ItemCollectionListener<TYPE,IDENTIFIABLE>> itemCollectionListeners = new ArrayList<>();
	protected Boolean deletable = Boolean.TRUE;
	protected UICommandable addCommandable,deleteCommandable;

	public AbstractItemCollection(Class<TYPE> itemClass,Class<IDENTIFIABLE> identifiableClass) {
		//itemClass = (Class<TYPE>) parameterizedClass(Object.class, 0); //(Class<TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		//itemClass = (Class<TYPE>) (Class<TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.itemClass = itemClass;
		this.identifiableClass = identifiableClass;
		label = UIManager.getInstance().getLanguageBusiness().findClassLabelText(itemClass); //textOfClass(itemClass);
		addCommandable = UIProvider.getInstance().createCommandable("command.add", IconType.ACTION_ADD);
		addCommandable.setShowLabel(Boolean.FALSE);
		addCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4786916980017894274L;
			@Override
			public void serve(UICommand command, Object parameter) {
				add();
			}
		});
		deleteCommandable = UIProvider.getInstance().createCommandable("command.delete", IconType.ACTION_REMOVE);
		deleteCommandable.setShowLabel(Boolean.FALSE);
		deleteCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4786916980017894274L;
			@SuppressWarnings("unchecked")
			@Override
			public void serve(UICommand command, Object parameter) {
				delete((TYPE) parameter);
			}
		});
	}
	
	public void add(IDENTIFIABLE identifiable){
		TYPE instance = newInstance(itemClass);
		instance.setIdentifiable(identifiable);
		for(ItemCollectionListener<TYPE,IDENTIFIABLE> listener : itemCollectionListeners)
			listener.instanciated(this,instance); 
		
		items.add(instance);
		
		if(instance.getForm()==null)
			;
		else{
			instance.getForm().setDynamic(Boolean.TRUE);
			instance.getForm().build();
		}
		for(ItemCollectionListener<TYPE,IDENTIFIABLE> listener : itemCollectionListeners)
			listener.add(this,instance);
		updateTable();
	}
	
	public void add(){
		add(newInstance(identifiableClass));
	}
	
	public void delete(TYPE item){
		items.remove(item);
		for(ItemCollectionListener<TYPE,IDENTIFIABLE> listener : itemCollectionListeners)
			listener.delete(this,item);
		updateTable();
	}
	
	protected abstract void updateTable();
	
	public Collection<IDENTIFIABLE> getIdentifiables(){
		Collection<IDENTIFIABLE> collection = new ArrayList<>();
		for(AbstractItemCollectionItem<IDENTIFIABLE> item : items){
			collection.add(item.getIdentifiable());
		}
		return collection;
	}

}
