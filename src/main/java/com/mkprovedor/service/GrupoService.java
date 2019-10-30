package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Grupo;
import com.mkprovedor.repository.Grupos;
import com.mkprovedor.util.jpa.Transactional;

public class GrupoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Grupos grupos;

	@Transactional
	public void createNew(Grupo Grupo) {
		grupos.persist(Grupo);
	}

	@Transactional
	public List<Grupo> findByNome(Grupo grupoSelecionado) {
		return grupos.findByNome(grupoSelecionado);
	}

}
