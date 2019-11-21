package com.mkprovedor.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.mkprovedor.model.Historico;
import com.mkprovedor.repository.Historicos;
import com.mkprovedor.util.jpa.Transactional;

public class HistoricoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Historicos historicos;

	@Transactional
	public void createNew(Historico historico) {
		historicos.persist(historico);
	}

	@Transactional
	public List<Historico> filter(Date dataInicio, Date dataFim) {
		return historicos.filter(dataInicio, dataFim);
	}

	@Transactional
	public List<Historico> findByHistoricoDia(Date dataHoje) {
		return historicos.findByHistoricoDia(dataHoje);
	}
}
