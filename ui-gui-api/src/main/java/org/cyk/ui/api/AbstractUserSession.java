package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.model.AbstractHierarchyNode;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractUserSession<NODE,MODEL extends AbstractHierarchyNode> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 958643519183802472L;

	private static final Map<String, AbstractUserSession<?,?>> USER_SESSION_MAP = new HashMap<>();
	
	@Inject protected UserAccountBusiness userAccountBusiness;
	
	protected String identifier;
	protected Long timestamp;
	protected Locale locale = Locale.FRENCH;
	protected UserAccount userAccount;
	protected UIMenu applicationMenu,referenceEntityMenu,securityMenu,userAccountMenu,mobileApplicationMenu;
	protected AbstractTree<NODE,MODEL> navigatorTree;
	protected String notificationChannel;
	protected Boolean logoutCalled,isAdministrator,isManager;
	protected UIMenu contextualMenu;
	
	//FIXME not called. because of Session Scope ?
	/*
	public void notificationFired(@Observes Notification notification){
		System.out.println("AbstractUserSession.notificationFired()");
		/*FacesMessage facesMessage = message(notification);
		if(facesMessage==null)
			return;
		publish("/"+UIManager.PUSH_NOTIFICATION_CHANNEL+"/"+ "*", facesMessage);
    }
	*/
	
	public void notificationFired(Notification notification,FacesMessage facesMessage){
		userAccountBusiness.createSessionNotification(userAccount,notification);
		if(!userAccount.getSessionNotifications().isEmpty())
			__notificationFired__(notification,facesMessage);
	}
	
	protected void __notificationFired__(Notification notification,FacesMessage facesMessage){}
	
	public Party getUser(){
		return userAccount == null?null:userAccount.getUser();
	}
	
	public Boolean getLoggedIn() {
		return userAccount!=null;
	}
	
	public void init(UserAccount userAccount){
		logoutCalled = Boolean.FALSE;
		isAdministrator = inject(UserAccountBusiness.class).hasRole(userAccount, inject(RoleBusiness.class).find(RootConstant.Code.Role.ADMINISTRATOR));
		isManager = inject(UserAccountBusiness.class).hasRole(userAccount, inject(RoleBusiness.class).find(RootConstant.Code.Role.MANAGER));
		
		setUserAccount(userAccount);
		Collection<SystemMenu> systemMenus = MenuManager.getInstance().systemMenus(this);
		if(Boolean.TRUE.equals(isAdministrator)){
			setApplicationMenu(MenuManager.getInstance().applicationMenu(this,systemMenus));	
		}else{
			
		}
		
		setMobileApplicationMenu(MenuManager.getInstance().mobileApplicationMenu(this,systemMenus));
		setReferenceEntityMenu(MenuManager.getInstance().referenceEntityMenu(this,systemMenus));
		setSecurityMenu(MenuManager.getInstance().securityMenu(this));
		setUserAccountMenu(MenuManager.getInstance().userAccountMenu(this));
		setContextualMenu(MenuManager.getInstance().sessionContextualMenu(this));
		//setNavigatorTree((AbstractTree<NODE, MODEL>) MenuManager.getInstance().sessionNavigatorTree(this,systemMenus));
		
		identifier = userAccount.getIdentifier()+" - "+System.currentTimeMillis()+RandomStringUtils.randomAlphanumeric(10);
		notificationChannel = "/"+UIManager.PUSH_NOTIFICATION_CHANNEL+"/"+userAccount.getIdentifier();
		timestamp = System.currentTimeMillis();
		
		register(this);
		/*
		Collection<AbstractUserSession> userSessions = find(userAccount);
		if(userSessions.size()>1){
			//notificationFired(notification, facesMessage);
		}
		*/
		logTrace("User session of {} initialised",getUserAccount().getCredentials().getUsername());
	}
	
	public void showNotifications(){
		//System.out.println("AbstractUserSession.showNotifications() : "+userAccount.getSessionNotifications().size());
		MessageManager.INSTANCE.clear();//to avoid showing old message in case there are
		MessageManager.INSTANCE.notifications(userAccount.getSessionNotifications()).showGrowl();
		logTrace("Show {} notifications to username {}",userAccount.getSessionNotifications().size(),getUserAccount().getCredentials().getUsername());
	}
	
	protected abstract void __logout__();
	
	public void logout(){
		userAccountBusiness.disconnect(getUserAccount());
		__logout__();
		unRegister(this);
		logoutCalled = Boolean.TRUE;
		__invalidateSession__();
		__navigateToPublicIndex__();
		logTrace("Username {} has logged out", userAccount.getCredentials().getUsername());
	}
	
	public void autoLogout(){
		userAccountBusiness.disconnect(getUserAccount());
		__logout__();
		unRegister(this);
		__navigateToPublicIndex__();
		logTrace("Username {} has logged out", userAccount.getCredentials().getUsername());
	}
	
	protected abstract void __navigateToPublicIndex__();
	
	protected abstract void __invalidateSession__();

	/*public abstract Boolean getIsAdministrator();

	public abstract Boolean getIsManager();*/
	
	public Boolean hasRole(String roleCode){
		for(Role role : userAccount.getRoles())
			if(role.getCode().equals(roleCode))
				return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	public Boolean isUserInstanceOf(Class<?> aClass){
		return userAccount!=null && userAccount.getUser()!=null && aClass.isAssignableFrom(userAccount.getUser().getClass());
	}
	
	public static void register(AbstractUserSession<?,?> userSession){
		USER_SESSION_MAP.put(userSession.getIdentifier(), userSession);
	}
	
	public static void unRegister(AbstractUserSession<?,?> userSession){
		USER_SESSION_MAP.remove(userSession.getIdentifier());
	}
	
	public static Collection<AbstractUserSession<?,?>> find(UserAccount userAccount){
		Collection<AbstractUserSession<?,?>> collection = new ArrayList<>();
		for(Entry<String, AbstractUserSession<?,?>> entry : USER_SESSION_MAP.entrySet())
			if(entry.getValue().getUserAccount().equals(userAccount))
				collection.add(entry.getValue());
		return collection;
	}
	
	public static void logout(UserAccount userAccount){
		for(AbstractUserSession<?,?> session : find(userAccount))
			session.logout();
	}
}
