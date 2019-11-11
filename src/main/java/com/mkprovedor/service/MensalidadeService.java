package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Mensalidade;
import com.mkprovedor.repository.Mensalidades;
import com.mkprovedor.util.jpa.Transactional;

public class MensalidadeService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Mensalidades mensalidades;

	@Transactional
	public void createNew(Mensalidade mensalidade) {
		mensalidades.persist(mensalidade);
	}

	

	

	

	@Transactional
	public List<Mensalidade> findAll() {
		return mensalidades.findAll();
	}

	@Transactional
	public Mensalidade findByCliente(Cliente cliente) {
		return mensalidades.findByCliente(cliente);
	}

}
