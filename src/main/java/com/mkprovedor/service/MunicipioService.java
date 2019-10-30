package com.mkprovedor.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Municipio;
import com.mkprovedor.model.ENUf;
import com.mkprovedor.repository.Municipios;
import com.mkprovedor.util.jpa.Transactional;

public class MunicipioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Municipios municipios;

	@Transactional
	public void createNew(Municipio obj) {
		municipios.persist(obj);
	}

	@Transactional
	public List<Municipio> findAll() {
		return municipios.findAll();
	}

	@Transactional
	public List<Municipio> findByNome(String nome) {
		return municipios.findByNome(nome);
	}

	@Transactional
	public void update(Municipio obj) {
		municipios.update(obj);
	}

	@Transactional
	public void remove(Municipio obj) {
		municipios.delete(obj);
	}

	@Transactional
	public Municipio findById(Long id) {
		return municipios.findById(id);
	}

	@Transactional
	public List<Municipio> filter(Municipio obj) {
		return municipios.filter(obj);
	}

	@Transactional
	public List<Municipio> findByUF(ENUf uf) {
		return municipios.findByUF(uf);
	}
}