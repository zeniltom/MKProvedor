package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Contrato;
import com.mkprovedor.repository.Contratos;
import com.mkprovedor.util.jpa.Transactional;

public class ContratoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Contratos contratos;

	@Transactional
	public void createNew(Contrato contrato) {
		contratos.persist(contrato);
	}

	@Transactional
	public void update(Contrato contrato) {
		contratos.update(contrato);
	}

	@Transactional
	public void delete(Contrato contrato) {
		contratos.delete(contrato);
	}

	@Transactional
	public List<Contrato> findAll() {
		return contratos.findAll();
	}

	@Transactional
	public List<Contrato> filter(Contrato contrato) {
		return contratos.filter(contrato);
	}

	public Contrato findByCliente(Cliente cliente) {
		return contratos.findByCliente(cliente);
	}

	@Transactional
	public void deleteByCliente(Cliente cliente) {
		contratos.deleteByCliente(cliente);
	}
}
