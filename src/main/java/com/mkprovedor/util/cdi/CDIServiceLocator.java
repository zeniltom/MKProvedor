package com.mkprovedor.util.cdi;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.mkprovedor.exceptions.BeanManagerException;

public class CDIServiceLocator {

	private CDIServiceLocator() {}

	private static BeanManager getBeanManager() {
		try {
			InitialContext initialContext = new InitialContext();
			return (BeanManager) initialContext.lookup("java:comp/env/BeanManager");
		} catch (NamingException e) {
			throw new BeanManagerException();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		BeanManager bm = getBeanManager();
		Set<Bean<?>> beans = bm.getBeans(clazz);

		if (beans == null || beans.isEmpty()) {
			return null;
		}

		Bean<T> bean = (Bean<T>) beans.iterator().next();

		CreationalContext<T> ctx = bm.createCreationalContext(bean);
		return (T) bm.getReference(bean, clazz, ctx);
	}

}