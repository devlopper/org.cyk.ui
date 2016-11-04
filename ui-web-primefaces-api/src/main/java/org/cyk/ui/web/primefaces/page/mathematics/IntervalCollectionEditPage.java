package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;

@Getter @Setter @Named @ViewScoped
public class IntervalCollectionEditPage extends AbstractCollectionEditPage<IntervalCollection,Interval> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter
	public static class Form extends AbstractForm<IntervalCollection,Interval> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		@Input @InputNumber private BigDecimal lowestValue;
		@Input @InputNumber private BigDecimal highestValue;
		@Input @InputNumber private Byte numberOfDecimalAfterDot;
		
		@Override
		protected AbstractCollection<?> getCollection() {
			return identifiable;
		}
		
		public static final String FIELD_LOWEST_VALUE = "lowestValue";
		public static final String FIELD_HIGHEST_VALUE = "highestValue";
		public static final String FIELD_NUMBER_OF_DECIMAL_AFTER_DOT = "numberOfDecimalAfterDot";
	}

	@Override
	protected AbstractCollection<?> getCollection() {
		return identifiable;
	}

}
