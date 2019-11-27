package com.mkprovedor.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mkprovedor.model.Empregado;
import com.mkprovedor.model.Usuario;

public class Empregados implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Empregado findById(Long id) {
		return this.entityManager.find(Empregado.class, id);
	}

	public void persist(Empregado empregado) {
		this.entityManager.persist(empregado);
	}

	public void update(Empregado empregado) {
		this.entityManager.merge(empregado);
	}

	public void delete(Empregado empregado) {
		this.entityManager.remove(this.findById(empregado.getId()));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Empregado> filter(Empregado empregado) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Empregado.class);

		if (empregado.getNome() != null && !empregado.getNome().equals(""))
			criteria.add(Restrictions.ilike("nome", "%" + empregado.getNome() + "%"));

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Empregado> findByNome(String nome) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Empregado.class);

		if (nome != null && !nome.equals(""))
			criteria.add(Restrictions.ilike("nome", "%" + nome + "%"));

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	@SuppressWarnings({ "unchecked" })
	public List<Empregado> findByUsuario(Usuario usuario) {
		Long idLogado = null;
		if (usuario != null)
			idLogado = usuario.getId();
		else
			idLogado = (long) 0;

		return entityManager
				.createQuery("SELECT m FROM Empregado m WHERE m.usuario.id = " + idLogado + " ORDER by m.usuario.nome")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Empregado> findAll() {
		return entityManager.createQuery("Select m from Empregado m order by m.nome").getResultList();
	}

}