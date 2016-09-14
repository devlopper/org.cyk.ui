package org.cyk.ui.web.primefaces.api.integration;


public class SeleniumApiIT extends AbstractWebIT {

	private static final long serialVersionUID = 1L;

    @Override
	protected void __execute__() {
    	super.__execute__();
    	pause(3000l);
    	helper.goToPage("party/person/list","clazz=Person");
    	pause(3000l);
    	//pause(1000 * 3l);
    	//getDriver().findElement(By.xpath("/html/body/div[3]/div[2]/form/div[1]/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/table/tbody/tr/td[1]/button")).click();
    	helper.getElementByClassContains("identifiable_person_pers0214","open").click();
    		//.findElement(By.xpath("td[5]/table/tbody/tr/td[1]/button")).click();
    	pause(3000l);
    	//debug(webDriver.findElement(By.xpath("[@class^='InputText_nom_d_utilisateur_']")));
        //System.out.println(webDriver.findElements(By.xpath("//input[class='ui-inputtext']")).size());
        //debug(webDriver.findElement(By.xpath("input[id*='form:j_idt77:j_idt84:j_idt86:j_idt423:r3c2reg:inputText']")));
        //System.out.println(getDriver().findElements(By.cssSelector("[class*='InputPassword_mot_de_passe_']")).size());
        //System.out.println(SeleniumHelper.getInstance().getElementByClassContains("commandable_model_entity_person_"));
        //System.out.println(SeleniumHelper.getInstance().getElementByClassContains("commandable_list_person_"));
        //SeleniumHelper.getInstance().getElementByClassContains("commandable_list_person_").click();
	}
    
    
    @Override
    protected Integer getImplicitlyWaitNumberOfSecond() {
    	return 5;
    }
    
}
