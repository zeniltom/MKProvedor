package com.mkprovedor.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mkprovedor.model.Servico;
import com.mkprovedor.service.ServicoService;
import com.mkprovedor.util.Util;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ServicoCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServicoService servicoService;

	private Servico servico;

	public ServicoCadastroBean() {
		limpar();
	}

	public void inicializar() {

		if (this.servico == null)
			limpar();

		if (FacesUtil.isNotPostback()) {
		}
	}

	private void limpar() {
		servico = new Servico();
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/servico/pesquisa.xhtml");
	}

	public void salvar() throws IOException {

		if (this.servico.getId() == null) {

			servicoService.createNew(this.servico);
			FacesUtil.addInfoMessage("Serviço salvo com sucesso!");
			listar();

		} else {

			servicoService.update(this.servico);
			FacesUtil.addInfoMessage("Serviço atualizado com sucesso!");
			listar();
		}

		limpar();
	}

	public void excluir() {
		try {
			servicoService.delete(this.servico);
			FacesUtil.addInfoMessage("Servico " + this.servico.getDescricao() + " excluído com sucesso!");

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Não foi possível excluir " + this.servico.getDescricao() + ", consulte o suporte!");
		}
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico Servico) {
		this.servico = Servico;
	}

	public boolean isEditando() {
		return this.servico.getId() != null;
	}

}
