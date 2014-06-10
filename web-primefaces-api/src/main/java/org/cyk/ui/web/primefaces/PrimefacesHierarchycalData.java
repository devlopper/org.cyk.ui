package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.ui.api.HierarchycalData;
import org.cyk.ui.api.HierarchycalDataNode;
import org.cyk.utility.common.AbstractMethod;
import org.omnifaces.model.tree.ListTreeModel;
import org.omnifaces.model.tree.TreeModel;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.MenuModel;

public class PrimefacesHierarchycalData<DATA> extends HierarchycalData<DATA> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private MenuModel menuModel;
	@Getter @Setter private NodeEditEventMethod onNodeEditMethod,onNodeEditInitMethod,onNodeEditCancelMethod;
	@Getter private Command primefacesAddNodeCommand,primefacesSaveNodeCommand,primefacesCancelNodeCommand,primefacesDeleteNodeCommand;
	@Getter private TreeModel<HierarchycalDataNode> tree;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		primefacesAddNodeCommand =  new Command(addNodeCommand);
		
		//primefacesDeleteNodeCommand =  new Command(deleteNodeCommand);
		
		/*
		onNodeEditMethod = new NodeEditEventMethod(){
			private static final long serialVersionUID = -8499327887343205809L;
			@Override
			protected void onEvent(NodeEditEvent rowEditEvent) {
				TableNode<?> row = (TableNode<?>) rowEditEvent.getObject();
				row.updateFieldValues();
				saveNodeCommand.execute(row.getData());
			}};
		
		onNodeEditCancelMethod = new NodeEditEventMethod(){
			private static final long serialVersionUID = -8499327887343205809L;
			@Override
			protected void onEvent(NodeEditEvent rowEditEvent) {
				TableNode<?> row = (TableNode<?>) rowEditEvent.getObject();
				cancelNodeCommand.execute(row.getData());
			}};
			*/
	}
	
	
	
	@Override
	public void targetDependentInitialisation() {
		tree = new ListTreeModel<HierarchycalDataNode>();
		Field field = commonUtils.getField(getWindow(), this);
		menuModel = CommandBuilder.getInstance().menuModel(menu, window.getClass(), field.getName());
		for(DATA d : data)
			populate(d, tree);
	}
	
	private void populate(DATA root,TreeModel<HierarchycalDataNode> tree){
		TreeModel<HierarchycalDataNode> childTree = tree.addChild(new HierarchycalDataNode(root));
		@SuppressWarnings("unchecked")
		Collection<DATA> children = (Collection<DATA>) CHILDREN.execute(root);
		if(children!=null)
			for(DATA child : children)
				populate(child, childTree);
	}
	
	@Override
	protected void link(DATA parent, DATA child) {
		super.link(parent, child);
		find(tree, parent).addChild(new HierarchycalDataNode(child));
	}
	
	protected TreeModel<HierarchycalDataNode> find(TreeModel<HierarchycalDataNode> tree,DATA data){
		if(tree.getData()!=null && tree.getData().getData().equals(data))
			return tree;
		for(TreeModel<HierarchycalDataNode> child : tree.getChildren())
			return find(child, data);
		return null;
	}
			
	/**/
	
	/**/
	
	public static abstract class NodeEditEventMethod extends AbstractMethod<Object, TreeNode>{
		private static final long serialVersionUID = -145475519122234694L;
		@Override protected final Object __execute__(TreeNode node) {onEvent(node);return null;}
		protected abstract void onEvent(TreeNode node);
	}
	
	public static AbstractMethod<Collection<Object>, Object> CHILDREN = new AbstractMethod<Collection<Object>, Object>() {
		private static final long serialVersionUID = 6739817055207710222L;
		@SuppressWarnings("rawtypes")
		@Override
		protected Collection __execute__(Object parameter) {
			if(parameter instanceof DataTreeType)
				return ((DataTreeType)parameter).getChildren();
			return null;
		}
	};
		
}
