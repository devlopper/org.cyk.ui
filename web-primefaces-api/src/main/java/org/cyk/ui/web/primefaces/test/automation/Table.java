package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.openqa.selenium.By;

@Getter @Setter
public class Table extends AbstractElement implements Serializable {

	private static final long serialVersionUID = 1L;

	public Table(String className) {
		super(className);
	}
	
	@Override
	protected String buildClassName(String className) {
		return className;
	}
	
	public Table click(Integer rowIndex,Integer actionIndex){
		//"/html/body/div[3]/div[2]/form/div[1]/div/div[2]/div[2]/table/tbody/tr["+rowIndex+"]/td[5]/table/tbody/tr/td["+actionIndex+"]/button")).click();
    	getWebElement().findElement(By.xpath("/tbody/tr["+rowIndex+"]/td[5]/table/tbody/tr/td["+actionIndex+"]/button")).click();
    	return this;
	}
	public Table clickRead(Integer index){
		return click(index, 1);
    }
	public Table clickUpdate(Integer index){
		return click(index, 2);
    }
	public Table clickDelete(Integer index){
		return click(index, 3);
    }
	
}
