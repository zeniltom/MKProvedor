package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Chamado;
import com.mkprovedor.repository.Chamados;
import com.mkprovedor.util.jpa.Transactional;

public class ChamadoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Chamados chamados;

	@Transactional
	public void createNew(Chamado chamado) {
		chamados.persist(chamado);
	}

	@Transactional
	public void update(Chamado chamado) {
		chamados.update(chamado);
	}

	@Transactional
	public void delete(Chamado chamado) {
		chamados.delete(chamado);
	}

	@Transactional
	public List<Chamado> findAll() {
		return chamados.findAll();
	}

	@Transactional
	public List<Chamado> findByAbertos(Chamado chamado) {
		return chamados.findByAbertos(chamado);
	}

	public List<Chamado> findByFechados(Chamado chamado) {
		return chamados.findByFechados(chamado);
	}
}
