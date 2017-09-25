package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

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
import org.cyk.utility.common.annotation.user.interfaces.InputText;
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
	private InputCollection<Child> inputCollectionSourceNo = new InputCollection<>(Child.class);
	private InputCollection<Child> inputCollectionSourceNoInput = new InputCollection<>(Child.class);
	
	private InputCollection<Child> inputCollectionSourceYes = new InputCollection<>(Child.class,Master.class);
	private InputCollection<Child> inputCollectionSourceYesInput = new InputCollection<>(Child.class,Master.class);
	
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
				System.out.println("SELECTED Source No        : "+inputCollectionSourceNo.getCollection().getElements());
				System.out.println("SELECTED Source No  Input : "+inputCollectionSourceNoInput.getCollection().getElements());
				System.out.println("SELECTED Source Yes       : "+inputCollectionSourceYes.getCollection().getElements());
				System.out.println("SELECTED Source Yes Input : "+inputCollectionSourceYesInput.getCollection().getElements());
			}
		});
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		inputCollectionSourceNo.getCollection().setName("Source No");
		
		/*inputCollectionSourceNo.getCollection().addListener(new CollectionHelper.Instance.Listener.Adapter<Child>(){
			private static final long serialVersionUID = 1L;
			@Override
			public void addOne(Instance<Child> instance, Child element,Object source,Object sourceObject) {
				element.setName("Source No "+System.currentTimeMillis());
			}
		});*/
		
		inputCollectionSourceNoInput.getCollection().setName("Source No Input");
		/*inputCollectionSourceNoInput.getCollection().addListener(new CollectionHelper.Instance.Listener.Adapter<Child>(){
			private static final long serialVersionUID = 1L;
			@Override
			public void addOne(Instance<Child> instance, Child element,Object source,Object sourceObject) {
				element.setName("Source No Input "+System.currentTimeMillis());
			}
		});*/
		
		inputCollectionSourceYes.setInputChoice(form,"master1");
		inputCollectionSourceYes.getCollection().setName("Source Yes");
		
		/*inputCollectionSourceYes.getCollection().addListener(new InputCollection.CollectionAdapter<Child>(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public void addOne(Instance<Child> instance, Child element,Object source,Object sourceObject) {
				element.setName("C1 "+((Master)sourceObject).getName());
			}
			
		});*/
		
		inputCollectionSourceYesInput.setInputChoice(form,"master2");
		inputCollectionSourceYesInput.getCollection().setName("Source Yes Input");
		
		/*inputCollectionSourceYesInput.getCollection().addListener(new InputCollection.CollectionAdapter<Child>(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public void addOne(Instance<Child> instance, Child element,Object source,Object sourceObject) {
				element.setName("C2 "+((Master)((SelectItem)source).getValue()).getName());
			}
			
		});*/
	}
	
	public static class MasterList1 extends InstanceHelper.Many.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		@Override
		protected Collection<?> __execute__() {
			return new ArrayList<>(Arrays.asList(new SelectItem(new Master("ML11"),"ML11"),new SelectItem(new Master("ML12"),"ML12")
					,new SelectItem(new Master("ML13"),"ML13"),new SelectItem(new Master("ML14"),"ML14")));
		}
	}
	
	public static class MasterList2 extends InstanceHelper.Many.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		@Override
		protected Collection<?> __execute__() {
			return new ArrayList<>(Arrays.asList(new SelectItem(new Master("ML21"),"ML21"),new SelectItem(new Master("ML22"),"ML22")
					,new SelectItem(new Master("ML23"),"ML23"),new SelectItem(new Master("ML24"),"ML24")));
		}
	}
	
	@Getter @Setter
	public static class Form extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@NotNull @Input @InputText private String globalText;
		@Input(rendererStrategy=Input.RendererStrategy.MANUAL) @InputChoice(getChoicesClass=MasterList1.class,nullable=false) @InputOneChoice @InputOneCombo private Master master1;
		@Input(rendererStrategy=Input.RendererStrategy.MANUAL) @InputChoice(getChoicesClass=MasterList2.class,nullable=false) @InputOneChoice @InputOneCombo private Master master2;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @AllArgsConstructor @ToString(of="name")
	public static class Master extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String name;
		
	}
	
	@Getter @Setter
	public static class Child extends InputCollection.Element<Object> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String text;
		
		@Override
		public Child setName(String name) {
			return (Child) super.setName(name);
		}
		
		@Override
		public String toString() {
			return super.toString()+" text="+text;
		}
	}
}
