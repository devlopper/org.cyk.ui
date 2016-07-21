package org.cyk.ui.web.primefaces.page.nestedset;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.api.model.pattern.tree.NestedSetNodeEditFormModel;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessQueryPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Named
@ViewScoped
@Getter
@Setter
public class NestedSetNodeQueryPage extends AbstractBusinessQueryPage<NestedSetNode,NestedSetNodeQueryPage.QueryFormModel, NestedSetNodeEditFormModel> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private static final CascadeStyleSheet ROOT = new CascadeStyleSheet();
	static{
		ROOT.addClass("rootclass");
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		table.getRowListeners().add(new RowAdapter<Object>(){
			@Override
			public void created(Row<Object> row) {
				super.created(row);
				if( ((NestedSetNodeEditFormModel)row.getData()).getParent()==null )
					row.setCascadeStyleSheet(ROOT);
			}
		});
		rowAdapter.setUpdatable(Boolean.TRUE);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		table.setShowEditColumn(Boolean.TRUE);
	}
	
	@Override
	protected Boolean autoLoad() {
		return Boolean.TRUE;
	}
	
	@Override
	protected Class<NestedSetNode> __entityClass__() {
		return NestedSetNode.class;
	}
	
	@Override
	protected Class<NestedSetNodeQueryPage.QueryFormModel> __queryClass__() {
		return NestedSetNodeQueryPage.QueryFormModel.class;
	}

	@Override
	protected Class<NestedSetNodeEditFormModel> __resultClass__() {
		return NestedSetNodeEditFormModel.class;
	}

	@Override
	protected Collection<NestedSetNode> __query__() {
		if(form.getData().getSet()==null)
			return rootBusinessLayer.getNestedSetNodeDao().readAll();
		return rootBusinessLayer.getNestedSetNodeDao().readBySet(form.getData().getSet());
	}

	@Override
	protected Long __count__() {
		if(form.getData().getSet()==null)
			return rootBusinessLayer.getNestedSetNodeDao().countAll();
		return rootBusinessLayer.getNestedSetNodeDao().countBySet(form.getData().getSet());
	}	
	
	@Getter @Setter
	public static class QueryFormModel{
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private NestedSet set;
		
	}
	
	@Getter @Setter
	public static class ResultDetails extends AbstractOutputDetails<NestedSetNode>{
	
		private static final long serialVersionUID = 1995539042596935661L;
		
		@Input @InputText
		private String names,c1,c2;
		
		public ResultDetails(NestedSetNode nestedSetNode) {
			super(nestedSetNode);
		}
	}
	
}
