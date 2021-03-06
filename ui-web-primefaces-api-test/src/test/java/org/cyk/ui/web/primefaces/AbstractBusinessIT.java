package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.BusinessIntegrationTestHelper;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.RootBusinessTestHelper;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.business.impl.validation.DefaultValidator;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.business.impl.validation.ValidatorMap;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;
import org.cyk.system.root.persistence.impl.PersistenceIntegrationTestHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.test.ArchiveBuilder;
import org.cyk.utility.test.integration.AbstractIntegrationTestJpaBased;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public abstract class AbstractBusinessIT extends AbstractIntegrationTestJpaBased {
	private static final long serialVersionUID = 7531234257367131255L;
	
	static {
		ClassHelper.getInstance().map(ApplicationBusinessImpl.Listener.class, ApplicationBusinessAdapter.class,Boolean.FALSE);
	}
	
	@Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
	
	@Inject protected ExceptionUtils exceptionUtils;
	@Inject protected DefaultValidator defaultValidator;
	@Inject private GenericDaoImpl g;
	@Inject protected GenericBusiness genericBusiness;
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected RootBusinessTestHelper rootTestHelper;
	
	@Inject protected ValidatorMap validatorMap;// = ValidatorMap.getInstance();
	@Inject protected RootBusinessLayer rootBusinessLayer;
	
    @Override
    public EntityManager getEntityManager() {
        return g.getEntityManager();
    }
    
    @Override
    protected void populate() {}
	 
    @Override
    protected void _execute_() {
        super._execute_();
        create();    
        read(); 
        update();    
        delete();    
        finds();
        businesses();
    }
    
	protected abstract void finds();
	
	protected abstract void businesses();
	/*
	@SuppressWarnings("unchecked")
    protected <T extends AbstractIdentifiable> T create(T anObject){
        return (T) genericBusiness.create(anObject);
    }*/
	
	/* Shortcut */
    
    protected AbstractIdentifiable create(AbstractIdentifiable object){
        return genericBusiness.create(object);
    }
    
    protected AbstractIdentifiable update(AbstractIdentifiable object){
        return genericBusiness.update(object);
    }
    
    protected void validate(Object object){
        if(object==null)
            return;
        @SuppressWarnings("unchecked")
        AbstractValidator<Object> validator = (AbstractValidator<Object>) validatorMap.validatorOf(object.getClass());
        if(validator==null){
            //log.warning("No validator has been found. The default one will be used");
            //validator = defaultValidator;
            return;
        }
        try {
            validator.validate(object);
        } catch (Exception e) {}
        
        //if(!Boolean.TRUE.equals(validator.isSuccess()))
         //   ystem.out.println(validator.getMessagesAsString());
        
    }
    
    protected void installApplication(){
    	RootBusinessLayer.getInstance().installApplication();
    }
    
    public static Archive<?> createRootDeployment() {
        return  
                new ArchiveBuilder().create().getArchive().
                    addClasses(BusinessIntegrationTestHelper.classes()).
                    addPackages(Boolean.FALSE, BusinessIntegrationTestHelper.packages())
                    .addClasses(RootBusinessLayer.class,RootBusinessTestHelper.class)
                    .addClasses(PersistenceIntegrationTestHelper.classes())
                //_deploymentOfPackages("org.cyk.system.root").getArchive()
              
                //.addPackages(Boolean.FALSE,BusinessIntegrationTestHelper.PACKAGES)
                //.addClasses(BusinessIntegrationTestHelper.CLASSES)
                //.addPackage(ExceptionUtils.class.getPackage())
                //.addPackage(PersonValidator.class.getPackage())
                ;
    }
    
    public static class ApplicationBusinessAdapter extends ApplicationBusinessImpl.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@Override
		public void installationStarted(Installation installation) {
			super.installationStarted(installation);
			installation.setIsCreateAccounts(Boolean.FALSE);
			installation.getApplication().setWebContext("gui-primefaces");
			installation.getApplication().setName("GuiApp");
		}
		
    }
}
