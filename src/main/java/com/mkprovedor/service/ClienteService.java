package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.repository.Clientes;
import com.mkprovedor.util.jpa.Transactional;

public class ClienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Clientes clientes;

	@Transactional
	public void createNew(Cliente cliente) {
		clientes.persist(cliente);
	}

	@Transactional
	public void update(Cliente cliente) {
		clientes.update(cliente);
	}

	@Transactional
	public void delete(Cliente cliente) {
		clientes.delete(cliente);
	}

	@Transactional
	public List<Cliente> filter(Cliente empregado) {
		return clientes.filter(empregado);
	}

	@Transactional
	public List<Cliente> findByNome(String nome) {
		return clientes.findByNome(nome);
	}

	@Transactional
	public List<Cliente> findByCpfCnpj(Cliente cliente) {
		return clientes.findByCpfCnpj(cliente);
	}

	@Transactional
	public List<Cliente> findByDesativados() {
		return clientes.findByDesativados();
	}

	@Transactional
	public int findByTotalClientes() {
		return clientes.findByTotalClientes();
	}

	@Transactional
	public int findByTotalClientesLivres() {
		return clientes.findByTotalClientesLivres();
	}

	@Transactional
	public int findByTotalClientesBloqueados() {
		return clientes.findByTotalClientesBloqueados();
	}

}
