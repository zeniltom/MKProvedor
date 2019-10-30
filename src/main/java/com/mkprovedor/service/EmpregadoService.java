package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Empregado;
import com.mkprovedor.repository.Empregados;
import com.mkprovedor.util.jpa.Transactional;

public class EmpregadoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Empregados empregadoes;

	@Transactional
	public void createNew(Empregado empregado) {
		empregadoes.persist(empregado);
	}

	@Transactional
	public void update(Empregado empregado) {
		empregadoes.update(empregado);
	}

	@Transactional
	public void delete(Empregado empregado) {
		empregadoes.delete(empregado);
	}

	@Transactional
	public List<Empregado> findAll() {
		return empregadoes.findAll();
	}

	@Transactional
	public Empregado findByEmail(Empregado empregado) {
		return empregadoes.findByEmail(empregado.getEmail());
	}

	@Transactional
	public List<Empregado> filter(Empregado empregado) {
		return empregadoes.filter(empregado);
	}

	public List<Empregado> findByNome(String nome) {
		return empregadoes.findByNome(nome);
	}
}
