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
import com.mkprovedor.model.EmpregadoGrupo;

public class EmpregadoGrupos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public EmpregadoGrupo findById(Long id) {
		return this.entityManager.find(EmpregadoGrupo.class, id);
	}

	public void persist(EmpregadoGrupo empregadoGrupo) {
		this.entityManager.persist(empregadoGrupo);
	}

	public void update(EmpregadoGrupo empregadoGrupo) {
		this.entityManager.merge(empregadoGrupo);
	}

	public void delete(EmpregadoGrupo empregadoGrupo) {
		this.entityManager.remove(this.findByEmpregadoANDGrupo(empregadoGrupo).get(0));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<EmpregadoGrupo> filter(EmpregadoGrupo empregadoGrupo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(EmpregadoGrupo.class);

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<EmpregadoGrupo> findByEmpregado(Empregado empregado) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(EmpregadoGrupo.class);
		criteria.createAlias("empregado", "empregado");
		criteria.createAlias("grupo", "grupo");

		if (empregado.getId() != null)
			criteria.add(Restrictions.eq("empregado.id", empregado.getId()));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<EmpregadoGrupo> findByEmpregadoANDGrupo(EmpregadoGrupo empregadoGrupo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(EmpregadoGrupo.class);
		criteria.createAlias("empregado", "empregado");
		criteria.createAlias("grupo", "grupo");

		if (empregadoGrupo.getEmpregado() != null && empregadoGrupo.getEmpregado().getId() != null
				&& empregadoGrupo.getEmpregado().getId() != 0)
			criteria.add(Restrictions.eq("empregado.id", empregadoGrupo.getEmpregado().getId()));

		if (empregadoGrupo.getGrupo() != null && empregadoGrupo.getGrupo().getNome() != null
				&& !empregadoGrupo.getGrupo().getNome().equals(""))
			criteria.add(Restrictions.eq("grupo.nome", empregadoGrupo.getGrupo().getNome()));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<EmpregadoGrupo> findAll() {
		return entityManager.createQuery("SELECT m FROM EmpregadoGrupo m").getResultList();
	}

}