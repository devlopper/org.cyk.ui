package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import org.cyk.system.root.model.ContentType;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.InputChoice;

public class UIProviderAdapter<MODEL, ROW, LABEL, CONTROL, SELECTITEM> implements UIProviderListener<MODEL, ROW, LABEL, CONTROL, SELECTITEM>,Serializable {

	private static final long serialVersionUID = -1238691206716866866L;

	@Override
	public Class<? extends Control<?, ?, ?, ?, ?>> controlClassSelected(Class<? extends Control<?, ?, ?, ?, ?>> aClass) {
		return null;
	}

	@Override
	public void controlInstanceCreated(Control<?, ?, ?, ?, ?> control) {}

	@Override
	public void choices(InputChoice<?, ?, ?, ?, ?, ?> inputChoice,Object data, Field field, List<Object> list) {}

	@Override
	public Class<? extends UICommandable> commandableClassSelected(Class<? extends UICommandable> aClass) {
		return null;
	}

	@Override
	public void commandableInstanceCreated(UICommandable aCommandable) {}

	@Override
	public String readOnlyValue(Field field, Object object) {
		return null;
	}

	@Override
	public String formatValue(Field field, Object value) {
		return null;
	}

	@Override
	public ContentType contentType() {
		return null;
	}
}
