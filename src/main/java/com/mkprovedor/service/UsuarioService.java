package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Usuario;
import com.mkprovedor.repository.Usuarios;
import com.mkprovedor.util.jpa.Transactional;

public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	@Transactional
	public void createNew(Usuario usuario) {
		usuarios.persist(usuario);
	}

	@Transactional
	public void update(Usuario usuario) {
		usuarios.update(usuario);
	}

	@Transactional
	public void delete(Usuario usuario) {
		usuarios.delete(usuario);
	}

	@Transactional
	public List<Usuario> findAll() {
		return usuarios.findAll();
	}

	@Transactional
	public Usuario findByEmail(Usuario usuario) {
		return usuarios.findByEmail(usuario.getEmail());
	}

	@Transactional
	public List<Usuario> filter(Usuario usuario) {
		return usuarios.filter(usuario);
	}

	public List<Usuario> findByNome(String nome) {
		return usuarios.findByNome(nome);
	}
}
