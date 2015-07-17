package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractItemCollection<TYPE> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3543754685060813767L;

	protected String label;
	protected Class<TYPE> itemClass;
	protected List<TYPE> items = new ArrayList<>();
	protected Collection<ItemCollectionListener<TYPE>> itemCollectionListeners = new ArrayList<>();
	protected Boolean deletable = Boolean.TRUE;
	protected UICommandable addCommandable,deleteCommandable;

	public AbstractItemCollection(Class<TYPE> itemClass) {
		//itemClass = (Class<TYPE>) parameterizedClass(Object.class, 0); //(Class<TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		//itemClass = (Class<TYPE>) (Class<TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.itemClass = itemClass;
		itemCollectionListeners.add(new DefaultItemCollectionAdapter<TYPE>());
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
	
	public void add(){
		for(ItemCollectionListener<TYPE> listener : itemCollectionListeners)
			listener.add(this);
		updateTable();
	}
	
	public void delete(TYPE item){
		for(ItemCollectionListener<TYPE> listener : itemCollectionListeners)
			listener.delete(this,item);
		updateTable();
	}
	
	protected abstract void updateTable();
	
	/**/
	
	public static class DefaultItemCollectionAdapter<TYPE> extends AbstractBean implements ItemCollectionListener<TYPE>{

		private static final long serialVersionUID = 5920340778121618178L;

		@Override
		public void add(AbstractItemCollection<TYPE> itemCollection) {
			itemCollection.items.add(newInstance(itemCollection.itemClass));
		}

		@Override
		public void delete(AbstractItemCollection<TYPE> itemCollection, TYPE item) {
			itemCollection.items.remove(item);
		}
		
	}
	
}
