package org.cyk.system.test.model.actor;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Setter;
import lombok.Getter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public class Actor extends AbstractActor implements Serializable {

	private static final long serialVersionUID = 1548374606816696414L;

	public static class SearchCriteria extends AbstractActor.AbstractSearchCriteria<Actor> {

		private static final long serialVersionUID = -7909506438091294611L;

		public SearchCriteria() {
			this(null);
		} 

		public SearchCriteria(String name) {
			super(name);
		}

		@Override
		public void set(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void set(StringSearchCriteria arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
	}

	
} 
