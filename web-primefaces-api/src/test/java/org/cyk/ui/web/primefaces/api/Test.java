package org.cyk.ui.web.primefaces.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("/login=user");
		list.add("/private/__role__/__administrator__/**=user");
		list.add("/private/__role__/__manager__/**=user");
		list.add("/private/__role__/__businessactor__/**=user");
		list.add("/private/__role__/__settingmanager__/**=user");
		list.add("/private/__role__/__securitymanager__/**=user");
		list.add("/private/**=user");
		list.add("/private/__role__/__salemanager__/**=user");
		list.add("/private/__role__/__stockmanager__/**=user");
		list.add("/private/__role__/__humanresourcesmanager__/**=user");
		list.add("/private/__role__/__customermanager__/**=user");
		list.add("/private/__role__/__productionmanager__/**=user");
		list.add("/private/__role__/__inputter__/**=user");
		list.add("/private/__role__/__finaliser__/**=user");
		list.add("/private/__role__/__cashier__/**=user");
		
		for(String s : list)
			System.out.println(s);
		
		Collections.sort(list);
		
		System.out.println("---------------------------------------------------------------");
		for(String s : list)
			System.out.println(s);

	}

}
