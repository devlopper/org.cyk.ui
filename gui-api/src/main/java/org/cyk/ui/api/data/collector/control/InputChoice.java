package org.cyk.ui.api.data.collector.control;

import java.util.List;

public interface InputChoice<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<VALUE_TYPE,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	List<CHOICE_ITEM> getList();

}
