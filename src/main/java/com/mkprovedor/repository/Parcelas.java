package com.mkprovedor.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mkprovedor.model.Mensalidade;
import com.mkprovedor.model.Parcela;

public class Parcelas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Parcela findById(Long id) {
		return this.entityManager.find(Parcela.class, id);
	}

	public void persist(Parcela parcela) {
		this.entityManager.persist(parcela);
	}

	public void update(Parcela parcela) {
		this.entityManager.merge(parcela);
	}

	public void delete(Parcela parcela) {
		this.entityManager.remove(this.findById(parcela.getId()));
	}

	public void deleteMensalidadeAndParcela(Mensalidade mensalidade) {

		String mensalidadeId = "0";

		if (mensalidade != null && mensalidade.getId() != null)
			mensalidadeId = mensalidade.getId().toString();

		this.entityManager.createNativeQuery("CALL excluir_parcelas_and_mensalidade(" + mensalidadeId + ")").executeUpdate();
	}

	public void deleteByMensalidade(Mensalidade mensalidade) {

		try {
			this.entityManager.createQuery("DELETE FROM Parcela p WHERE p.mensalidade.id = :idMensalidade")
					.setParameter("idMensalidade", mensalidade.getId()).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Parcela> findByMensalidade(Mensalidade mensalidade) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Parcela.class);
		criteria.createAlias("mensalidade", "mensalidade");

		if (mensalidade != null && mensalidade.getId() != null)
			criteria.add(Restrictions.eq("mensalidade.id", mensalidade.getId()));
		else
			criteria.add(Restrictions.eq("mensalidade.id", (long) 0));

		criteria.addOrder(Order.asc("id"));
		criteria.addOrder(Order.asc("parcela"));

		return criteria.list();
	}

}