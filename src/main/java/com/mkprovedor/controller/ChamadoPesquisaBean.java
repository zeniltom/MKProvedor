package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mkprovedor.model.Chamado;
import com.mkprovedor.service.ChamadoService;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ChamadoPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ChamadoService chamadoService;

	private Chamado chamado;

	private List<Chamado> chamadosAbertos;
	private List<Chamado> chamadosFechados;

	private Chamado chamadoSelecionado;

	public ChamadoPesquisaBean() {
		chamado = new Chamado();
	}

	public void excluir() {
		try {
			chamadoService.delete(chamadoSelecionado);
			FacesUtil.addInfoMessage("Chamado Nº" + chamadoSelecionado.getDescricao() + " excluído com sucesso!");

		} catch (Exception e) {
			FacesUtil.addErrorMessage(
					"Não foi possível excluir o chamado Nº " + chamadoSelecionado.getId() + ", consulte o suporte!");
		}
	}

	public void listar() {
		chamadosAbertos = chamadoService.findByAbertos(chamado);
		chamadosFechados = chamadoService.findByFechados(chamado);
	}

	public Chamado getChamado() {
		return chamado;
	}

	public List<Chamado> getchamadosAbertos() {
		return chamadosAbertos;
	}

	public List<Chamado> getChamadosFechados() {
		return chamadosFechados;
	}

	public Chamado getChamadoSelecionado() {
		return chamadoSelecionado;
	}

	public void setChamadoSelecionado(Chamado chamadoSelecionado) {
		this.chamadoSelecionado = chamadoSelecionado;
	}

}
