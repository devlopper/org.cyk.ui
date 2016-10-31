package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.model.AbstractQueryOneFormModel;
import org.cyk.ui.web.primefaces.page.AbstractSelectOnePage;

@Getter @Setter
public abstract class AbstractActorQueryOneFormModel<ACTOR extends AbstractActor> extends AbstractQueryOneFormModel.Default<ACTOR> implements Serializable {
	private static final long serialVersionUID = -3756660150800681378L;
	
	/**/
	
	/**/
	
	public static class Default extends AbstractActorQueryOneFormModel<AbstractActor> implements Serializable{
		private static final long serialVersionUID = -623788070864438247L;
		
	}
	
	/**/
	
	@Getter @Setter
	public static class AbstractActorSelectOnePageAdapter<ACTOR extends AbstractActor> extends AbstractSelectOnePage.Listener.Adapter.Default<ACTOR,String> implements Serializable {

		private static final long serialVersionUID = -7392513843271510254L;
		
		public AbstractActorSelectOnePageAdapter(Class<ACTOR> aClass) {
			super(aClass);
		}
		
		/**/
		
		public static class Default extends AbstractActorSelectOnePageAdapter<AbstractActor> implements Serializable {
			private static final long serialVersionUID = 1L;

			public Default(Class<AbstractActor> aClass) {
				super(aClass);
			}
			
		}
		
	}
}