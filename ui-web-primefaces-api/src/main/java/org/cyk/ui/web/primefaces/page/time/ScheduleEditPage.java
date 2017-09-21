package org.cyk.ui.web.primefaces.page.time;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.time.ScheduleItemBusiness;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.api.model.time.InstantIntervalFormModel;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;

@Getter @Setter @Named @ViewScoped
public class ScheduleEditPage extends AbstractCollectionEditPage.Extends<Schedule,ScheduleItem,ScheduleEditPage.ScheduleItemItem> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected ItemCollection<ScheduleItemItem, ScheduleItem, Schedule> instanciateItemCollection() {
		return createItemCollection(ScheduleItemItem.class, ScheduleItem.class,identifiable 
				,new org.cyk.ui.web.primefaces.ItemCollectionAdapter.Extends<ScheduleItemItem,ScheduleItem,Schedule>(identifiable,crud,form,ScheduleItem.class){
			private static final long serialVersionUID = 1L;
			
			@Override
			public Collection<ScheduleItem> load() {
				getCollection().getItems().setElements(inject(ScheduleItemBusiness.class).findByCollection(getCollection()));
				return getCollection().getItems().getElements();
			}
			
			@Override
			public ScheduleItem instanciate(AbstractItemCollection<ScheduleItemItem, ScheduleItem, Schedule, SelectItem> itemCollection) {
				ScheduleItem interval = inject(ScheduleItemBusiness.class).instanciateOne();
				interval.setCollection(collection);
				return interval;
			}
			
			@Override
			public void read(ScheduleItemItem item) {
				super.read(item);
				item.getInstantInterval().set(item.getIdentifiable().getInstantInterval());
			}
			
			@Override
			public void write(ScheduleItemItem item) {
				super.write(item);
				item.getInstantInterval().write(item.getIdentifiable().getInstantInterval());
			}
			
		});
	}
	
	@Getter @Setter
	public static class Form extends AbstractForm.Extends<Schedule,ScheduleItem> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		
	}

	@Getter @Setter
	public static class ScheduleItemItem extends AbstractItemCollectionItem<ScheduleItem> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		protected InstantIntervalFormModel instantInterval=new InstantIntervalFormModel();
		protected String from,to;//FIXME workaround to avoid promerty not found
		
		@Override
		public void read() {
			super.read();
			instantInterval.set(identifiable.getInstantInterval());
		}
		
		@Override
		public void write() {
			super.write();
			instantInterval.write(identifiable.getInstantInterval());
		}
		
		/*
		{
			InputNumber inputNumber = new InputNumber();
			inputNumber.setLabel("y");
			instantInterval.getFrom().addInput(inputNumber);
			
			inputNumber = new InputNumber();
			inputNumber.setLabel("MONTH");
			instantInterval.getFrom().addInput(inputNumber);
			
			inputNumber = new InputNumber();
			inputNumber.setLabel("DA");
			instantInterval.getFrom().addInput(inputNumber);
			
			System.out
					.println("ScheduleEditPage.ScheduleItemItem.enclosing_method() : "+instantInterval.getFrom().getInputs());
		}*/
		
	}
}
