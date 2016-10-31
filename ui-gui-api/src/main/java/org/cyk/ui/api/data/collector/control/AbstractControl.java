package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UserDeviceType;
import org.cyk.ui.api.data.collector.form.AbstractControlSet;
import org.cyk.ui.api.data.collector.layout.Position;
import org.cyk.utility.common.cdi.AbstractBean;

@SuppressWarnings("unchecked")
@Getter @Setter @NoArgsConstructor
public abstract class AbstractControl<MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM> extends AbstractBean implements Control<MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM>,Serializable {

	private static final long serialVersionUID = 5671513590779656492L;

	protected AbstractControlSet<?,MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM> set;	
	protected String id,type;
	protected Position position = new Position();
	protected UIManager uiManager = UIManager.getInstance();
	protected CascadeStyleSheet css = new CascadeStyleSheet();
	protected String uniqueCssClass;
	
	{
		id = System.currentTimeMillis()+RandomStringUtils.randomAlphanumeric(2);
		type = getControlType((Class<AbstractControl<?, ?, ?, ?, ?>>) getClass());
	}
	
	protected static String text(String code) {
		return UIManager.getInstance().text(code);
	}
	
	@Override
	public UserDeviceType getUserDeviceType() {
		return set.getUserDeviceType();
	}
	
	public void setUniqueCssClass(String uniqueCssClass){
		css.removeClass(this.uniqueCssClass);
		this.uniqueCssClass = uniqueCssClass;
		css.addClass(getUniqueCssClass());
	}
	
	public static String getControlType(Class<?> aClass){
		return aClass.getSimpleName();
	}
} 
