package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.form.Form;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class IdentifiableEditPage extends org.cyk.ui.web.api.resources.page.IdentifiableEditPage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Class<? extends Form> getFormMasterClass() {
		Class<? extends Form> clazz = super.getFormMasterClass();
		if(Form.class.equals(clazz))
			clazz = ClassHelper.getInstance().getMapping(FormMaster.class);
		return clazz;
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class FormMaster extends org.cyk.ui.web.api.resources.page.IdentifiableEditPage.FormMaster implements Serializable {
		private static final long serialVersionUID = 1L;
		/*
		@Override
		protected void __prepare__() {
			super.__prepare__();
			//controls
			//inputs
			Form.Detail detail = getDetail();
			detail.getLayout().setType(Layout.Type.ADAPTIVE);
			____add____();
		}
		
		protected void ____add____(){
			____addCode____();
			____addName____();
			____addHierarchy____();
			____addType____();
		}
		
		protected void ____addCode____(){
			if(ClassHelper.getInstance().isIdentified(getObject().getClass()))
				____add____(ClassHelper.getInstance().getIdentifierFieldName(getObject().getClass()));
		}
		
		protected void ____addName____(){
			if(ClassHelper.getInstance().isNamed(getObject().getClass()))
				____add____(ClassHelper.getInstance().getNameFieldName(getObject().getClass()));
		}
		
		protected void ____addHierarchy____(){
			if(ClassHelper.getInstance().isHierarchy(getObject().getClass()))
				____add____(ClassHelper.getInstance().getHierarchyFieldName(getObject().getClass()));
		}
		
		protected void ____addType____(){
			if(ClassHelper.getInstance().isTyped(getObject().getClass()))
				____add____(ClassHelper.getInstance().getTypeFieldName(getObject().getClass()));
		}
		
		protected void ____add____(String fieldName){
			if(Boolean.TRUE.equals(Input.isinputable(getObject().getClass(), fieldName))){
				getDetail().setFieldsObjectFromMaster(FieldHelper.getInstance().getIsContainSeparator(fieldName) ? ____getFieldsObjectFromMaster____(fieldName) : null);
				getDetail().add(FieldHelper.getInstance().getLast(fieldName)).addBreak();
			}
		}

		protected String[] ____getFieldsObjectFromMaster____(String fieldName) {
			return FieldHelper.getInstance().getFieldNames(FieldHelper.getInstance().getBeforeLast(fieldName)).toArray(new String[]{});
		}*/
	}
}
