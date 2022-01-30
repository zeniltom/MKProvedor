package com.mkprovedor.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mkprovedor.model.Grupo;

public class Grupos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Grupo findById(Long id) {
		return this.entityManager.find(Grupo.class, id);
	}

	public void persist(Grupo grupo) {
		this.entityManager.persist(grupo);
	}

	public void update(Grupo grupo) {
		this.entityManager.merge(grupo);
	}

	public void delete(Grupo grupo) {
		this.entityManager.remove(this.findById(grupo.getId()));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Grupo> findByNome(Grupo grupo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Grupo.class);

		if (grupo.getNome() != null && !(grupo.getNome().equals("")))
			criteria.add(Restrictions.ilike("nome", "%" + grupo.getNome() + "%"));

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Grupo> findAll() {
		return entityManager.createQuery("SELECT m FROM Grupo m ORDER BY m.nome").getResultList();
	}
}