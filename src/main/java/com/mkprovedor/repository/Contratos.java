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
import com.mkprovedor.model.Contrato;

public class Contratos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Contrato findById(Long id) {
		return this.entityManager.find(Contrato.class, id);
	}

	public void persist(Contrato contrato) {
		this.entityManager.persist(contrato);
	}

	public void update(Contrato contrato) {
		this.entityManager.merge(contrato);
	}

	public void delete(Contrato contrato) {
		this.entityManager.remove(this.findById(contrato.getId()));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Contrato> filter(Contrato contrato) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Contrato.class);

		if (contrato.getDescricao() != null && !contrato.getDescricao().equals(""))
			criteria.add(Restrictions.ilike("descricao", "%" + contrato.getDescricao() + "%"));

		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Contrato> findAll() {
		return entityManager.createQuery("SELECT m FROM Contrato m ORDER BY m.descricao").getResultList();
	}

	@SuppressWarnings("deprecation")
	public Contrato findByCliente(Cliente cliente) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Contrato.class);
		criteria.createAlias("cliente", "cliente");

		if (cliente != null && cliente.getId() != null)
			criteria.add(Restrictions.eq("cliente.id", cliente.getId()));
		else
			criteria.add(Restrictions.eq("cliente.id", (long) 0));

		return (Contrato) criteria.uniqueResult();
	}

	public void deleteByCliente(Cliente cliente) {
		try {
			this.entityManager.createQuery("DELETE FROM Contrato WHERE cliente.id = :idCliente")
					.setParameter("idCliente", cliente.getId()).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}