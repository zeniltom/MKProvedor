package com.mkprovedor.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import com.mkprovedor.model.Cliente;
import com.mkprovedor.model.Contrato;
import com.mkprovedor.service.ClienteService;
import com.mkprovedor.service.ContratoService;
import com.mkprovedor.service.RelatorioService;
import com.mkprovedor.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ClientePesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@PersistenceContext(unitName = "PedidoPU")
	private EntityManager manager;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private ClienteService clienteService;

	@Inject
	private ContratoService contratoService;

	@Inject
	private RelatorioService relatorioService;

	private Cliente cliente;

	private Contrato contrato;

	private List<Cliente> clientesFiltrados;
	private List<Cliente> clientesDesativados;

	private Cliente clienteSelecionado;

	private boolean juridico;

	public ClientePesquisaBean() {
		cliente = new Cliente();
		contrato = new Contrato();
	}

	public void excluir() {
		try {
			clienteService.delete(clienteSelecionado);
			FacesUtil.addInfoMessage("Cliente " + clienteSelecionado.getNome() + " excluído com sucesso!");

		} catch (Exception e) {
			FacesUtil.addErrorMessage(
					"Não foi possível excluir " + clienteSelecionado.getNome() + ", consulte o suporte!");
		}
	}

	public void imprimirContrato() {
		relatorioService.gerarContrato(this.facesContext, this.response, this.manager,
				this.clienteSelecionado);
	}

	public void pesquisar() {
		clientesFiltrados = clienteService.findByCpfCnpj(cliente);
		clientesDesativados = clienteService.findByDesativados();
	}

	public String contratoCliente(Cliente cliente) {
		contrato = contratoService.findByCliente(cliente);

		if (contrato == null)
			return "Sem plano";
		else
			return contrato.getServico().getTipoPlano();
	}

	public String contratoTempo(Cliente cliente) {
		contrato = contratoService.findByCliente(cliente);

		if (contrato == null)
			return "Sem plano";
		else
			return String.valueOf(contrato.getQtdMeses());
	}

	public String status(Cliente cliente) {
		String status = "";
		String retorno = "";

		contrato = contratoService.findByCliente(cliente);

		if (contrato == null)
			return "semPlano";

		else {

			if (cliente.getStatus() != null) {
				status = cliente.getStatus();

				if (status.equalsIgnoreCase("BLOQUEADO"))
					retorno = "bloqueado";
				else if (status.equalsIgnoreCase("DESATIVADO"))
					retorno = "desativado";

				return retorno;
			}
		}

		return retorno;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}

	public List<Cliente> getClientesDesativados() {
		return clientesDesativados;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public boolean isJuridico() {
		return juridico;
	}

	public void setJuridico(boolean juridico) {
		this.juridico = juridico;
	}
}
