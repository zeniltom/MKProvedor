package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.EmpregadoGrupo;
import com.mkprovedor.repository.EmpregadoGrupos;
import com.mkprovedor.util.jpa.Transactional;

public class EmpregadoGrupoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpregadoGrupos EmpregadoGrupos;

	@Transactional
	public void createNew(EmpregadoGrupo EmpregadoGrupo) {
		EmpregadoGrupos.persist(EmpregadoGrupo);
	}

	@Transactional
	public void update(EmpregadoGrupo EmpregadoGrupo) {
		EmpregadoGrupos.update(EmpregadoGrupo);
	}

	@Transactional
	public void delete(EmpregadoGrupo EmpregadoGrupo) {
		EmpregadoGrupos.delete(EmpregadoGrupo);
	}

	@Transactional
	public List<EmpregadoGrupo> findAll() {
		return EmpregadoGrupos.findAll();
	}
}
