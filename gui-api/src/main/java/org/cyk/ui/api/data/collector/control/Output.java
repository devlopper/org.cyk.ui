package org.cyk.ui.api.data.collector.control;


public interface Output<MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM> extends Control<MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM>  {

	String getLabel();//TODO just a trick to avoid an error of output label does not have property label
	
}
