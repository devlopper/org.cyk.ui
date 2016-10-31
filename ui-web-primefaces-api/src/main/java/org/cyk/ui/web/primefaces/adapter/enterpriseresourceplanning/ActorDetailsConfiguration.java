package org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.business.impl.party.person.AbstractActorDetails;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.party.AbstractActorEditPage;

@Getter @Setter
public class ActorDetailsConfiguration extends PersonDetailsConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final List<String> ACTOR_EXTENDED_FIELD_NAMES = new ArrayList<>();
	static{
		ACTOR_EXTENDED_FIELD_NAMES.add(AbstractActorEditPage.Form.FIELD_REGISTRATION_DATE);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
		return new DetailsControlSetAdapter(clazz);
	}
	
	/**/
	
	@Getter @Setter
	public static class FormControlSetAdapter extends PersonDetailsConfiguration.FormControlSetAdapter implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		public FormControlSetAdapter(Class<?> identifiableClass) {
			super(identifiableClass);
			addFieldNamePairOrder(AbstractActorEditFormModel.FIELD_CODE, AbstractActorEditFormModel.FIELD_REGISTRATION_DATE);
		}
		
		@Override
		public String[] getFieldNames() {
			return ACTOR_EXTENDED_FIELD_NAMES.toArray(new String[]{});
		}

	}
	
	@Getter @Setter @NoArgsConstructor
	public static class DetailsControlSetAdapter extends PersonDetailsConfiguration.DetailsControlSetAdapter implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		public DetailsControlSetAdapter(Class<?> identifiableClass) {
			super(identifiableClass);
			addFieldNamePairOrder(AbstractActorDetails.FIELD_CODE, AbstractActorEditPage.Form.FIELD_REGISTRATION_DATE);
		}
		
		@Override
		public String[] getFieldNames() {
			return ACTOR_EXTENDED_FIELD_NAMES.toArray(new String[]{});
		}

		@Override
		public Boolean build(Object data, Field field) {
			if(data instanceof AbstractActorDetails && ACTOR_EXTENDED_FIELD_NAMES.contains(field.getName()))
				return Boolean.TRUE;
			return super.build(data, field);
		}
	}
}
