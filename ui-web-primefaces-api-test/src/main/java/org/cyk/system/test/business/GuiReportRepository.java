package org.cyk.system.test.business;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.system.root.business.impl.file.report.AbstractReportRepository;
import org.cyk.system.root.business.impl.file.report.ReportBasedOnDynamicBuilderAdapter;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class GuiReportRepository extends AbstractReportRepository implements Serializable {

	private static final long serialVersionUID = 6917567891985885124L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		INSTANCE = this;
	}
	
	@Override
	public void build() {
		addListener(new ReportBasedOnDynamicBuilderAdapter(){
        	@Override
        	public void report(ReportBasedOnDynamicBuilder<?> report,ReportBasedOnDynamicBuilderParameters<?> parameters) {
        		//parameters.setOwner("GuiComp");
        	}
        });
		
		/*addConfiguration(new ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable, Object>(
	    		RootBusinessLayer.getInstance().getParameterGenericReportBasedOnDynamicBuilder(),Actor.class,DefaultActorOutputDetails.class) {
			private static final long serialVersionUID = -1966207854828857772L;
			@Override
			public Object model(AbstractIdentifiable identifiable) {
				return new DefaultActorOutputDetails( (AbstractActor) identifiable);
			}
			@Override
			public Boolean useCustomIdentifiableCollection() {
				return Boolean.TRUE;
			}
			@Override
			public Collection<? extends AbstractIdentifiable> identifiables(ReportBasedOnDynamicBuilderParameters<Object> parameters) {
				return actorBusiness.findAll();
			}
		});*/
	}
	
	private static GuiReportRepository INSTANCE;
	public static GuiReportRepository getInstance() {
		return INSTANCE;
	}
	
		
}
