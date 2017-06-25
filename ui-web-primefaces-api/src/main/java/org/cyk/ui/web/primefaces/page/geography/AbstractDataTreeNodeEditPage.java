package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeTypeForm;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractDataTreeNodeEditPage<DATA_TREE_NODE extends AbstractDataTreeNode> extends AbstractCrudOnePage<DATA_TREE_NODE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		identifiable.setAutomaticallyMoveToNewParent(Crud.UPDATE.equals(crud));
		//form.getSubmitCommandable().getCommand().setConfirm(Boolean.TRUE);
		WebInput<?, ?, ?, ?> webInput = (WebInput<?, ?, ?, ?>) form.getInputByFieldName(AbstractDataTreeForm.FIELD_PARENT);
		webInput.getWebInputListeners().add(new WebInput.Listener.Adapter.Default(){
			private static final long serialVersionUID = 138235165190874360L;
			@Override
			public void validate(FacesContext facesContext, UIComponent uiComponent, Object value)throws ValidatorException {
				super.validate(facesContext, uiComponent, value);
				/*if( ((Form)form.getData()).isCurrentParentEqualsNewParent() )
					;
				else
					messageManager.message(SeverityType.WARNING, ((Form)form.getData()).getCurrentParent()+" node will be moved under "+value,false).showGrowl();
				*/
					//webManager.throwValidationException();
			}
		});
	}
	
	@Override
	protected DATA_TREE_NODE instanciateIdentifiable() {
		//DATA_TREE_NODE node = super.instanciateIdentifiable();
		//node.getParents().add(webManager.getIdentifiableFromRequestParameter(identifiableClass,Boolean.TRUE));
		return ((AbstractDataTreeNodeBusiness<DATA_TREE_NODE>)getBusiness()).instanciateOne(webManager.getIdentifiableFromRequestParameter(identifiableClass,Boolean.TRUE));
	}
	
	@Override
	protected void processOnIdentifiableFound(DATA_TREE_NODE identifiable) {
		super.processOnIdentifiableFound(identifiable);
		identifiable.setParent(getBusiness().findParent(identifiable));
	}
	/*
	@SuppressWarnings("unchecked")
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass) {
		DATA_TREE_NODE node =  (DATA_TREE_NODE) super.identifiableFromRequestParameter(aClass);
		if(node!=null){
			node.getParents().clear();
			node.getParents().add(getBusiness().findParent(node));
		}
		return (T) node;
	}*/
	
	@Getter @Setter 
	public static abstract class AbstractForm<NODE extends AbstractDataTreeType> extends AbstractDataTreeTypeForm<NODE> {
		private static final long serialVersionUID = -4741435164709063863L;
		
		
	}
	
}
