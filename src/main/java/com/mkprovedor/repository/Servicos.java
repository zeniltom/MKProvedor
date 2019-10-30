package com.mkprovedor.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mkprovedor.model.Servico;

public class Servicos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Servico findById(Long id) {
		return this.entityManager.find(Servico.class, id);
	}

	public void persist(Servico servico) {
		this.entityManager.persist(servico);
	}

	public void update(Servico servico) {
		this.entityManager.merge(servico);
	}

	public void delete(Servico servico) {
		this.entityManager.remove(this.findById(servico.getId()));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Servico> filter(Servico servico) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Servico.class);

		if (servico.getDescricao() != null && !servico.getDescricao().equals(""))
			criteria.add(Restrictions.ilike("descricao", "%" + servico.getDescricao() + "%"));

		criteria.addOrder(Order.asc("descricao"));
		criteria.addOrder(Order.asc("tipoPlano"));
		criteria.addOrder(Order.asc("tipoServico"));
		criteria.addOrder(Order.asc("valorPlano"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Servico> findAll() {
		return entityManager.createQuery("SELECT m FROM Servico m ORDER BY m.descricao, m.tipoPlano").getResultList();
	}
}