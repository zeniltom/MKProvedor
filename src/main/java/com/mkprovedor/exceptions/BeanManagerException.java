package com.mkprovedor.exceptions;

public class BeanManagerException extends RuntimeException {

	private static final long serialVersionUID = 3638896684032922343L;

	public BeanManagerException() {
		super("Não pôde encontrar BeanManager no JNDI.");
	}

}
