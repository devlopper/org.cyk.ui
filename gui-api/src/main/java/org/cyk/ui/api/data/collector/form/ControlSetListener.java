package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.control.OutputLabel;
import org.cyk.utility.common.AbstractFieldSorter.FieldSorter;
import org.cyk.utility.common.AbstractFieldSorter.ObjectField;
import org.cyk.utility.common.AbstractFieldSorter.ObjectFieldSorter;
import org.cyk.utility.common.cdi.BeanAdapter;


public interface ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> {

	String[] getFieldNames();
	void setFieldNames(String[] names);
	
	String[] getRequiredFieldNames();
	void setRequiredFieldNames(String[] names);
	
	List<String> getExpectedFieldNames();
	void setExpectedFieldNames(List<String> collection);
	
	void sort(List<Field> fields);
	void sortObjectFields(List<ObjectField> objectFields,Class<?> aClass);
	
	FieldSorter getFieldSorter();
	ObjectFieldSorter getObjectFieldSorter(List<ObjectField> objectFields,Class<?> aClass);
	
	Boolean build(Object data,Field field);
	String fiedLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Object data,Field field);
	void labelBuilt(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Field field,LABEL label);
	void input(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Input<?, MODEL, ROW, LABEL, CONTROL, SELECTITEM> input);
	Boolean showFieldLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Field field);
	
	MODEL createModel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet);
	
	Boolean canCreateRow(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Object object);
	ROW createRow(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet);
	
	LABEL createLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,OutputLabel<MODEL,ROW, LABEL, CONTROL, SELECTITEM> outputLabel,ROW row);

	CONTROL createControl(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> control,ROW row);
	
	void setControlLabel(ControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> controlSet,CONTROL control,LABEL label);
	
	/**/
	
	public static class Adapter<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends BeanAdapter implements ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> {

		private static final long serialVersionUID = 1L;

		protected ObjectFieldSorter objectFieldSorter;
		@Getter @Setter protected List<String> expectedFieldNames;
		@Getter @Setter protected String[] fieldNames,requiredFieldNames;
		protected List<String[]> fieldNamePairOrders;
		
		@Override
		public void sort(List<Field> fields) {}

		@Override
		public void sortObjectFields(List<ObjectField> objectFields,Class<?> aClass) {}

		@Override
		public FieldSorter getFieldSorter() {
			return null;
		}

		@Override
		public ObjectFieldSorter getObjectFieldSorter(List<ObjectField> objectFields,Class<?> aClass) {
			return null;
		}

		@Override
		public Boolean build(Object data, Field field) {
			return null;
		}

		@Override
		public String fiedLabel(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet,Object data, Field field) {
			return null;
		}

		@Override
		public void labelBuilt(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet,Field field, LABEL label) {}

		@Override
		public void input(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet,Input<?, MODEL, ROW, LABEL, CONTROL, SELECTITEM> input) {}

		@Override
		public Boolean showFieldLabel(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet,Field field) {
			return null;
		}

		@Override
		public MODEL createModel(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet) {
			return null;
		}

		@Override
		public Boolean canCreateRow(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet,Object object) {
			return null;
		}

		@Override
		public ROW createRow(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet) {
			return null;
		}

		@Override
		public LABEL createLabel(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet,OutputLabel<MODEL, ROW, LABEL, CONTROL, SELECTITEM> outputLabel,ROW row) {
			return null;
		}

		@Override
		public CONTROL createControl(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet,Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM> control, ROW row) {
			return null;
		}

		@Override
		public void setControlLabel(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet,CONTROL control, LABEL label) {}
		
		public void addFieldNamePairOrder(String first,String second) {
			if(fieldNamePairOrders == null)
				fieldNamePairOrders = new ArrayList<>();
			fieldNamePairOrders.add(new String[]{first,second});
		}
		
		/*protected String getFieldLabelIf(Class<?> aClass,Object data,Field field,String fieldName,String label){
			if(aClass.isAssignableFrom(data.getClass()) && fieldName.equals(field.getName()))
				return label;
			return null;
		}*/
		
		/**/
		@NoArgsConstructor
		public static class Default<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends Adapter<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private Class<?> identifiableClass;
			private Crud crud;
			
			public Default(Class<?> identifiableClass, Crud crud) {
				super();
				this.identifiableClass = identifiableClass;
				this.crud = crud;
			}
			
			@Override
			public List<String> getExpectedFieldNames() {
				//if(expectedFieldNames == null){
					expectedFieldNames = new ArrayList<>(); 
					if(identifiableClass!=null && crud!=null){
						FormConfiguration formConfiguration = FormConfiguration.get(identifiableClass, crud, Boolean.FALSE);
						if(formConfiguration!=null){
							expectedFieldNames.addAll(formConfiguration.getFieldNames());
						}
					}
				//}
				return expectedFieldNames;
			}

			@Override
			public ObjectFieldSorter getObjectFieldSorter(List<ObjectField> objectFields,Class<?> aClass) {
				//if( objectFieldSorter == null ){
					objectFieldSorter = new ObjectFieldSorter(objectFields, aClass);
					List<String> list = getExpectedFieldNames();
					if(list!=null && !list.isEmpty()){
						list = new ArrayList<>(list);
						if(getFieldNames()!=null)
							list.addAll(Arrays.asList(getFieldNames()));
						if(fieldNamePairOrders != null)	
							for(String[] names : fieldNamePairOrders){
								Integer index1 = list.indexOf(names[1]);
								if(index1 > -1){
									Integer index2 = list.indexOf(names[0]);
									if(index2 > -1)
										list.add(index2+1, names[1]);
								}	
							}	
						objectFieldSorter.setUseExpectedFieldNames(Boolean.TRUE);
						objectFieldSorter.setExpectedFieldNames(list.toArray(new String[]{}));
					}
				//}
				return objectFieldSorter;
			}
			
			@Override
			public void sortObjectFields(List<ObjectField> objectFields,Class<?> aClass) {
				ObjectFieldSorter objectFieldSorter = getObjectFieldSorter(objectFields, aClass);
				if(objectFieldSorter!=null)
					objectFieldSorter.sort();
			}
		}
		
		/**/
		
		protected Boolean isFieldNameIn(Field field,String...names){
			return ArrayUtils.contains(names, field.getName());
		}
		
		protected Boolean isFieldNameNotIn(Field field,String...names){
			return Boolean.FALSE.equals(isFieldNameIn(field, names));
		}
		
		protected Boolean isBusinessIdentificationField(Field field){
			return isFieldNameIn(field, CODE,NAME);
		}
		

		/**/
		
		public static final String IMAGE = "image";
		public static final String CODE = "code";
		public static final String NAME = "name";
		public static final String ABBREVIATION = "abbreviation";
		public static final String DESCRIPTION = "description";
		public static final String EXISTENCE_PERIOD = "existencePeriod";
		
		/**/
		@NoArgsConstructor
		public static class Form<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends Default<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> implements Serializable {
			private static final long serialVersionUID = 1L;

			public Form(Class<?> identifiableClass, Crud crud) {
				super(identifiableClass, crud);
			}
			
			
		}
		
		@NoArgsConstructor
		public static class Details<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends Default<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> implements Serializable {
			private static final long serialVersionUID = 1L;

			public Details(Class<?> identifiableClass, Crud crud) {
				super(identifiableClass, crud);
			}
			
		}
	}
	
}
