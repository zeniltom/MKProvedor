package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mkprovedor.model.Empregado;
import com.mkprovedor.repository.EmpregadoGrupos;
import com.mkprovedor.service.EmpregadoService;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EmpregadoPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpregadoService cadastroEmpregadoService;

	@Inject
	EmpregadoGrupos empregadoGrupos;

	private Empregado empregado;

	private List<Empregado> empregadoesFiltrados;

	private Empregado empregadoSelecionado;

	public EmpregadoPesquisaBean() {
		empregado = new Empregado();
	}

	public void excluir() {
		try {
			cadastroEmpregadoService.delete(empregadoSelecionado);
			FacesUtil.addInfoMessage("Empregado " + empregadoSelecionado.getNome() + " excluído com sucesso!");

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Não foi possível excluir " + empregadoSelecionado.getNome() + ", consulte o suporte!");
		}
	}

	public void pesquisar() {
		empregadoesFiltrados = cadastroEmpregadoService.filter(empregado);
	}

	public Empregado getEmpregado() {
		return empregado;
	}

	public List<Empregado> getEmpregadosFiltrados() {
		return empregadoesFiltrados;
	}

	public Empregado getEmpregadoSelecionado() {
		return empregadoSelecionado;
	}

	public void setEmpregadoSelecionado(Empregado EmpregadoSelecionado) {
		this.empregadoSelecionado = EmpregadoSelecionado;
	}

}
