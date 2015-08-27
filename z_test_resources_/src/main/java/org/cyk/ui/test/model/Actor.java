package org.cyk.ui.test.model;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Setter;
import lombok.Getter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public class Actor extends AbstractActor implements Serializable {

	private static final long serialVersionUID = 1548374606816696414L;

} 
