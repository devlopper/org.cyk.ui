package org.cyk.ui.api.component;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.enterprise.inject.spi.CDI;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractInputOutputComponent<VALUE_TYPE> extends AbstractBean implements UIInputOutputComponent<VALUE_TYPE>,Serializable {

	private static final long serialVersionUID = -7631765878611822196L;
	
	private String name;
	@Getter private String id;
	@Getter @Setter private Integer leftIndex,topIndex,width=1,height=1;
	protected Class<VALUE_TYPE> clazz;
	@Getter @Setter protected VALUE_TYPE value;
	protected UIManager uiManager;
	
	public AbstractInputOutputComponent() {
		constructor(/*(Class<VALUE_TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]*/ valueTypeClass(), null);
	}
	
	public AbstractInputOutputComponent(Class<VALUE_TYPE> aClass,VALUE_TYPE aValue) {
		constructor(aClass, aValue);
	}
	
	private void constructor(Class<VALUE_TYPE> aClass,VALUE_TYPE aValue){
		this.clazz=aClass;
		this.value=aValue;
		id = System.currentTimeMillis()+RandomStringUtils.randomAlphanumeric(2);
		uiManager = getReference(CDI.current().getBeanManager(),UIManager.class);
	}
	
	@SuppressWarnings("unchecked")
	protected Class<VALUE_TYPE> valueTypeClass(){
		return (Class<VALUE_TYPE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public String getName() {
		return StringUtils.isEmpty(name)?toString():name;
	}
	
	@Override
	public String getFamily() {
		return getClass().getSimpleName();
	}
	
}
