
package com.mkprovedor.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "servico")
public class Servico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "é obrigatório")
	@Column(nullable = false)
	private String tipoPlano;

	@NotBlank(message = "é obrigatório")
	@Column(nullable = false)
	private String tipoServico;

	@Column(nullable = false, precision = 10, scale = 2)
	private double valorPlano;

	@NotNull(message = "é obrigatório")
	@Column(nullable = false)
	private String descricao;

	@Override
	public String toString() {
		NumberFormat moeda = NumberFormat.getCurrencyInstance();
		moeda.setCurrency(Currency.getInstance(new Locale("pt", "BR")));

		String servico = descricao + " [" + tipoPlano + "] - " + tipoServico + " | R$ " + moeda.format(valorPlano);
		return servico.toUpperCase();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoPlano() {
		return tipoPlano;
	}

	public void setTipoPlano(String tipoPlano) {
		this.tipoPlano = tipoPlano;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

	public double getValorPlano() {
		return valorPlano;
	}

	public void setValorPlano(double valorPlano) {
		this.valorPlano = valorPlano;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servico other = (Servico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
