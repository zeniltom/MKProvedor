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
import com.mkprovedor.model.Usuario;
import com.mkprovedor.repository.Empregados;
import com.mkprovedor.repository.Usuarios;
import com.mkprovedor.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuarios usuarios = CDIServiceLocator.getBean(Usuarios.class);
		Empregados empregados = CDIServiceLocator.getBean(Empregados.class);
		Usuario usuario = usuarios.findByLogin(login);

		UsuarioSistema user = null;

		if (usuario != null) {
			Empregado empregado = empregados.findByUsuario(usuario).get(0);

			user = new UsuarioSistema(usuario, empregado, getGrupos(usuario));
		}

		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Grupo grupo : usuario.getGrupos())
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));

		return authorities;
	}

}
