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
import com.mkprovedor.model.UsuarioGrupo;

public class UsuarioGrupos implements Serializable {

	private static final String GRUPO = "grupo";

	private static final String USUARIO = "usuario";

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public UsuarioGrupo findById(Long id) {
		return this.entityManager.find(UsuarioGrupo.class, id);
	}

	public void persist(UsuarioGrupo usuarioGrupo) {
		this.entityManager.persist(usuarioGrupo);
	}

	public void update(UsuarioGrupo usuarioGrupo) {
		this.entityManager.merge(usuarioGrupo);
	}

	public void delete(UsuarioGrupo usuarioGrupo) {
		this.entityManager.remove(this.findByUsuarioANDGrupo(usuarioGrupo).get(0));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<UsuarioGrupo> filter(UsuarioGrupo usuarioGrupo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UsuarioGrupo.class);

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<UsuarioGrupo> findByUsuario(Usuario usuario) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UsuarioGrupo.class);
		criteria.createAlias(USUARIO, USUARIO);
		criteria.createAlias(GRUPO, GRUPO);

		if (usuario.getId() != null)
			criteria.add(Restrictions.eq("usuario.id", usuario.getId()));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<UsuarioGrupo> findByUsuarioANDGrupo(UsuarioGrupo usuarioGrupo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UsuarioGrupo.class);
		criteria.createAlias(USUARIO, USUARIO);
		criteria.createAlias(GRUPO, GRUPO);

		if (usuarioGrupo.getUsuario() != null && usuarioGrupo.getUsuario().getId() != null
				&& usuarioGrupo.getUsuario().getId() != 0)
			criteria.add(Restrictions.eq("usuario.id", usuarioGrupo.getUsuario().getId()));

		if (usuarioGrupo.getGrupo() != null && usuarioGrupo.getGrupo().getNome() != null
				&& !usuarioGrupo.getGrupo().getNome().equals(""))
			criteria.add(Restrictions.eq("grupo.nome", usuarioGrupo.getGrupo().getNome()));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioGrupo> findAll() {
		return entityManager.createQuery("SELECT m FROM UsuarioGrupo m").getResultList();
	}

}