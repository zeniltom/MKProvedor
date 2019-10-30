package com.mkprovedor.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Mensalidade;

public class Mensalidades implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Mensalidade findById(Long id) {
		return this.entityManager.find(Mensalidade.class, id);
	}

	public void persist(Mensalidade mensalidade) {
		this.entityManager.persist(mensalidade);
	}

	public void update(Mensalidade mensalidade) {
		this.entityManager.merge(mensalidade);
	}

	public void delete(Mensalidade mensalidade) {
		this.entityManager.remove(this.findById(mensalidade.getId()));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Mensalidade> filter(Mensalidade mensalidade) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Mensalidade.class);

		if (mensalidade.getDescricao() != null && !mensalidade.getDescricao().equals(""))
			criteria.add(Restrictions.ilike("descricao", "%" + mensalidade.getDescricao() + "%"));

		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings("deprecation")
	public Mensalidade findByCliente(Cliente cliente) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Mensalidade.class);
		criteria.createAlias("cliente", "cliente");

		if (cliente != null && cliente.getId() != null)
			criteria.add(Restrictions.eq("cliente.id", cliente.getId()));
		else
			criteria.add(Restrictions.eq("cliente.id", (long) 0));

		return (Mensalidade) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Mensalidade> findAll() {
		return entityManager.createQuery("SELECT m FROM Mensalidade m ORDER BY m.cliente.nome, m.valor").getResultList();
	}
}