package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.ui.api.data.collector.control.InputCollection;
import org.cyk.ui.web.primefaces.CommandHelper.Command;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CollectionHelper.Instance;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Named
@ViewScoped
@Getter
@Setter
public class InputCollectionDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private InputCollection<Item> inputCollection1 = new InputCollection<>(Item.class);
	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		inputCollection1.getCollection().addOne(new Item().setName("One"));
		inputCollection1.getCollection().addOne(new Item().setName("Three"));
		inputCollection1.getCollection().addOne(new Item().setName("Another one"));
		inputCollection1.getAddCommand().setProperty(Command.COMMAND_PROPERTY_NAME_UPDATE, "@(.ui-datatable)");
		
		inputCollection1.getCollection().addListener(new CollectionHelper.Instance.Listener.Adapter<Item>(){
			private static final long serialVersionUID = 1L;
			@Override
			public void addOne(Instance<Item> instance, Item element) {
				element.setName("Another element "+System.currentTimeMillis());
			}
		});
		
		inputCollection1.getDeleteCommand().setProperty(Command.COMMAND_PROPERTY_NAME_UPDATE, "@(.ui-datatable)");
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Item extends InputCollection.Element<Object> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Item setName(String name) {
			return (Item) super.setName(name);
		}
		
	}
}
