package com.mkprovedor.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.mkprovedor.model.Chamado;
import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Empregado;
import com.mkprovedor.service.ChamadoService;
import com.mkprovedor.util.Util;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ChamadoCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ChamadoService chamadoService;

	private Chamado chamado;

	public ChamadoCadastroBean() {
		limpar();
	}

	public void inicializar() {

		if (this.chamado == null)
			limpar();

		if (FacesUtil.isNotPostback()) {

		}
	}

	public void clienteSelecionado(SelectEvent event) {
		Cliente cliente = (Cliente) event.getObject();
		chamado.setCliente(cliente);
	}

	public void empregadoSelecionado(SelectEvent event) {
		Empregado empregado = (Empregado) event.getObject();
		chamado.setEmpregado(empregado);
	}

	private void limpar() {
		chamado = new Chamado();
		chamado.setEmpregado(new Empregado());
		chamado.setCliente(new Cliente());
		chamado.setSituacao(true);
		chamado.setDataChamado(new Date());
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/chamado/pesquisa.xhtml");
	}

	public void salvar() throws IOException {

		if (this.chamado.getId() == null) {

			chamadoService.createNew(this.chamado);
			FacesUtil.addInfoMessage("Chamado registrado com sucesso!");
			listar();

		} else {

			chamadoService.update(this.chamado);
			FacesUtil.addInfoMessage("Chamado atualizado com sucesso!");
			listar();
		}

		limpar();
	}

	public void excluir() {
		try {
			chamadoService.delete(this.chamado);
			FacesUtil.addInfoMessage("Chamado Nº" + this.chamado.getDescricao() + " excluído com sucesso!");

		} catch (Exception e) {
			FacesUtil.addErrorMessage(
					"Não foi possível excluir o chamado Nº " + this.chamado.getId() + ", consulte o suporte!");
		}
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado Chamado) {
		this.chamado = Chamado;
	}

	public boolean isEditando() {
		return this.chamado.getId() != null;
	}

}
