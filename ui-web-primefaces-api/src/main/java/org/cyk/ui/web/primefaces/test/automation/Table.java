package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import org.cyk.system.root.business.impl.userinterface.style.CascadeStyleSheetBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Table extends AbstractElement implements Serializable {

	private static final long serialVersionUID = 1L;

	private Class<? extends AbstractIdentifiable> identifiableClass;
	
	public Table(Class<? extends AbstractIdentifiable> identifiableClass,String className) {
		super(className);
		this.identifiableClass = identifiableClass;
	}
	
	@Override
	protected String buildClassName(String className) {
		return className;
	}
	
	@Override
	protected WebElement findWebElement() {
		return super.findWebElement().findElement(By.cssSelector(".ui-datatable-tablewrapper")).findElement(By.tagName("table"));
	}
	
	public Table clickCreate(){
		getHelper().getElementByClassContains(className,"add").click();
		return this;
    }
	public Table clickRead(String identifier){
		getHelper().getElementByClassContains(className,new CascadeStyleSheetBusinessImpl().generateClass(identifiableClass, identifier),"open").click();
		return this;
    }
	public Table clickUpdate(String identifier){
		getHelper().getElementByClassContains(className,new CascadeStyleSheetBusinessImpl().generateClass(identifiableClass, identifier),"edit").click();
		return this;
    }
	public Table clickDelete(String identifier){
		getHelper().getElementByClassContains(className,new CascadeStyleSheetBusinessImpl().generateClass(identifiableClass, identifier),"remove").click();
		return this;
    }
	
}
