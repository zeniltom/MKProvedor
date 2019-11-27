package com.mkprovedor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "é obrigatório")
	@Column(nullable = false)
	private String nome;

	@NotBlank(message = "é obrigatório")
	@Length(max = 30, message = "não pode ultrapassar de 30 caracteres")
	private String cpfCnpj;

	private String telefone;

	private String descricao;

	@NotBlank(message = "é obrigatório")
	@Column(nullable = false)
	private String usuarioPPPOE;

	@NotBlank(message = "é obrigatório")
	@Column(nullable = false)
	private String senha;

	@NotBlank(message = "é obrigatório")
	private String enredeco;

	private String complemento;

	@NotBlank(message = "é obrigatório")
	private String bairro;

	private String numero;

	private String cep;

	private String status;

	@Temporal(TemporalType.DATE)
	private Date dataDesativacao;

	private boolean juridica;

	@ManyToOne
	@JoinColumn(name = "municipio_id")
	private Municipio municipio;

	@Override
	public String toString() {
		return nome + " (" + id + ")";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUsuarioPPPOE() {
		return usuarioPPPOE;
	}

	public void setUsuarioPPPOE(String usuarioPPPOE) {
		this.usuarioPPPOE = usuarioPPPOE;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEnredeco() {
		return enredeco;
	}

	public void setEnredeco(String enredeco) {
		this.enredeco = enredeco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isJuridica() {
		return juridica;
	}

	public void setJuridica(boolean juridica) {
		this.juridica = juridica;
	}

	public Date getDataDesativacao() {
		return dataDesativacao;
	}

	public void setDataDesativacao(Date dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
