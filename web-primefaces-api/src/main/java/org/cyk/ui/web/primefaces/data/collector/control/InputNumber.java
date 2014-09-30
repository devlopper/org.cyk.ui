/*package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class InputNumber extends AbstractInput<Number> implements Serializable {

	private static final long serialVersionUID = 1390099136018097004L;

	
	@Override
	public Converter getConverter() {
		Class<?> wrapper = ClassUtils.primitiveToWrapper(field.getType());
		try{
			return (Converter) Class.forName("javax.faces.convert."+wrapper.getSimpleName()+"Converter").newInstance();
		}catch(Exception e){
			log.log(Level.SEVERE,e.toString(),e);
		}
		return null;
	}
	
}
*/