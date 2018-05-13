package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.userinterface.container.form.FormDetail;
import org.cyk.utility.common.userinterface.input.InputText;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class DynamicFormDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private DynaFormModel model1,model2;  
    private static List<SelectItem> LANGUAGES = new ArrayList<SelectItem>();
	
    @Override
    protected void initialisation() {
    	super.initialisation();
        model1 = createModel1(); 
        model2 = createModel2(); 
    } 
    
    private DynaFormModel createModel1(){
    	DynaFormModel model = new DynaFormModel();  
    	  
        // add rows, labels and editable controls  
        // set relationship between label and editable controls to support outputLabel with "for" attribute  
  
        // 1. row  
        DynaFormRow row = model.createRegularRow();  
  
        DynaFormLabel label11 = row.addLabel("Author");  
        DynaFormControl control12 = row.addControl(new BookProperty("Author", true), "input");  
        label11.setForControl(control12);  
  
        DynaFormLabel label13 = row.addLabel("ISBN");  
        DynaFormControl control14 = row.addControl(new BookProperty("ISBN", true), "input");  
        label13.setForControl(control14);  
  
        // 2. row  
        row = model.createRegularRow();  
  
        DynaFormLabel label21 = row.addLabel("Title");  
        DynaFormControl control22 = row.addControl(new BookProperty("Title", false), "input", 3, 1);  
        label21.setForControl(control22);  
  
        // 3. row  
        row = model.createRegularRow();  
  
        DynaFormLabel label31 = row.addLabel("Publisher");  
        DynaFormControl control32 = row.addControl(new BookProperty("Publisher", false), "input");  
        label31.setForControl(control32);  
  
        DynaFormLabel label33 = row.addLabel("Published on");  
        DynaFormControl control34 = row.addControl(new BookProperty("Published on", false), "calendar");  
        label33.setForControl(control34);  
  
        // 4. row  
        row = model.createRegularRow();  
  
        DynaFormLabel label41 = row.addLabel("Language");  
        DynaFormControl control42 = row.addControl(new BookProperty("Language", false), "select");  
        label41.setForControl(control42);  
  
        DynaFormLabel label43 = row.addLabel("Description", 1, 2);  
        DynaFormControl control44 = row.addControl(new BookProperty("Description", false), "textarea", 1, 2);  
        label43.setForControl(control44);  
  
        // 5. row  
        row = model.createRegularRow();  
  
        DynaFormLabel label51 = row.addLabel("Rating");  
        DynaFormControl control52 = row.addControl(new BookProperty("Rating", 3, true), "rating");  
        label51.setForControl(control52); 
        
        return model;
    }
    
    private DynaFormModel createModel2(){
    	FormDetail formDetail = new FormDetail();
    	InputText c1 = new InputText();
    	c1.setLabelFromIdentifier("f1");
    	InputText c2 = new InputText();
    	c2.setLabelFromIdentifier("f2");
    	InputText c3 = new InputText();
    	c3.setLabelFromIdentifier("f3");
    	InputText c4 = new InputText();
    	c4.setLabelFromIdentifier("f4");
    	InputText c5 = new InputText();
    	c5.setLabelFromIdentifier("f5");
    	InputText c6 = new InputText();
    	c6.setLabelFromIdentifier("f6");
    	InputText c7 = new InputText();
    	c7.setLabelFromIdentifier("f7");
    	c7.getArea().getWidth().setDistance(2);
    	InputText c8 = new InputText();
    	c8.setLabelFromIdentifier("f8");
    	
    	formDetail.getLayout().setType( org.cyk.utility.common.userinterface.Layout.Type.ADAPTIVE);
    	formDetail.layOut(c1).layOut(c2).layOutBreak().layOut(c3).layOutBreak().layOut(c4).layOut(c5).layOutBreak().layOut(c6).layOut(c7).layOutBreak().layOut(c8).layOutBreak();
		
    	DynaFormModel model = (DynaFormModel) FormDetail.buildTarget(formDetail);
    	
        return model;
    }

    public List<BookProperty> getBookProperties() {  
        if (model1 == null) {  
            return null;  
        }  
  
        List<BookProperty> bookProperties = new ArrayList<BookProperty>();  
        for (DynaFormControl dynaFormControl : model1.getControls()) {  
            bookProperties.add((BookProperty) dynaFormControl.getData());  
        }  
  
        return bookProperties;  
    }  
  
    public String submitForm() {  
        FacesMessage.Severity sev = FacesContext.getCurrentInstance().getMaximumSeverity();  
        boolean hasErrors = (sev != null && (FacesMessage.SEVERITY_ERROR.compareTo(sev) >= 0));  
  
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.addCallbackParam("isValid", !hasErrors);  
  
        return null;  
    }  
  
    public List<SelectItem> getLanguages() {  
        if (LANGUAGES.isEmpty()) {  
            LANGUAGES.add(new SelectItem("en", "English"));  
            LANGUAGES.add(new SelectItem("de", "German"));  
            LANGUAGES.add(new SelectItem("ru", "Russian"));  
            LANGUAGES.add(new SelectItem("tr", "Turkish"));  
        }  
  
        return LANGUAGES;  
    }  
	
    /**/
    
    public static class BookProperty implements Serializable {  
    	  
        private static final long serialVersionUID = 20120521L;  
      
        private String name;  
        private Object value;  
        private boolean required;  
      
        public BookProperty(String name, boolean required) {  
            this.name = name;  
            this.required = required;  
        }  
      
        public BookProperty(String name, Object value, boolean required) {  
            this.name = name;  
            this.value = value;  
            this.required = required;  
        }  
      
        public String getName() {  
            return name;  
        }  
      
        public void setName(String name) {  
            this.name = name;  
        }  
      
        public Object getValue() {  
            return value;  
        }  
      
        public Object getFormattedValue() {  
            if (value instanceof Date) {  
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");  
      
                return simpleDateFormat.format(value);  
            }  
      
            return value;  
        }  
      
        public void setValue(Object value) {  
            this.value = value;  
        }  
      
        public boolean isRequired() {  
            return required;  
        }  
      
        public void setRequired(boolean required) {  
            this.required = required;  
        }  
    }  
}
