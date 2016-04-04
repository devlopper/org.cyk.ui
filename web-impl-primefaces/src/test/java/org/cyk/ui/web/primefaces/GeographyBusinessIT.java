package org.cyk.ui.web.primefaces;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.persistence.api.geography.LocalityDao;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class GeographyBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
	
	@Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
	
	@Inject private LocalityBusiness localityBusiness;  
	@Inject private LocalityTypeBusiness localityTypeBusiness;
	
	@Inject private LocalityDao localityDao;  
	
	private LocalityType continent,country,state,region,department,city;
	private Locality africa,america;
	private Locality coteDivoire,burkinaFaso,ghana,benin;
	private Locality coteDivoireNorthState,coteDivoireSouthState,coteDivoireEastState,coteDivoireWestState
		,burkinanFasoNorthState,burkinanFasoSouthState,burkinanFasoEastState,burkinanFasoWestState
		,ghanaNorthState,ghanaSouthState,ghanaEastState,ghanaWestState
		,beninNorthState,beninSouthState,beninEastState,beninWestState;
	private Locality coteDivoireSouthStateLaguneRegion,coteDivoireSouthStateSassandraRegion;
	private Locality coteDivoireSouthStateLaguneRegionDepartment1,coteDivoireSouthStateLaguneRegionDepartment2,coteDivoireSouthStateLaguneRegionDepartment3;
	private Locality abidjan,bonoua,sassandra,sanpedro;
	
	protected void createAll() {
		Integer level = 0;
		create(continent = new LocalityType(null, String.valueOf(level++), "Continent"));
		create(country = new LocalityType(continent, String.valueOf(level++), "Country"));
		create(state = new LocalityType(country, String.valueOf(level++), "State"));
		create(region = new LocalityType(state, String.valueOf(level++), "Region"));
		create(department = new LocalityType(region, String.valueOf(level++), "Department"));
		create(city = new LocalityType(department, String.valueOf(level++), "City"));
		
		level = 0;
		create(africa = new Locality(null, continent, String.valueOf(level++), "Africa"));
		
		create(america = new Locality(null, continent, String.valueOf(level++), "America"));
		
		create(coteDivoire = createLocality(africa, country, String.valueOf(level++), "Ivory Coast"));
		create(burkinaFaso = createLocality(africa, country, String.valueOf(level++), "Burkina Faso"));
		
		create(coteDivoireNorthState = createLocality(coteDivoire, state, String.valueOf(level++), "Le grand Nord"));
		create(coteDivoireSouthState = createLocality(coteDivoire, state, String.valueOf(level++), "Le grand Sud"));
		/*
		create(coteDivoireSouthStateLaguneRegion = createLocality(coteDivoireSouthState, region, String.valueOf(level++), "Les lagunes"));
		create(coteDivoireSouthStateSassandraRegion = createLocality(coteDivoireSouthState, region, String.valueOf(level++), "Le sassandra"));
		
		create(coteDivoireSouthStateLaguneRegionDepartment1 = createLocality(coteDivoireSouthStateLaguneRegion, department, String.valueOf(level++), "Abidjanaise"));
		create(coteDivoireSouthStateLaguneRegionDepartment2 = createLocality(coteDivoireSouthStateLaguneRegion, department, String.valueOf(level++), "Bassam"));
		
		create(abidjan = createLocality(coteDivoireSouthStateLaguneRegionDepartment1, city, String.valueOf(level++), "Abidjan"));
		*/
	}
	
    @Override
    protected void create() {
        //continent = localityTypeBusiness.instanciateOne("Continent");
    	/*LocalityType t1 = new LocalityType(null, "C", "Continent");
        genericBusiness.create(t1);
        Assert.assertNotNull(t1.getIdentifier());
        Locality l = new Locality(null, t1, "A");
        genericBusiness.create(l);
        Assert.assertNotNull(l.getIdentifier());*/
    }

    @Override
    protected void read() {
        //Assert.assertNotNull(localityBusiness.find("A"));
    }
    
    @Override
    protected void update() {
        /*Locality l =localityBusiness.find("A");
        l.setName("Dabou");
        Assert.assertEquals("Dabou", localityBusiness.find("A").getName());*/
    }
    
    @Override
    protected void delete() {
       /*genericBusiness.delete(localityBusiness.find("A"));
       Assert.assertNull(localityBusiness.find("A"));*/
    }
		
    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	installApplication();
    	createAll();
    	//create(new Country(new Locality(null, RootBusinessLayer.getInstance().getCountryLocalityType(), "MyCountry", "TheCountry"),225));
    	//Assert.assertNotNull(localityTypeBusiness.find(LocalityType.COUNTRY));
    	//System.out.println(localityBusiness.findByType(RootBusinessLayer.getInstance().getCountryLocalityType()));
    	
    	//System.out.println(localityDao.readByParent(localityBusiness.find(coteDivoireSouthStateLaguneRegionDepartment1.getIdentifier())));
    	//showChildren(coteDivoireSouthStateLaguneRegionDepartment1);
    	//showChildren(africa);
    	//localityBusiness.delete(localityBusiness.find(coteDivoireSouthStateLaguneRegionDepartment1.getIdentifier()));
    	//localityBusiness.delete(abidjan);
    	
    	
    	showChildren(africa);
    	showChildren(america);
    	
    	move(coteDivoire, america);
    	
    	//localityBusiness.move(localityBusiness.find(coteDivoireSouthStateLaguneRegionDepartment1.getIdentifier()), 
    	//		localityBusiness.find(coteDivoireSouthStateSassandraRegion.getIdentifier()));
    	
    	/*
    	localityBusiness.move(localityBusiness.find(coteDivoire.getIdentifier()), 
    			localityBusiness.find(america.getIdentifier()));
    	*/
    	System.out.println("------     After moving     -----");
    	showChildren(africa);
    	showChildren(america);
    	
    	
    }

    /**/
    
    private void move(Locality locality,Locality parent){
    	locality = localityBusiness.find(locality.getIdentifier());
    	parent = localityBusiness.find(parent.getIdentifier());
    	localityBusiness.move(locality, parent);
    }
    
    private void showChildren(Locality locality){
    	locality = localityBusiness.find(locality.getIdentifier());
    	List<Locality> localities = new ArrayList<>(localityBusiness.findByParent(locality));
    	localities.add(0, locality);
    	for(Locality l : localities){
    		System.out.println(l.getNode().getIdentifier()+" - "+l+"("+localityDao.readParent(l)+")");
    	}
    }

    private Locality createLocality(Locality parent,LocalityType type,String code,String name){
    	return new Locality(localityBusiness.find(parent.getIdentifier()), localityTypeBusiness.find(type.getIdentifier()), code, name);
    }

}
