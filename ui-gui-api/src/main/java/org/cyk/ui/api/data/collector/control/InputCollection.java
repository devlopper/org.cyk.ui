package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;

import org.cyk.ui.api.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.GridHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputCollection<T,SELECT_ITEM> extends GridHelper.Grid<T> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	protected InputChoice<?, ?, ?, ?, ?, SELECT_ITEM> inputChoice;

	public InputCollection(Class<T> elementClass) {
		super(elementClass);
		getAddCommand().setProperty(Constant.INPUT_VALUE_IS_NOT_REQUIRED, Boolean.TRUE);
		getAddCommand().setNameRendered(getAddCommand().getMappedIcon()==null);
		
		getDeleteCommand().setProperty(Constant.INPUT_VALUE_IS_NOT_REQUIRED, Boolean.TRUE);
		getDeleteCommand().setNameRendered(getDeleteCommand().getMappedIcon()==null);
	}
	
	public InputCollection<T,SELECT_ITEM> setInputChoice(InputChoice<?, ?, ?, ?, ?, SELECT_ITEM> inputChoice){
		this.inputChoice = inputChoice;
		if(this.inputChoice!=null){
			getCollection().setSources(this.inputChoice.getList());
			getCollection().setIsEachElementHasSource(Boolean.TRUE);
		}
		return this;
	}
	
	@Override
	protected void add() {
		if(inputChoice==null)
			super.add();
		else if(inputChoice.getValue()!=null)
			collection.addOne(inputChoice.getValue());
	}
	
	/**/
	
	public static class Element<T> extends org.cyk.utility.common.helper.CollectionHelper.Element<T> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	/**/
	
	public static interface Listener<T> {
		
		public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;

		}
		
	}
}
