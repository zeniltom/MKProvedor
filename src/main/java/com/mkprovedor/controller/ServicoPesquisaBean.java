package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mkprovedor.model.Servico;
import com.mkprovedor.service.ServicoService;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ServicoPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServicoService servicoService;

	private Servico servico;

	private List<Servico> servicosFiltrados;

	private Servico servicoSelecionado;

	public ServicoPesquisaBean() {
		servico = new Servico();
	}

	public void excluir() {
		try {
			servicoService.delete(servicoSelecionado);
			FacesUtil.addInfoMessage("Servico " + servicoSelecionado.getDescricao() + " excluído com sucesso!");

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Não foi possível excluir " + servicoSelecionado.getDescricao() + ", consulte o suporte!");
		}
	}

	public void pesquisar() {
		servicosFiltrados = servicoService.filter(servico);
	}

	public Servico getServico() {
		return servico;
	}

	public List<Servico> getServicosFiltrados() {
		return servicosFiltrados;
	}

	public Servico getServicoSelecionado() {
		return servicoSelecionado;
	}

	public void setServicoSelecionado(Servico servicoSelecionado) {
		this.servicoSelecionado = servicoSelecionado;
	}

}
