package org.cyk.ui.api.data.collector.form;
/*
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.UICommand;
import org.cyk.utility.common.annotation.user.interfaces.InputSets;

@Getter @Setter
public abstract class AbstractOneInstanceForm<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractForm<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> implements Serializable {

	private static final long serialVersionUID = -1043478880255116994L;
	
	protected Integer layoutColumnCount=0;
	
	 organized as set  
	protected List<AbstractInputSet<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM>> inputSets = new ArrayList<AbstractInputSet<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM>>();
	
	{
		fieldsRequiredMessage=text("form.one.fields.required");
	}
	
	public AbstractOneInstanceForm(DATA data) {
		super(data);
	}
	
	protected abstract AbstractInputSet<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> __createInputSet__();
	
	public AbstractInputSet<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> createInputSet(String label){
		AbstractInputSet<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> inputSet = __createInputSet__();
		inputSet.setForm(this);
		inputSets.add(inputSet);
		inputSet.setTitle(label);
		return inputSet;
	}
		
	@Override
	public Boolean validate(UICommand command, Object parameter) {
		return Boolean.TRUE;
	}
	
	
	
	public AbstractInputSet<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> inputSetById(String setId){
		for(AbstractInputSet<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> set : inputSets)
			if(set.getAnnotation().identifier().equals(setId))
				return set;
		return null;
	}

	public AbstractInputSet<DATA,FORM,OUTPUTLABEL,INPUT,SELECTITEM> inputSetByIndex(Integer index){
		if(hasInputSetByIndex(index))
			return inputSets.get(index);
		return null;
	}
	
	public Boolean hasInputSetByIndex(Integer index){
		return index<inputSets.size();
	}
	
	public Boolean getCustomInputSetPositioning(){
		return data.getClass().isAnnotationPresent(InputSets.class);
	}
}
*/