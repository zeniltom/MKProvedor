package com.mkprovedor.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mkprovedor.model.Historico;

public class Historicos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Historico findById(Long id) {
		return this.entityManager.find(Historico.class, id);
	}

	public void persist(Historico historico) {
		this.entityManager.persist(historico);
	}

	public void update(Historico historico) {
		this.entityManager.merge(historico);
	}

	public void delete(Historico historico) {
		this.entityManager.remove(this.findById(historico.getId()));
	}

	@SuppressWarnings("unchecked")
	public List<Historico> findAll() {
		return entityManager.createQuery("SELECT m FROM Historico m ORDER BY m.id").getResultList();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Historico> filter(Date dataInicio, Date dataFim) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Historico.class);

		if (dataInicio != null)
			criteria.add(Restrictions.ge("dataPagamento", dataInicio));

		if (dataFim != null)
			criteria.add(Restrictions.le("dataPagamento", dataFim));

		criteria.addOrder(Order.asc("dataPagamento"));
		criteria.addOrder(Order.asc("valor"));

		return criteria.list();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Historico> findByHistoricoDia(Date dataHoje) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Historico.class);

		if (dataHoje != null)
			criteria.add(Restrictions.eq("dataPagamento", dataHoje));

		criteria.addOrder(Order.asc("dataPagamento"));
		criteria.addOrder(Order.asc("valor"));

		return criteria.list();
	}
}