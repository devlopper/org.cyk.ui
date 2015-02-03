package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;

import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

public class FieldSequenceComparator implements Comparator<Field>,Serializable {

	private static final long serialVersionUID = -2995791778171074757L;

	@Override
	public int compare(Field field1, Field field2) {
		System.out.println("FieldSequenceComparator.compare() : "+field1.getName()+" : "+field2.getName());
		Sequence s1 = field1.getAnnotation(Sequence.class);
		Sequence s2 = field1.getAnnotation(Sequence.class);
		if(s1==null || s2==null)
			return 0;
		System.out.println("FieldSequenceComparator.compare() : "+field1.getName()+" : "+field2.getName()+" --- ");
		Boolean ordered = ordered(field1, s1, field2, s2);
		if(ordered==null)
			return 0;
		System.out.println("FieldSequenceComparator.compare() : "+ordered);
		return ordered?-1:1;
	}
	
	private Boolean ordered(Field f1,Sequence s1, Field f2,Sequence s2){
		System.out.println("FieldSequenceComparator.ordered()");
		if(s1.field().equals(f2.getName()))
			return Direction.BEFORE.equals(s1.direction());
		else if(s2.field().equals(f1.getName()))
			return Direction.AFTER.equals(s2.direction());
		return null;
	}

}
