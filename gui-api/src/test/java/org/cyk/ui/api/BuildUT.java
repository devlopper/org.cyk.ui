package org.cyk.ui.api;

import java.lang.reflect.Field;

import org.cyk.ui.api.model.AbstractPartyFormModel;
import org.cyk.ui.api.model.PersonFormModel;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.test.AbstractUnitTest;
  
public class BuildUT extends AbstractUnitTest {

	private static final long serialVersionUID = 4990067858159745391L;

	//private AbstractControlSet<?,?, ?, ?, ?, ?> controlSet1,controlSet2;
	@Override
	protected void _execute_() {
		super._execute_();
		/*controlSet1 = new ControlSet<>();
		controlSet1.setTitle("MyControlSet 1");
		
		controlSet2 = new ControlSet<>();
		controlSet2.setTitle("MyControlSet 2");
		
		controlSet1.getChildren().add(controlSet2);
		
		controlSet1.build();*/
		Field field = CommonUtils.getInstance().getFieldFromClass(PersonFormModel.class, "contactCollectionFormModel");
		System.out.println(field);
		System.out.println(CommonUtils.getInstance().getFieldAnnotation(field,AbstractPartyFormModel.class, Sequence.class, Boolean.TRUE));
		
		System.out.println(CommonUtils.getInstance().getFieldAnnotation(field,PersonFormModel.class, Sequence.class, Boolean.TRUE));
	}
	
	
	
}
