package org.cyk.ui.web.primefaces.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIManager.CollectionLoadMethod;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.UIMessageManager.Text;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.PrimefacesEditor;
import org.cyk.ui.web.primefaces.test.MyEntity.MyDetails2;
import org.cyk.utility.common.AbstractMethod;

@Named
@ViewScoped
@Getter
@Setter
public class DynaFormController extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private PrimefacesEditor editor;
			
	@Override
	protected void initialisation() { 
		super.initialisation();
		UIManager.COLLECTION_LOAD_METHOD = new CollectionLoadMethod() {
			private static final long serialVersionUID = -2251974175051850252L;
			@Override
			protected Collection<Object> __execute__(Class<Object> aClass) {
				Collection<Object> collection = new ArrayList<>();
				//if(MyDetails.class.equals(aClass))
				//	return Arrays.asList(new MyDetails(),new MyDetails());
				if(MyDetails2.class.equals(aClass)){
					collection.add(new MyDetails2());
					collection.add(new MyDetails2());
				}
				return collection;
			}
		};
		
		editor = (PrimefacesEditor) editorInstance(new MyEntity());
		//contentMenuModel = editor.getModel();
		editor.setSubmitMethodMain(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -2421175279479434675L;
			@Override
			protected Object __execute__(Object parameter) {
				messageManager.message(
						SeverityType.INFO,new Text("Recap",false),new Text(ToStringBuilder.reflectionToString(editor.getObjectModel(),ToStringStyle.MULTI_LINE_STYLE),false))
						.showDialog();
				return null;
			}
		});
		
		
	}
	


}
