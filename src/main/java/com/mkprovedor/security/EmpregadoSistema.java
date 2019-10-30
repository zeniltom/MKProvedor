package com.mkprovedor.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.mkprovedor.model.Empregado;

public class EmpregadoSistema extends User {

	private static final long serialVersionUID = 1L;

	private Empregado empregado;

	public EmpregadoSistema(Empregado empregado, Collection<? extends GrantedAuthority> authorities) {
		super(empregado.getEmail(), empregado.getSenha(), authorities);
		this.empregado = empregado;
	}

	public Empregado getEmpregado() {
		return empregado;
	}

	public void setEmpregado(Empregado empregado) {
		this.empregado = empregado;
	}

}
