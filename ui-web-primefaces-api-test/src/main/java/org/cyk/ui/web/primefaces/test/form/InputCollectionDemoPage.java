package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.primefaces.data.collector.control.InputCollection;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CollectionHelper.Instance;
import org.cyk.utility.common.helper.InstanceHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named
@ViewScoped
@Getter
@Setter
public class InputCollectionDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private FormOneData<Form> form;
	private InputCollection<Child> inputCollection1 = new InputCollection<>(Child.class);
	private InputCollection<Child> inputCollection2 = new InputCollection<>(Child.class);
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		form = (FormOneData<Form>) createFormOneData(new Form(),Crud.CREATE);

		form.setDynamic(Boolean.TRUE);
		
		form.getSubmitCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4119943624542439662L;
			@Override
			public void serve(UICommand command, Object parameter) {
				super.serve(command, parameter);
				
			}
		});
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		inputCollection1.getCollection().setName("Number letter list");
		inputCollection1.getCollection().addOne(new Child().setName("One"));
		inputCollection1.getCollection().addOne(new Child().setName("Three"));
		inputCollection1.getCollection().addOne(new Child().setName("Another one"));
		
		inputCollection1.getCollection().addListener(new CollectionHelper.Instance.Listener.Adapter<Child>(){
			private static final long serialVersionUID = 1L;
			@Override
			public void addOne(Instance<Child> instance, Child element,Object source) {
				element.setName("Another element "+System.currentTimeMillis());
			}
		});
		
		final org.cyk.ui.api.data.collector.control.InputChoice<?, ?, ?, ?, ?, SelectItem> inputChoice = form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputChoice.class, "master");
		inputChoice.getList().add(new SelectItem(new Master("M1"), "M1"));
		inputChoice.getList().add(new SelectItem(new Master("M2"), "M2"));
		inputChoice.getList().add(new SelectItem(new Master("M3"), "M3"));
		inputChoice.getList().add(new SelectItem(new Master("M4"), "M4"));
		inputChoice.getList().add(new SelectItem(new Master("M5"), "M5"));
		inputCollection2.setInputChoice(inputChoice);
		inputCollection2.getCollection().setName("Number digit list");
		
		inputCollection2.getCollection().addListener(new CollectionHelper.Instance.Listener.Adapter<Child>(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public Boolean isInstanciatable(Instance<Child> instance, Object object) {
				return object instanceof Master;
			}
			
			@Override
			public void addOne(Instance<Child> instance, Child element,Object source) {
				element.setName("Child of Master "+((Master)((SelectItem)source).getValue()).getName());
			}
			
			@Override
			public Object getSource(Instance<Child> instance, Object object) {
				if(object instanceof Child){
					return ((Child)object).getObject();
				}else if(object instanceof Master){
					for(SelectItem selectItem : inputChoice.getList()){
						if(selectItem.getValue()==object){
							return selectItem;
						}
					}
				}
				return super.getSource(instance, object);
			}
		});
	}
	
	public static class MasterList extends InstanceHelper.Many.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		@Override
		protected Collection<?> __execute__() {
			System.out.println("InputCollectionDemoPage.MasterList.__execute__()");
			return new ArrayList<>(Arrays.asList(new Master("Master1"),new Master("Master2"),new Master("Master3"),new Master("Master4")));
		}
	}
	
	@Getter @Setter
	public static class Form extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Input(rendererStrategy=Input.RendererStrategy.MANUAL) @InputChoice(getChoicesClass=MasterList.class) @InputOneChoice @InputOneCombo private Master master;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @AllArgsConstructor @ToString(of="name")
	public static class Master implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String name;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Child extends InputCollection.Element<Object> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Child setName(String name) {
			return (Child) super.setName(name);
		}
		
	}
}
