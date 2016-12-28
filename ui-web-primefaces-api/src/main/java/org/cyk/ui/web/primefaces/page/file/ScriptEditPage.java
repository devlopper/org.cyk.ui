package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.file.ScriptVariableBusiness;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptEvaluationEngine;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.api.ItemCollectionWebAdapter;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ScriptEditPage extends AbstractCrudOnePage<Script> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private ItemCollection<ScriptVariableItem,ScriptVariable> scriptVariableCollection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		scriptVariableCollection = createItemCollection(ScriptVariableItem.class, ScriptVariable.class
				,new ItemCollectionWebAdapter<ScriptVariableItem,ScriptVariable>(){
			private static final long serialVersionUID = -3872058204105902514L;
			@Override
			public Collection<ScriptVariable> load() {
				return inject(ScriptVariableBusiness.class).findByScript(identifiable);
			}
			
			@Override
			public void instanciated(AbstractItemCollection<ScriptVariableItem, ScriptVariable,SelectItem> itemCollection,ScriptVariableItem item) {
				super.instanciated(itemCollection, item);
				item.setName(item.getIdentifiable().getName());
			}	
			@Override
			public void write(ScriptVariableItem item) {
				super.write(item);
				item.getIdentifiable().setName(item.getName());
			}
			@Override
			public Crud getCrud() {
				return crud;
			}
			@Override
			public Boolean isShowAddButton() {
				return Boolean.TRUE;
			}
		});
		((Commandable)scriptVariableCollection.getAddCommandable()).getButton().setImmediate(Boolean.TRUE);
	}
	
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<Script> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		//@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.TEXT)) @NotNull
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete @NotNull private File file;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private ScriptEvaluationEngine evaluationEngine;
		
		@Override
		public void read() {
			super.read();
			
		}
		
		public static final String FIELD_FILE = "file";
		public static final String FIELD_EVALUATION_ENGINE = "evaluationEngine";
	}
	
	@Getter @Setter
	public static class ScriptVariableItem extends AbstractItemCollectionItem<ScriptVariable> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		private String name;
	}
	
	

}
