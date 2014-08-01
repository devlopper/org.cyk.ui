package org.cyk.ui.web.api.resolver;

import java.beans.FeatureDescriptor;
import java.io.Serializable;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;

/*//FIXME Use Version of Glassfish where this issue has been solved
 * 
 * Add this to web fragment if not
 * <context-param>
		<description>In order for the Bean Validation model to work as intended, you must set to true</description>
		<param-name>javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL</param-name>
		<param-value>true</param-value>
	</context-param>
	
	Remove this to the face config
	
	<el-resolver>org.cyk.ui.web.api.resolver.NullStringResolver</el-resolver>
 * 
 */
public class NullStringResolver extends ELResolver implements Serializable {

	private static final long serialVersionUID = -951013441338441816L;

	@Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        return String.class;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return null;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        return null;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        return null;
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        return true;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
    }

    @Override
    public Object convertToType(ELContext context, Object obj, Class<?> targetType) {
        /*if (String.class.equals(targetType) && (obj == null || ((String) obj).isEmpty())) {
            context.setPropertyResolved(true);
        }*/
    	 if (String.class.equals(targetType) && (obj == null ||  ( (obj instanceof String) && ((String) obj).isEmpty()) )) 
	         context.setPropertyResolved(true);
        return null;
    }
}