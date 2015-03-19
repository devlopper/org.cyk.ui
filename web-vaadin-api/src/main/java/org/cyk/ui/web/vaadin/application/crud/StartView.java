package org.cyk.ui.web.vaadin.application.crud;

import java.io.Serializable;

import org.cyk.ui.api.MenuManagerOLD.Type;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.model.table.HierarchyNode;
import org.cyk.ui.web.vaadin.AbstractView;
import org.cyk.ui.web.vaadin.CommandBuilder;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class StartView extends AbstractView<Panel,VerticalLayout,CrudApplication> implements Serializable {

	private static final long serialVersionUID = 4894464840887074438L;

	public StartView() {
		//setContent(new ExampleTableEditingComponent());
		Tree tree = CommandBuilder.getInstance().tree(application.getMenuManager().build(Type.APPLICATION,application.getInternalApplicationModuleType()));
		application.getPageLayoutManager().getWest().addComponent(tree);
		
		tree.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 6816275173566732882L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				HierarchyNode hierarchyNode = (HierarchyNode) event.getProperty().getValue();
				if(hierarchyNode==null || hierarchyNode.getData()==null){
					
				}else{
					UICommandable commandable = (UICommandable) hierarchyNode.getData();
					if(commandable.getBusinessEntityInfos()==null){
						setContent(null);
					}else{
						try {
							setContent(new EditorComponent(commandable.getBusinessEntityInfos().getClazz().newInstance()));
							//setContent(new TableComponent((Class<AbstractIdentifiable>) commandable.getBusinessEntityInfos().getClazz()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
	}
	
	@Override
	protected void fill() {
		//addComponent(new Label("You are on the start view"));    
	}
	
}
