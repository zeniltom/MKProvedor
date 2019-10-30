package com.mkprovedor.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mkprovedor.model.Empregado;
import com.mkprovedor.model.Grupo;
import com.mkprovedor.repository.Empregados;
import com.mkprovedor.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Empregados empregadoes = CDIServiceLocator.getBean(Empregados.class);
		Empregado empregado = empregadoes.findByLogin(login);

		EmpregadoSistema user = null;

		if (empregado != null) {
			user = new EmpregadoSistema(empregado, getGrupos(empregado));
		}

		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Empregado empregado) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Grupo grupo : empregado.getGrupos())
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));

		return authorities;
	}
}
