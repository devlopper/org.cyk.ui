package org.cyk.ui.web.primefaces.dynamic;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.UIMessageManager.Text;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.PrimefacesEditor;
import org.cyk.utility.common.AbstractMethod;

@Named
@ViewScoped
@Getter
@Setter
public class DynamicEditorController extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private PrimefacesEditor editor;
	
			
	@Override
	protected void initialisation() { 
		super.initialisation();
		
		BusinessEntityInfos businessEntityInfos = uiManager.classFromKey(requestParameter(webManager.getRequestParameterClass()));
		try {
			editor = (PrimefacesEditor) editorInstance(businessEntityInfos.getClazz().newInstance());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		editor.setSubmitMethodMain(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -2421175279479434675L;
			@Override
			protected Object __execute__(Object parameter) {
				messageManager.message(
						SeverityType.INFO,new Text("Dynamic Module",false),new Text(ToStringBuilder.reflectionToString(editor.getObjectModel(),ToStringStyle.MULTI_LINE_STYLE),false))
						.showDialog();
				return null;
			}
		});
		
		
	}
	


}
