package com.mkprovedor.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mkprovedor.model.Chamado;

public class Chamados implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Chamado findById(Long id) {
		return this.entityManager.find(Chamado.class, id);
	}

	public void persist(Chamado chamado) {
		this.entityManager.persist(chamado);
	}

	public void update(Chamado chamado) {
		this.entityManager.merge(chamado);
	}

	public void delete(Chamado chamado) {
		this.entityManager.remove(this.findById(chamado.getId()));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Chamado> findByAbertos(Chamado chamado) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Chamado.class);

		if (chamado.getDescricao() != null && !chamado.getDescricao().equals(""))
			criteria.add(Restrictions.ilike("descricao", "%" + chamado.getDescricao() + "%"));

		criteria.add(Restrictions.eq("situacao", true));

		criteria.addOrder(Order.desc("dataChamado"));
		criteria.addOrder(Order.asc("id"));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Chamado> findByFechados(Chamado chamado) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Chamado.class);

		if (chamado.getDescricao() != null && !chamado.getDescricao().equals(""))
			criteria.add(Restrictions.ilike("descricao", "%" + chamado.getDescricao() + "%"));

		criteria.add(Restrictions.eq("situacao", false));

		criteria.addOrder(Order.desc("dataChamado"));
		criteria.addOrder(Order.asc("id"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Chamado> findAll() {
		return entityManager.createQuery("SELECT m FROM Chamado m ORDER BY m.nome").getResultList();
	}
}