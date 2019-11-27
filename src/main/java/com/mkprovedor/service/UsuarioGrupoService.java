package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Usuario;
import com.mkprovedor.model.UsuarioGrupo;
import com.mkprovedor.repository.UsuarioGrupos;
import com.mkprovedor.util.jpa.Transactional;

public class UsuarioGrupoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioGrupos usuarioGrupos;

	@Transactional
	public void createNew(UsuarioGrupo usuarioGrupo) {
		usuarioGrupos.persist(usuarioGrupo);
	}

	@Transactional
	public void update(UsuarioGrupo usuarioGrupo) {
		usuarioGrupos.update(usuarioGrupo);
	}

	@Transactional
	public void delete(UsuarioGrupo usuarioGrupo) {
		usuarioGrupos.delete(usuarioGrupo);
	}

	public List<UsuarioGrupo> findByEmpregado(Usuario usuario) {
		return usuarioGrupos.findByUsuario(usuario);
	}

	@Transactional
	public List<UsuarioGrupo> findAll() {
		return usuarioGrupos.findAll();
	}
}
