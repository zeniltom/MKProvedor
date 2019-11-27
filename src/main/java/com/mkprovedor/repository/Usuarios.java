package com.mkprovedor.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mkprovedor.model.Usuario;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Usuario findById(Long id) {
		return this.entityManager.find(Usuario.class, id);
	}

	public void persist(Usuario usuario) {
		this.entityManager.persist(usuario);
	}

	public void update(Usuario usuario) {
		this.entityManager.merge(usuario);
	}

	public void delete(Usuario usuario) {
		this.entityManager.remove(this.findById(usuario.getId()));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Usuario> filter(Usuario usuario) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		if (usuario.getNome() != null && !usuario.getNome().equals(""))
			criteria.add(Restrictions.ilike("nome", "%" + usuario.getNome() + "%"));

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	@SuppressWarnings("deprecation")
	public Usuario findByLogin(String login) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		if (login != null && !login.equals(""))
			criteria.add(Restrictions.like("login", login));

		return (Usuario) criteria.uniqueResult();
	}

	@SuppressWarnings("deprecation")
	public Usuario findByEmail(String email) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		if (email != null && !email.equals(""))
			criteria.add(Restrictions.like("email", email));

		return (Usuario) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findAll() {
		return entityManager.createQuery("Select m from Usuario m order by m.nome").getResultList();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Usuario> findByNome(String nome) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		if (nome != null && !nome.equals(""))
			criteria.add(Restrictions.ilike("nome", "%" + nome + "%"));

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}
}