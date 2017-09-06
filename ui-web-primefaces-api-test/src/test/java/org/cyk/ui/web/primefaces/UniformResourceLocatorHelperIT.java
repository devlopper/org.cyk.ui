package org.cyk.ui.web.primefaces;

import org.cyk.utility.common.helper.UniformResourceLocatorHelper;

public class UniformResourceLocatorHelperIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Override
    protected void businesses() {
    	System.out.println("UniformResourceLocatorHelperIT.businesses() : "
    			+UniformResourceLocatorHelper.getInstance().getToken(UniformResourceLocatorHelper.TokenName.SCHEME));
    	//assertThat("Actor details created", UniformResourceLocatorHelper.getInstance().getToken(UniformResourceLocatorHelper.TokenName.SCHEME)!=null);
    }
    
    @Override protected void finds() {}
    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}
    
    
}
