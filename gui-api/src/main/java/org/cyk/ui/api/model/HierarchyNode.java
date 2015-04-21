package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class HierarchyNode implements Serializable {

	private static final long serialVersionUID = -7119597731917014456L;

	private Object data;
	private String label;
	private Boolean expanded=Boolean.TRUE;
	
	public HierarchyNode(Object data) {
		super();
		this.data = data;
		//if(data!=null)
		//	label = UIManager.getInstance().getToStringMethod().execute(data);
	}
	
	@Override
	public String toString() {
		return label;
	}
	
	/**/
	
	/*
	public static AbstractMethod<Collection<Object>, Object> CHILDREN_METHOD = new AbstractMethod<Collection<Object>, Object>() {
		private static final long serialVersionUID = 6739817055207710222L;
		@SuppressWarnings("rawtypes")
		@Override
		protected Collection __execute__(Object parameter) {
			if(parameter instanceof AbstractDataTreeNode)
				return ((AbstractDataTreeNode)parameter).getChildren();
			return null;
		}
	};
	*/
}
