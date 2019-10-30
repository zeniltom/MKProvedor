package com.mkprovedor.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.mkprovedor.model.Municipio;
import com.mkprovedor.model.ENUf;

public class Municipios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Municipio findById(Long id) {
		return manager.find(Municipio.class, id);
	}

	public void persist(Municipio obj) {
		manager.persist(obj);
	}

	public void update(Municipio obj) {
		manager.merge(obj);
	}

	public void delete(Municipio obj) {
		manager.remove(manager.find(Municipio.class, obj.getId()));
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Municipio> filter(Municipio obj) {
		Session session = (Session) manager.getDelegate();
		Criteria criteria = session.createCriteria(Municipio.class);

		if (obj.getId() != 0)
			criteria.add(Restrictions.eq("id", obj.getId()));

		if (obj.getNome() != null)
			if (!obj.getNome().equals(""))
				criteria.add(Restrictions.ilike("nome", "%" + obj.getNome() + "%"));

		if (obj.getCadastroUnico() != 0)
			criteria.add(Restrictions.ilike("cadastroUnico", "%" + obj.getCadastroUnico() + "%"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Municipio> findByNome(String nome) {
		Query query = manager.createQuery("Select m from Municipio m where m.nome LIKE :nome order by m.nome");
		query.setParameter("nome", nome + "%");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Municipio> findByUF(ENUf uf) {
		Query query = manager.createQuery("Select m from Municipio m where m.uf=:iduf order by m.nome");
		query.setParameter("iduf", uf.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Municipio> findAll() {
		return manager.createQuery("SELECT m FROM Municipio m ORDER BY m.nome").getResultList();
	}
}