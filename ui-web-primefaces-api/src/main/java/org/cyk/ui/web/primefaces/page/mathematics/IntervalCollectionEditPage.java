package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.api.model.mathematics.IntervalExtremityFormModel;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named @ViewScoped
public class IntervalCollectionEditPage extends AbstractCollectionEditPage.Extends<IntervalCollection,Interval,IntervalCollectionEditPage.IntervalItem> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		identifiable.getItems().setSynchonizationEnabled(Boolean.TRUE);
		itemCollection = createItemCollection(IntervalItem.class, Interval.class,identifiable 
				,new org.cyk.ui.web.primefaces.ItemCollectionAdapter<IntervalItem,Interval,IntervalCollection>(identifiable,crud,form){
					private static final long serialVersionUID = 1L;
			
					@Override
					public Collection<Interval> load() {
						getCollection().getItems().setCollection(inject(IntervalBusiness.class).findByCollection(getCollection()));
						return getCollection().getItems().getCollection();
					}
					
					@Override
					public Interval instanciate(AbstractItemCollection<IntervalItem, Interval, IntervalCollection, SelectItem> itemCollection) {
						Interval interval = inject(IntervalBusiness.class).instanciateOne();
						interval.setCollection(collection);
						return interval;
					}
					
					@Override
					public Boolean isShowAddButton() {
						return Boolean.TRUE;
					}
					
					@Override
					public void read(IntervalItem item) {
						super.read(item);
						item.getLow().set(item.getIdentifiable().getLow());
						item.getHigh().set(item.getIdentifiable().getHigh());
					}
					
					@Override
					public void write(IntervalItem item) {
						super.write(item);
						item.getLow().write(item.getIdentifiable().getLow());
						item.getHigh().write(item.getIdentifiable().getHigh());
					}
					
		});
		
		itemCollection.setShowAddCommandableAtBottom(Boolean.TRUE);
		((Commandable)itemCollection.getAddCommandable()).getButton().setImmediate(Boolean.TRUE);
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		super.transfer(command, parameter);
		if(form.getSubmitCommandable().getCommand()==command){
			getIdentifiable().getItems().setCollection(itemCollection.getIdentifiables());
		}
	}
	
	@Getter @Setter
	public static class Form extends AbstractForm.Extends<IntervalCollection,Interval> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		@Input @InputNumber private BigDecimal lowestValue;
		@Input @InputNumber private BigDecimal highestValue;
		@Input @InputNumber private Byte numberOfDecimalAfterDot;
		
		public static final String FIELD_LOWEST_VALUE = "lowestValue";
		public static final String FIELD_HIGHEST_VALUE = "highestValue";
		public static final String FIELD_NUMBER_OF_DECIMAL_AFTER_DOT = "numberOfDecimalAfterDot";
	}

	@Getter @Setter
	public static class IntervalItem extends AbstractItemCollectionItem<Interval> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		protected IntervalExtremityFormModel low=new IntervalExtremityFormModel(),high=new IntervalExtremityFormModel();

	}
}
