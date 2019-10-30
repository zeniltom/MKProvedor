package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Servico;
import com.mkprovedor.repository.Servicos;
import com.mkprovedor.util.jpa.Transactional;

public class ServicoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Servicos servicos;

	@Transactional
	public void createNew(Servico servico) {
		servicos.persist(servico);
	}

	@Transactional
	public void update(Servico servico) {
		servicos.update(servico);
	}

	@Transactional
	public void delete(Servico servico) {
		servicos.delete(servico);
	}

	@Transactional
	public List<Servico> findAll() {
		return servicos.findAll();
	}

	@Transactional
	public List<Servico> filter(Servico empregado) {
		return servicos.filter(empregado);
	}
}
