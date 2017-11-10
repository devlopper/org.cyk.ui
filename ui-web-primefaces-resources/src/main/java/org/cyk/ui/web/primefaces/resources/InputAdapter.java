package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.FileHelper.File;
import org.cyk.utility.common.userinterface.Image;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputFile;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyPickList;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

public class InputAdapter extends Input.Listener.Adapter.Default implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Override
	public void read(@SuppressWarnings("rawtypes") Input input) {
		super.read(input);
		if(input instanceof InputFile){
			InputFile inputFile = (InputFile) input;
			if( inputFile.getValue()!=null && inputFile.getValue().getBytes()!=null ){
				Image image = (Image) input.getPropertiesMap().getImageComponent();
	            image.getPropertiesMap().setValue(new ByteArrayContent(inputFile.getValue().getBytes(), inputFile.getValue().getMime()))
	            	.setStream(Boolean.FALSE);	
			}
		}
	}
	
	@Override
	public Object getPreparedValue(Input<?> input) {
		if(input instanceof InputChoiceManyPickList)
			return  ((DualListModel<?>)input.getValueObject()).getTarget();
		if(input instanceof InputFile){
			UploadedFile file = (UploadedFile) ((InputFile)input).getValueObject();
			FileHelper.File value = (FileHelper.File) input.getValue();
			if(file==null || file.getContents()==null || file.getContents().length == 0){
				//value = null;
				value = (File) input.getValue();
			}else {
				if(value==null)
					value = new FileHelper.File();
				value.setName(FileHelper.getInstance().getName(file.getFileName()));
				value.setExtension(FileHelper.getInstance().getExtension(file.getFileName()));
				value.setMime(file.getContentType());
				value.setBytes(file.getContents());
			}
			return value;
		}
		return super.getPreparedValue(input);
	}
	
}